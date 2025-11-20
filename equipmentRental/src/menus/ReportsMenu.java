package menus;

import database.ReportsDAO;
import utilities.Utilities;

import java.util.Scanner;

public class ReportsMenu {
    
    private static final Scanner scanner = new Scanner(System.in);

    // Reports Menu
    public static void reportsMenu() {
        int choice;
        do {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Total items rented by a member");
            System.out.println("2. Most popular item");
            System.out.println("3. Most popular manufacturer");
            System.out.println("4. Most used drone");
            System.out.println("5. Member with most checkouts");
            System.out.println("6. Equipment by type before year");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> reportRentingCheckouts();
                case 2 -> ReportsDAO.reportMostPopularItem();
                case 3 -> ReportsDAO.reportMostPopularManufacturer();
                case 4 -> ReportsDAO.reportMostPopularDrone();
                case 5 -> ReportsDAO.reportMemberWithMostRentals();
                case 6 -> reportEquipmentByType();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }
    
    private static void reportRentingCheckouts() {
        System.out.print("Enter Member ID: ");
        int memberId = Utilities.getIntInput();
        ReportsDAO.reportRentingCheckouts(memberId);
    }
    
    private static void reportEquipmentByType() {
        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter year (show equipment released before this year): ");
        int year = Utilities.getIntInput();
        ReportsDAO.reportEquipmentByTypeAndYear(type, year);
    }
}
