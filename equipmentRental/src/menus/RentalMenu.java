package menus;

import database.Database;
import utilities.Utilities;
import java.util.Scanner;

public class RentalMenu {

    private static final Scanner scanner = new Scanner(System.in);

    // Rental and Delivery Options Menu
    public static void rentalMenu() {
        int choice;
        do {
            System.out.println("\n--- Rental & Delivery Menu ---");
            System.out.println("1. Rent Equipment");
            System.out.println("2. Return Equipment");
            System.out.println("3. Schedule Delivery");
            System.out.println("4. Schedule Pickup");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> rentEquipment();
                case 2 -> returnEquipment();
                case 3 -> deliverEquipment();
                case 4 -> pickupEquipment();               
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void rentEquipment() {
        System.out.println("\nRenting Equipment...");

        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();

        String sql = "INSERT INTO Rental (User_ID, SerialNo, Date_Rented, Date_Due) VALUES (?, ?, date('now'), ?)";
        int rows = Database.executeUpdate(sql, memberId, String.format("EQ%05d", serial), dueDate);
        
        if (rows > 0) {
            System.out.println("Equipment rented successfully!");
        } else {
            System.out.println("Failed to rent equipment.");
        }
    }

    private static void returnEquipment() {
        System.out.println("\nReturning Equipment...");

        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();

        String sql = "UPDATE Rental SET Date_Returned = date('now') WHERE User_ID = ? AND SerialNo = ? AND Date_Returned IS NULL";
        int rows = Database.executeUpdate(sql, memberId, String.format("EQ%05d", serial));
        
        if (rows > 0) {
            System.out.println("Equipment returned successfully!");
        } else {
            System.out.println("Rental not found or already returned.");
        }
    }

    private static void deliverEquipment() {
        System.out.println("\nScheduling Equipment Delivery...");
        
        // Show available drones
        System.out.println("\nAvailable Drones:");
        String droneSql = "SELECT * FROM Drones";
        Database.runQuery(droneSql);

        System.out.print("\nEnter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = Utilities.getIntInput();

        String sql = "INSERT INTO Drone_Delivers (D_Serial, SerialNo, User_ID, Delivery_Date) VALUES (?, ?, ?, date('now'))";
        int rows = Database.executeUpdate(sql, droneId, String.format("EQ%05d", serial), memberId);
        
        if (rows > 0) {
            System.out.println("Delivery scheduled successfully!");
        } else {
            System.out.println("Failed to schedule delivery.");
        }
    }

    private static void pickupEquipment() {
        System.out.println("\nScheduling Equipment Pickup...");
        
        // Show available drones
        System.out.println("\nAvailable Drones:");
        String droneSql = "SELECT * FROM Drones";
        Database.runQuery(droneSql);

        System.out.print("\nEnter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = Utilities.getIntInput();

        String sql = "INSERT INTO Drone_Delivers (D_Serial, SerialNo, User_ID, Pickup_Date) VALUES (?, ?, ?, date('now'))";
        int rows = Database.executeUpdate(sql, droneId, String.format("EQ%05d", serial), memberId);
        
        if (rows > 0) {
            System.out.println("Pickup scheduled successfully!");
        } else {
            System.out.println("Failed to schedule pickup.");
        }
    }
}
