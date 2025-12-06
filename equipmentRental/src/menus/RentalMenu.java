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
        System.out.print("Enter rental fee: ");
        double fee = Utilities.getDoubleInput();

        String sqlRental = "INSERT INTO RENTAL (User_ID, Check_Out, Due_Date, Returns, Fees) VALUES (?, date('now'), ?, 'No', ?)";
        int rowsRental = Database.executeUpdate(sqlRental, String.format("USR%05d", memberId), dueDate, fee);
        
        if (rowsRental > 0) {
            // Insert into HAS_RENTAL table to link equipment with user
            String sqlHasRental = "INSERT INTO HAS_RENTAL (SerialNo, User_ID) VALUES (?, ?)";
            Database.executeUpdate(sqlHasRental, String.format("EQ%05d", serial), String.format("USR%05d", memberId));
            
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
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();

        // Update RENTAL table - Returns is 'Yes'/'No'
        String sql = "UPDATE RENTAL SET Returns = 'Yes' WHERE User_ID = ? AND Check_Out = ? AND Returns = 'No'";
        int rows = Database.executeUpdate(sql, String.format("USR%05d", memberId), checkOutDate);
        
        if (rows > 0) {
            // Remove from HAS_RENTAL table since equipment is no longer rented by the user
            String sqlHasRental = "DELETE FROM HAS_RENTAL WHERE User_ID = ? AND SerialNo = ?";
            Database.executeUpdate(sqlHasRental, String.format("USR%05d", memberId), String.format("EQ%05d", serial));
            
            // Also remove any delivery/pickup records for this equipment (not in transit anymore)
            String sqlDroneDelivers = "DELETE FROM DRONE_DELIVERS WHERE SerialNo = ?";
            Database.executeUpdate(sqlDroneDelivers, String.format("EQ%05d", serial));
            
            System.out.println("Equipment returned successfully!");
        } else {
            System.out.println("Rental not found or already returned.");
        }
    }

    private static void deliverEquipment() {
        System.out.println("\nScheduling Equipment Delivery...");
        
        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        
        // Verify that this user has rented this equipment
        String checkSql = "SELECT * FROM HAS_RENTAL WHERE User_ID = ? AND SerialNo = ?";
        System.out.println("\nVerifying rental record...");
        Database.runQuery(checkSql, String.format("USR%05d", memberId), String.format("EQ%05d", serial));
        
        // Show available drones
        System.out.println("\nAvailable Drones:");
        String droneSql = "SELECT * FROM DRONES";
        Database.runQuery(droneSql);

        System.out.print("\nEnter Drone Serial Number to assign for delivery: ");
        int droneId = Utilities.getIntInput();

        // Insert delivery record
        String sql = "INSERT INTO DRONE_DELIVERS (SerialNo, D_Serial) VALUES (?, ?)";
        int rows = Database.executeUpdate(sql, String.format("EQ%05d", serial), String.format("DRN%05d", droneId));
        
        if (rows > 0) {
            System.out.println("Delivery scheduled successfully for member " + memberId + "!");
        } else {
            System.out.println("Failed to schedule delivery.");
            System.out.println("This may be because the drone is already assigned to another delivery/pickup,");
            System.out.println("or the equipment already has a delivery/pickup scheduled.");
        }
    }

    private static void pickupEquipment() {
        System.out.println("\nScheduling Equipment Pickup...");
        
        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        
        // Verify that this user has rented this equipment
        String checkSql = "SELECT * FROM HAS_RENTAL WHERE User_ID = ? AND SerialNo = ?";
        System.out.println("\nVerifying rental record...");
        Database.runQuery(checkSql, String.format("USR%05d", memberId), String.format("EQ%05d", serial));
        
        // Show available drones
        System.out.println("\nAvailable Drones:");
        String droneSql = "SELECT * FROM DRONES";
        Database.runQuery(droneSql);

        System.out.print("\nEnter Drone Serial Number to assign for pickup: ");
        int droneId = Utilities.getIntInput();

        // Delete any existing delivery record for this equipment/drone
        // This allows pickup to be scheduled after delivery was completed
        String deleteSql = "DELETE FROM DRONE_DELIVERS WHERE SerialNo = ? AND D_Serial = ?";
        Database.executeUpdate(deleteSql, String.format("EQ%05d", serial), String.format("DRN%05d", droneId));

        // Insert pickup record
        String sql = "INSERT INTO DRONE_DELIVERS (SerialNo, D_Serial) VALUES (?, ?)";
        int rows = Database.executeUpdate(sql, String.format("EQ%05d", serial), String.format("DRN%05d", droneId));
        
        if (rows > 0) {
            System.out.println("Pickup scheduled successfully for member " + memberId + "!");
        } else {
            System.out.println("Failed to schedule pickup.");
        }
    }
}
