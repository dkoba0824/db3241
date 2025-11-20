package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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