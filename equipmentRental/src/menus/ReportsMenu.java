package menus;

import utilities.Utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import database.Database;
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
        System.out.println("Rental Checkouts");
    }
    private static void popularItem() {
        System.out.println("Popular Item");
    }
    private static void popularManufacturer() {
        System.out.println("Most Popular Manufacturer:");
        String sql = "SELECT M.Name AS Manufacturer, COUNT(H.SerialNo) AS Total_Rented FROM Manufacturer AS M, Equipment AS E, Has_Rental AS H WHERE M.M_ID = E.M_ID AND E.SerialNo = H.SerialNo GROUP BY M.M_ID ORDER BY Total_Rented DESC LIMIT 1;";

        try (Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){  

            if (rs.next()) {
                String manufacturer = rs.getString("Manufacturer");
                int total = rs.getInt("Total_Rented");
                System.out.println(manufacturer + " - " + total);
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static void popularDrone() {
        System.out.println("Popular Drone");
    }
    private static void ItemsOut() {
        System.out.println("Items Checked Out");
    }
    private static void EquipByType() {
        System.out.println("Equipment by Type");
    }

}

