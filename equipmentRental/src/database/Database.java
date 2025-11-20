package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:nonprofitorg.db";
    private static PreparedStatement ps;

    // Returns DB connection for queries
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Database connection error:");
            e.printStackTrace();
            return null;
        }
    }

    // Test method to verify connection on startup
    public static void testConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                System.out.println("Connection to database successful!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    // Method to run queries using a prepared statement
     public static void sqlQuery(Connection conn, PreparedStatement ps) {
        try {
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                String value = rsmd.getColumnName(i);
                System.out.print(value);
                if (i < columnCount) System.out.print(",  ");
            }
            System.out.print("\n");

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                    if (i < columnCount) System.out.print(",  ");
                }
                System.out.print("\n");
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // SQL query with no parameters (pass query as string)
    public static void runQuery(String sql) {
        try (Connection conn = getConnection()) {
            ps = conn.prepareStatement(sql);
            sqlQuery(conn, ps);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Queries that have additional parameters
    public static void psTotalItemsRented(String sql, String memberId) {
        try (Connection conn = getConnection()) {
            ps = conn.prepareStatement(sql);
            ps.setString(1, memberId);
            sqlQuery(conn, ps);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void psEquipmentByTypeBeforeYear(String sql, String type, int year) {
        try (Connection conn = getConnection()) {
            ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ps.setInt(2, year);
            sqlQuery(conn, ps);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //IMPT: Other methods can call runQuery directly
    
    public static void runQuery(String sql, Object... params) {
        try (Connection conn = getConnection()) {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            sqlQuery(conn, ps);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Placeholders for other SQL methods
    public static void addEquipmentPlaceholder() {
        System.out.println("[DB Placeholder] Add equipment");
    }

    public static void updateEquipmentPlaceholder() {
        System.out.println("[DB Placeholder] Update equipment");
    }

    public static void deleteEquipmentPlaceholder() {
        System.out.println("[DB Placeholder] Delete equipment");
    }

    public static void searchEquipmentPlaceholder() {
        System.out.println("[DB Placeholder] Search equipment");
    }

    public static void getAllEquipmentPlaceholder() {
        System.out.println("[DB Placeholder] Get all equipment");
    }


}