package utilities;

import java.util.Scanner;

public class Utilities {

    private static final Scanner scanner = new Scanner(System.in);

    // Helper Functions
    public static void printLine() {
        System.out.println("------------------------------------------------");
    }


    public static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
