package menus;

import utilities.Utilities;

import java.util.Scanner;

public class ReportsMenu {

    // Reports Menu will be implemented here in future checkpoint
    public static void reportsMenu() {
        int choice;
        do {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> System.out.println("Option 1");
                case 2 -> System.out.println("Option 2");
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
}
