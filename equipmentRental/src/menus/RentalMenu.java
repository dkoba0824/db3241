package menus;

import database.RentalDAO;
import database.DroneDAO;
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
        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();

        RentalDAO.rentEquipment(memberId, serial, dueDate);
    }

    private static void returnEquipment() {
        System.out.println("\nReturning Equipment...");

        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();

        RentalDAO.returnEquipment(memberId, serial);
    }

    private static void deliverEquipment() {
        System.out.println("\nScheduling Equipment Delivery...");
        
        // Show available drones
        DroneDAO.displayAvailableDrones();

        System.out.print("\nEnter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = Utilities.getIntInput();

        DroneDAO.scheduleDelivery(memberId, serial, droneId);
    }

    private static void pickupEquipment() {
        System.out.println("\nScheduling Equipment Pickup...");
        
        // Show available drones
        DroneDAO.displayAvailableDrones();

        System.out.print("\nEnter Member ID: ");
        int memberId = Utilities.getIntInput();
        System.out.print("Enter Equipment Serial Number: ");
        int serial = Utilities.getIntInput();
        System.out.print("Enter Drone ID to assign: ");
        int droneId = Utilities.getIntInput();

        DroneDAO.schedulePickup(memberId, serial, droneId);
    }
}
