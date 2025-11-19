package menus;

import utilities.Utilities;

import java.util.Scanner;

public class DroneMenu {

    private static final Scanner scanner = new Scanner(System.in);

    // Drone Fleet Menu (Will be implemented in the future)
    public static void droneMenu() {
        int choice;
        do {
            System.out.println("\n--- Drone Fleet Menu ---\n");
            System.out.println("1. Add Drone");
            System.out.println("2. List Drones");
            System.out.println("3. Update Drone");
            System.out.println("4. Remove Drone");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> addDrone();
                case 2 -> listDrones();
                case 3 -> updateDrone();
                case 4 -> removeDrone();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addDrone() {
        System.out.println("Adding new drone...");
        System.out.print("Enter drone model: ");
        String model = scanner.nextLine();
        System.out.print("Enter drone capacity: ");
        String capacity = scanner.nextLine();
        System.out.println("Drone \"" + model + "\" with capacity \"" + capacity
                + "\" added successfully!");
    }

    private static void listDrones() {
        System.out.println("Listing all drones...");
        System.out.println("(Drone list will be displayed here)");
    }

    private static void updateDrone() {
        System.out.println("Updating drone...");
        System.out.print("Enter drone ID to update: ");
        String id = scanner.nextLine();
        System.out.println("Drone ID " + id + " updated successfully!");
    }

    private static void removeDrone() {
        System.out.println("Removing drone...");
        System.out.print("Enter drone ID to remove: ");
        String id = scanner.nextLine();
        System.out.println("Drone ID " + id + " removed successfully!");
    }
}

