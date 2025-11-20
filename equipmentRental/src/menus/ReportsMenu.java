package menus;

import utilities.Utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import database.Database;
import entities.Equipment;

import java.util.Scanner;

public class ReportsMenu {
    private static final Scanner scanner = new Scanner(System.in);

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
                case 1 -> rentalCheckouts();
                case 2 -> popularItem();
                case 3 -> popularManufacturer();
                case 4 -> popularDrone();
                case 5 -> ItemsOut() ;
                case 6 -> EquipByType();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }
    private static void rentalCheckouts() {
        System.out.println("Rental Checkouts: Find total items rented by a member.");

        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine(); 

        String sql = "SELECT COUNT(*) AS Total_Rented FROM Rental WHERE User_ID = ?";


        Database.psTotalItemsRented(sql, memberId);
    }
    private static void popularItem() {
        System.out.println("Most Popular Item");
        String sql = """
        SELECT E.SerialNo, E.Model, COUNT(H.SerialNo) AS Total_Rented 
        FROM Equipment E, Has_Rental AS H 
        WHERE E.SerialNo = H.SerialNo 
        GROUP BY E.SerialNo 
        ORDER BY Total_Rented DESC
        LIMIT 1;
    """;
       
    Database.runQuery(sql);

        
    }
    private static void popularManufacturer() {
        System.out.println("Most Popular Manufacturer:");
        String sql = "SELECT M.Name AS Manufacturer, COUNT(H.SerialNo) AS Total_Rented FROM Manufacturer AS M, Equipment AS E, Has_Rental AS H WHERE M.M_ID = E.M_ID AND E.SerialNo = H.SerialNo GROUP BY M.M_ID ORDER BY Total_Rented DESC LIMIT 1;";

        Database.runQuery(sql);

    }
    private static void popularDrone() {
        System.out.println("Most Popular Drone");
        String sql = """
        SELECT D.SerialNo, D.Name, SUM(C.Ware_Dist) AS Dist_Traveled, 
               COUNT(DD.SerialNo) AS Total_Deliveries
        FROM Drones AS D
        JOIN Drone_Delivers AS DD ON D.SerialNo = DD.D_Serial
        JOIN Equipment AS E ON E.SerialNo = DD.SerialNo
        JOIN Has_Rental AS H ON E.SerialNo = H.SerialNo
        JOIN Customers AS C ON C.User_ID = H.User_ID
        GROUP BY D.SerialNo
        ORDER BY Total_Deliveries DESC
        LIMIT 1;
    """;
    Database.runQuery(sql);
        
    }
    private static void ItemsOut() {
        System.out.println("Member with the most checkouts:");
        String sql = "SELECT User_ID, COUNT(SerialNo) AS Rental_Amount " +
                 "FROM Has_Rental " +
                 "GROUP BY User_ID " +
                 "ORDER BY Rental_Amount DESC " +
                 "LIMIT 1;";
                 Database.runQuery(sql);

        }
    
    private static void EquipByType() {
        System.out.println("Equipment by Type: Find equipment of a certain type released before a given year.");

        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine(); 
        System.out.print("Enter year: ");
        int year = Utilities.getIntInput();

        String sql = "SELECT Descrip " +
        "FROM Equipment " +
        "WHERE Type = ? AND Year < ?;";

        Database.psEquipmentByTypeBeforeYear(sql, type, year);
    }

}

