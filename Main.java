package rentalSystem;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("Company Management System\n");
            System.out.println("1. Equipment");
            System.out.println("2. Drone Fleet");
            System.out.println("3. Members");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> equipmentMenu();
                case 2 -> droneMenu();
                case 3 -> memberMenu();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    // Equipment Menu
    private static void equipmentMenu() {
        int choice;
        do {
            System.out.println("\n--- Equipment Menu ---\n");
            System.out.println("1. Add Equipment");
            System.out.println("2. List Equipment");
            System.out.println("3. Update Equipment");
            System.out.println("4. Remove Equipment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> addEquipment();
                case 2 -> listEquipment();
                case 3 -> updateEquipment();
                case 4 -> removeEquipment();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addEquipment() {
        System.out.println("Adding new equipment...");
        System.out.print("Enter equipment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter equipment price: ");
        String price = scanner.nextLine();
        System.out.print("Enter equipment quantity: ");
        String quantity = scanner.nextLine();
        System.out.println("Equipment \"" + name + "\" of type \"" + type
                + "\" added successfully!");
        System.out.println("Price: " + price + "$ " + "Quantity: " + quantity + " units");
    }

    private static void listEquipment() {
        System.out.println("Listing all equipment...");
        System.out.println("(Equipment list will be displayed here)");
    }

    private static void updateEquipment() {
        System.out.println("Updating equipment...");
        System.out.print("Enter equipment ID to update: ");
        String id = scanner.nextLine();
        System.out.println("Equipment ID " + id + " updated successfully!");
    }

    private static void removeEquipment() {
        System.out.println("Removing equipment...");
        System.out.print("Enter equipment ID to remove: ");
        String id = scanner.nextLine();
        System.out.println("Equipment ID " + id + " removed successfully!");
    }

    // Drone Fleet Menu
    private static void droneMenu() {
        int choice;
        do {
            System.out.println("\n--- Drone Fleet Menu ---\n");
            System.out.println("1. Add Drone");
            System.out.println("2. List Drones");
            System.out.println("3. Update Drone");
            System.out.println("4. Remove Drone");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = getIntInput();

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

    // Member Menu
    private static void memberMenu() {
        int choice;
        do {
            System.out.println("\n--- Member Information Menu ---");
            System.out.println("1. Add Member");
            System.out.println("2. View Members");
            System.out.println("3. Update Member Info");
            System.out.println("4. Remove Member");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = getIntInput();

            switch (choice) {
                case 1 -> addMember();
                case 2 -> viewMembers();
                case 3 -> updateMember();
                case 4 -> removeMember();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addMember() {
        System.out.println("Adding new member...");
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.println("Member \"" + name + "\" added successfully!");
    }

    private static void viewMembers() {
        System.out.println("Viewing all members...");
        System.out.println("\nm1\nm2\nm3)");
    }

    private static void updateMember() {
        System.out.println("Updating member information...");
        System.out.print("Enter member ID to update: ");
        String id = scanner.nextLine();
        System.out.println("Member ID " + id + " updated successfully!");
    }

    private static void removeMember() {
        System.out.println("Removing member...");
        System.out.print("Enter member ID to remove: ");
        String id = scanner.nextLine();
        System.out.println("Member ID " + id + " removed successfully!");
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
