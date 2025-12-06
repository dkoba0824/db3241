package utilities;

import java.util.Scanner;

public class Utilities {
    private static Scanner scanner = new Scanner(System.in);

    public static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }
}
