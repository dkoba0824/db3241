package menus;

import utilities.Utilities;

import java.util.Scanner;

public class ReportsMenu {

    // Reports Menu will be implemented here in future checkpoint
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
                case 1 -> System.out.println("[Report] Total items rented.");
                case 2 -> System.out.println("[Report] Most popular item.");
                case 3 -> System.out.println("[Report] Most popular manufacturer.");
                case 4 -> System.out.println("[Report] Most used drone.");
                case 5 -> System.out.println("[Report] Member with most checkouts.");
                case 6 -> System.out.println("[Report] Equipment by type before year.");
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }
}
