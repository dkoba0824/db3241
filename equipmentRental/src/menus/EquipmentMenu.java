package menus;

import database.Database;
import entities.Equipment;
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

        //Check for duplicate serial number
        if (findEquipmentBySerial(serialNumber) != null) {
            System.out.println("Equipment with this serial number already exists!");
            return;
        }

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

        // TODO: Replace with SQL
        // Equipment eq = new Equipment(serialNumber, description, type, model, year, dimensions, weight, location, quantity, true);
        // equipmentList.add(eq);
        // System.out.println("Equipment added successfully!\n" + eq);
    }

    private static void listEquipment() {
        System.out.println("Listing all equipment...");
        // TODO: Replace with SQL
        // if (equipmentList.isEmpty()) {
        //     System.out.println("No equipment available.");
        // } else {
        //     for (Equipment eq : equipmentList) {
        //         System.out.println(eq);
        //     }
        // }
    }

    private static void updateEquipment() {

        // TODO: Replace with SQL


        System.out.println("Updating equipment...");
        System.out.print("Enter equipment serial number to update: ");
        int serial = Utilities.getIntInput();
        Equipment eq = findEquipmentBySerial(serial);

        if (eq != null) {
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

            eq.setDescription(description);
            eq.setType(type);
            eq.setModel(model);
            eq.setYear(year);
            eq.setDimensions(dimensions);
            eq.setWeight(weight);
            eq.setLocation(location);
            eq.setQuantity(quantity);

            System.out.println("Equipment ID " + serial + " updated successfully!");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    private static void removeEquipment() {
        System.out.println("Removing equipment...");
        System.out.print("Enter serial number to remove: ");
        int id = Utilities.getIntInput();
        // TODO: Replace with SQL
        // Equipment eq = findEquipmentBySerial(id);

        // if (eq != null) {
        //     equipmentList.remove(eq);
        //     System.out.println("Equipment removed successfully!");
        // } else {
        //     System.out.println("Equipment not found.");
        // }
    }

    private static void searchEquipment() {
        System.out.print("Enter equipment serial number: ");
        int id = Utilities.getIntInput();
        Equipment eq = findEquipmentBySerial(id);

        // TODO: Replace with SQL
        // if (eq != null) {
        //     System.out.println("\nEquipment Found!");
        //     System.out.println(eq);
        // } else {
        //     System.out.println("No equipment found with that serial number.");
        // }
    }

    private static Equipment findEquipmentBySerial(int serial) {

        //Replace with SQL
        // for (Equipment eq : equipmentList) {
        //     if (eq.getSerialNumber() == serial){
        //         return eq;
        //     }
        // }
        return null;
    }
}
