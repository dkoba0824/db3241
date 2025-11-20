package database;

import java.sql.*;
import java.io.File;

public class Database {

    // Get the correct database path
    private static String getDbPath() {
        // Get the project root (where the database should be)
        File currentDir = new File(System.getProperty("user.dir"));
        
        // Check if we're in the equipmentRental directory
        if (currentDir.getName().equals("equipmentRental")) {
            return "jdbc:sqlite:nonprofitorg.db";
        }
        
        // Check if nonprofitorg.db exists in equipmentRental subdirectory
        File equipmentRentalDb = new File(currentDir, "equipmentRental/nonprofitorg.db");
        if (equipmentRentalDb.exists()) {
            return "jdbc:sqlite:equipmentRental/nonprofitorg.db";
        }
        
        // Default to current directory
        return "jdbc:sqlite:nonprofitorg.db";
    }

    // Returns DB connection for queries
    public static Connection getConnection() {
        try {
            String dbUrl = getDbPath();
            Connection conn = DriverManager.getConnection(dbUrl);
            
            // Verify we have the right database by checking for EQUIPMENT table
            try (var stmt = conn.createStatement();
                 var rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='EQUIPMENT'")) {
                if (!rs.next()) {
                    System.out.println("WARNING: Connected to database but EQUIPMENT table not found!");
                    System.out.println("Database location: " + new File("nonprofitorg.db").getAbsolutePath());
                    System.out.println("Current directory: " + System.getProperty("user.dir"));
                }
            }
            
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection error:");
            e.printStackTrace();
            return null;
        }
    }

    // Test method to verify connection on startup
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connection to database successful!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    // Run a simple SQL query and print results
    public static void runQuery(String sql) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i));
                if (i < columnCount) System.out.print(",  ");
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i));
                    if (i < columnCount) System.out.print(",  ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
    }

    // Run query with parameters
    public static void runQuery(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Set parameters
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i));
                if (i < columnCount) System.out.print(",  ");
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i));
                    if (i < columnCount) System.out.print(",  ");
                }
                System.out.println();
            }
            
            rs.close();
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
    }

    // Execute update/insert/delete statements
    public static int executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
            return 0;
        }
    }
}