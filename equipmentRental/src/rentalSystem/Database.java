package rentalSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:nonprofitorg.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database.");
            e.printStackTrace();
            return null;
        }
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connected to database successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}