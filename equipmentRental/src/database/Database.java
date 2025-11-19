package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:nonprofitorg.db";

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