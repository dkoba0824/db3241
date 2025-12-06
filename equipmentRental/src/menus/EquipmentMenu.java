package menus;

import database.Database;
import utilities.Utilities;
import java.util.Scanner;

public class EquipmentMenu {

    private static final Scanner scanner = new Scanner(System.in);

    // Equipment Menu
    public static void equipmentMenu() {
        int choice;
        do {
            System.out.println("\n--- Equipment Menu ---\n");
            System.out.println("1. Add Equipment");
            System.out.println("2. List Equipment");
            System.out.println("3. Update Equipment");
            System.out.println("4. Remove Equipment");
            System.out.println("5. Search Equipment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> addEquipment();
                case 2 -> listEquipment();
                case 3 -> updateEquipment();
                case 4 -> removeEquipment();
                case 5 -> searchEquipment();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addEquipment() {
        System.out.println("Adding new equipment...");
        System.out.print("Enter equipment serial number: ");
        int serialNumber = Utilities.getIntInput();

        System.out.print("Enter equipment description: ");
        String description = scanner.nextLine();
        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter equipment model: ");
        String model = scanner.nextLine();
        System.out.print("Enter equipment year: ");
        int year = Utilities.getIntInput();
        System.out.print("Enter equipment dimensions (ex: 10x20x30 in): ");
        String dimensions = scanner.nextLine();
        System.out.print("Enter equipment weight (in lbs): ");
        int weight = Utilities.getIntInput();
        System.out.print("Enter equipment warehouse location: ");
        String location = scanner.nextLine();        
        System.out.print("Enter equipment quantity: ");
        int quantity = Utilities.getIntInput();
        System.out.print("Enter manufacturer ID (e.g., MFG001): ");
        String manufacturerId = scanner.nextLine();
        System.out.print("Enter order number: ");
        String orderNumber = scanner.nextLine();
        System.out.print("Enter warranty expiration date (YYYY-MM-DD): ");
        String warrantyExp = scanner.nextLine();

        String sql = "INSERT INTO EQUIPMENT (SerialNo, Model, Quantity, Weight, Location, Status, Descrip, Dimensions, Year, Type, M_ID, OrderNo, Warranty_Exp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rows = Database.executeUpdate(sql, String.format("EQ%05d", serialNumber), model, quantity, weight, location, "Available", description, dimensions, year, type, manufacturerId, orderNumber, warrantyExp);
        
        if (rows > 0) {
            System.out.println("Equipment added successfully!");
        } else {
            System.out.println("Failed to add equipment.");
        }
    }

    private static void listEquipment() {
        System.out.println("Listing all equipment...");
        String sql = "SELECT * FROM EQUIPMENT ORDER BY CAST(SUBSTR(SerialNo, 3) AS INTEGER)";
        Database.runQuery(sql);
    }

    private static void updateEquipment() {
        System.out.println("Updating equipment...");
        System.out.print("Enter equipment serial number to update: ");
        int serial = Utilities.getIntInput();

        System.out.print("Enter updated equipment description: ");
        String description = scanner.nextLine();
        System.out.print("Enter updated equipment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter updated equipment model: ");
        String model = scanner.nextLine();
        System.out.print("Enter updated equipment year: ");
        int year = Utilities.getIntInput();
        System.out.print("Enter updated equipment dimensions (ex: 10x20x30 in): ");
        String dimensions = scanner.nextLine();
        System.out.print("Enter updated equipment weight (in lbs): ");
        int weight = Utilities.getIntInput();
        System.out.print("Enter updated equipment warehouse location: ");
        String location = scanner.nextLine();        
        System.out.print("Enter updated equipment quantity: ");
        int quantity = Utilities.getIntInput();
        System.out.print("Enter updated equipment status: ");
        String status = scanner.nextLine();

        String sql = "UPDATE EQUIPMENT SET Descrip = ?, Type = ?, Model = ?, Year = ?, Dimensions = ?, Weight = ?, Location = ?, Quantity = ?, Status = ? WHERE SerialNo = ?";
        int rows = Database.executeUpdate(sql, description, type, model, year, dimensions, weight, location, quantity, status, String.format("EQ%05d", serial));
        if (rows > 0) {
            System.out.println("Equipment ID " + serial + " updated successfully!");
        } else {
            System.out.println("Equipment not found or failed to update.");
        }
    }

    private static void removeEquipment() {
        System.out.println("Removing equipment...");
        System.out.print("Enter serial number to remove: ");
        int id = Utilities.getIntInput();

        // Delete from HAS_RENTAL table (foreign key)
        String sqlHasRental = "DELETE FROM HAS_RENTAL WHERE SerialNo = ?";
        Database.executeUpdate(sqlHasRental, String.format("EQ%05d", id));
        
        // Delete from DRONE_DELIVERS table (foreign key)
        String sqlDroneDelivers = "DELETE FROM DRONE_DELIVERS WHERE SerialNo = ?";
        Database.executeUpdate(sqlDroneDelivers, String.format("EQ%05d", id));
        
        // Delete from EQUIPMENT table
        String sql = "DELETE FROM EQUIPMENT WHERE SerialNo = ?";
        int rows = Database.executeUpdate(sql, String.format("EQ%05d", id));
        
        if (rows > 0) {
            System.out.println("Equipment removed successfully!");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    private static void searchEquipment() {
        System.out.print("Enter equipment serial number: ");
        int id = Utilities.getIntInput();
        
        String sql = "SELECT * FROM EQUIPMENT WHERE SerialNo = ?";
        Database.runQuery(sql, String.format("EQ%05d", id));
    }

}
