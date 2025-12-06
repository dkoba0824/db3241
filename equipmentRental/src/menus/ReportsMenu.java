package menus;

import database.Database;
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
                case 2 -> reportMostPopularItem();
                case 3 -> reportMostPopularManufacturer();
                case 4 -> reportMostPopularDrone();
                case 5 -> reportMemberWithMostRentals();
                case 6 -> reportEquipmentByType();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }
    
    private static void reportRentingCheckouts() {
        System.out.print("Enter Member ID (Format: \"USR#####\"): ");
        String memberId = scanner.nextLine();
        String sql = "SELECT COUNT(*) AS Total_Rented FROM Rental WHERE User_ID = ?";
        Database.runQuery(sql, memberId);
    }
    
    private static void reportMostPopularItem() {
        System.out.println("Most Popular Item:");
        String sql = "SELECT E.SerialNo, E.Model, COUNT(R.SerialNo) AS Total_Rented FROM EQUIPMENT E JOIN Rental R ON E.SerialNo = R.SerialNo GROUP BY E.SerialNo ORDER BY Total_Rented DESC LIMIT 1";
        Database.runQuery(sql);
    }
    
    private static void reportMostPopularManufacturer() {
        System.out.println("Most Popular Manufacturer:");
        String sql = "SELECT M.Name, COUNT(R.SerialNo) AS Total_Rented FROM Manufacturer M JOIN EQUIPMENT E ON M.M_ID = E.M_ID JOIN Rental R ON E.SerialNo = R.SerialNo GROUP BY M.M_ID ORDER BY Total_Rented DESC LIMIT 1";
        Database.runQuery(sql);
    }
    
    private static void reportMostPopularDrone() {
        System.out.println("Most Popular Drone:");
        String sql = "SELECT D.SerialNo, D.Name, COUNT(DD.D_Serial) AS Total_Deliveries FROM Drones D JOIN Drone_Delivers DD ON D.SerialNo = DD.D_Serial GROUP BY D.SerialNo ORDER BY Total_Deliveries DESC LIMIT 1";
        Database.runQuery(sql);
    }
    
    private static void reportMemberWithMostRentals() {
        System.out.println("Member with Most Rentals:");
        String sql = "SELECT User_ID, COUNT(SerialNo) AS Rental_Amount FROM Rental GROUP BY User_ID ORDER BY Rental_Amount DESC LIMIT 1";
        Database.runQuery(sql);
    }
    
    private static void reportEquipmentByType() {
        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter year (show equipment released before this year): ");
        int year = Utilities.getIntInput();
        String sql = "SELECT Descrip FROM EQUIPMENT WHERE Type = ? AND Year < ?";
        Database.runQuery(sql, type, year);
    }
}
