package menus;

import database.Database;
import utilities.Utilities;
import java.util.Scanner;

public class DroneMenu {

    private static final Scanner scanner = new Scanner(System.in);

    // Drone Fleet Menu
    public static void droneMenu() {
        int choice;
        do {
            System.out.println("\n--- Drone Fleet Menu ---\n");
            System.out.println("1. Add Drone");
            System.out.println("2. List Drones");
            System.out.println("3. Update Drone");
            System.out.println("4. Remove Drone");
            System.out.println("5. Search Drone");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> addDrone();
                case 2 -> listDrones();
                case 3 -> updateDrone();
                case 4 -> removeDrone();
                case 5 -> searchDrone();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addDrone() {
        System.out.println("Adding new drone...");
        System.out.print("Enter drone serial number (e.g., DRN00001): ");
        String serialNo = scanner.nextLine();

        System.out.print("Enter drone name: ");
        String name = scanner.nextLine();
        System.out.print("Enter drone model: ");
        String model = scanner.nextLine();
        System.out.print("Enter drone year: ");
        int year = Utilities.getIntInput();
        System.out.print("Enter drone location: ");
        String location = scanner.nextLine();
        System.out.print("Enter drone status (Active/Inactive/Maintenance): ");
        String status = scanner.nextLine();
        System.out.print("Enter speed capacity (mph): ");
        int speedCap = Utilities.getIntInput();
        System.out.print("Enter weight capacity (lbs): ");
        int weightCap = Utilities.getIntInput();
        System.out.print("Enter distance capacity (miles): ");
        int distCap = Utilities.getIntInput();
        System.out.print("Enter warehouse ID (e.g., WH001): ");
        String warehouseId = scanner.nextLine();
        System.out.print("Enter manufacturer ID (e.g., MFG001): ");
        String manufacturerId = scanner.nextLine();
        System.out.print("Enter warranty expiration date (YYYY-MM-DD): ");
        String warrantyExp = scanner.nextLine();

        String sql = "INSERT INTO Drones (SerialNo, Name, Year, Model, Location, Status, Speed_Cap, Weight_Cap, Dist_Cap, W_ID, M_ID, Warranty_Exp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rows = Database.executeUpdate(sql, serialNo, name, year, model, location, status, speedCap, weightCap, distCap, warehouseId, manufacturerId, warrantyExp);
        
        if (rows > 0) {
            System.out.println("Drone added successfully!");
        } else {
            System.out.println("Failed to add drone.");
        }
    }

    private static void listDrones() {
        System.out.println("Listing all drones...");
        String sql = "SELECT * FROM Drones ORDER BY SerialNo";
        Database.runQuery(sql);
    }

    private static void updateDrone() {
        System.out.println("Updating drone...");
        System.out.print("Enter drone serial number to update: ");
        String serial = scanner.nextLine();

        System.out.print("Enter updated drone name: ");
        String name = scanner.nextLine();
        System.out.print("Enter updated drone model: ");
        String model = scanner.nextLine();
        System.out.print("Enter updated year: ");
        int year = Utilities.getIntInput();
        System.out.print("Enter updated location: ");
        String location = scanner.nextLine();
        System.out.print("Enter updated weight capacity (lbs): ");
        int weightCap = Utilities.getIntInput();
        System.out.print("Enter updated speed capacity (mph): ");
        int speedCap = Utilities.getIntInput();
        System.out.print("Enter updated distance capacity (miles): ");
        int distCap = Utilities.getIntInput();
        System.out.print("Enter updated drone status (Active/Inactive/Maintenance): ");
        String status = scanner.nextLine();

        String sql = "UPDATE Drones SET Name = ?, Model = ?, Year = ?, Location = ?, Weight_Cap = ?, Speed_Cap = ?, Dist_Cap = ?, Status = ? WHERE SerialNo = ?";
        int rows = Database.executeUpdate(sql, name, model, year, location, weightCap, speedCap, distCap, status, serial);
        
        if (rows > 0) {
            System.out.println("Drone " + serial + " updated successfully!");
        } else {
            System.out.println("Drone not found or failed to update.");
        }
    }

    private static void removeDrone() {
        System.out.println("Removing drone...");
        System.out.print("Enter drone serial number to remove: ");
        String id = scanner.nextLine();
        
        String sql = "DELETE FROM Drones WHERE SerialNo = ?";
        int rows = Database.executeUpdate(sql, id);
        
        if (rows > 0) {
            System.out.println("Drone removed successfully!");
        } else {
            System.out.println("Drone not found.");
        }
    }

    private static void searchDrone() {
        System.out.print("Enter drone serial number: ");
        String id = scanner.nextLine();
        
        String sql = "SELECT * FROM Drones WHERE SerialNo = ?";
        Database.runQuery(sql, id);
    }
}

