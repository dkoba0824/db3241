// Reference other files
import database.Database;
import utilities.Utilities;

import menus.EquipmentMenu;
import menus.MemberMenu;
import menus.DroneMenu;
import menus.RentalMenu;
import menus.ReportsMenu;
import menus.TransactionsMenu;

import entities.Equipment;
import entities.Member;

public class Main {

    // Main Menu
    public static void main(String[] args) {
        
        //Connect to DB
        Database.testConnection();

        int choice;

        do {
            System.out.println("\n--- Company Management System ---\n");
            System.out.println("1. Equipment");
            System.out.println("2. Drone Fleet");
            System.out.println("3. Members");
            System.out.println("4. Rental and Delivery Options");
            System.out.println("5. Reports");
            System.out.println("6. Transactions");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> EquipmentMenu.equipmentMenu();
                case 2 -> DroneMenu.droneMenu();
                case 3 -> MemberMenu.memberMenu();
                case 4 -> RentalMenu.rentalMenu();
                case 5 -> ReportsMenu.reportsMenu();
                case 6 -> TransactionsMenu.transactionsMenu();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }
}
