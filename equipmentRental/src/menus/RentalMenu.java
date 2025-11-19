package menus;

import utilities.Utilities;

import java.util.Scanner;

public class RentalMenu {

    private static final Scanner scanner = new Scanner(System.in);

    // Rental and Delivery Options Menu (NOTE: Functionality limited for Checkpoint 2, produces warnings)
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
        System.out.print("Enter rental start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter expected return date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        System.out.println("Equipment rented successfully!");
    }

    private static void returnEquipment() {
        System.out.println("\nReturning Equipment...");

        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter return date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        System.out.println("Equipment returned successfully!");
    }

    private static void deliverEquipment() {
        System.out.println("\nScheduling Equipment Delivery...");

        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = Utilities.getIntInput();
        System.out.print("Enter delivery date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.println("Equipment delivered successfully!");
    }

    private static void pickupEquipment() {
        System.out.println("\nScheduling Equipment Pickup...");

        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = Utilities.getIntInput();
        System.out.print("Enter pickup date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.println("Equipment returned successfully!");
    }
}
