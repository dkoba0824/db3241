package menus;

import database.Database;
import utilities.Utilities;
import java.util.Scanner;

public class MemberMenu {

    private static final Scanner scanner = new Scanner(System.in);

    // Member Menu
    public static void memberMenu() {
        int choice;
        do {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. Add Member");
            System.out.println("2. View Members");
            System.out.println("3. Update Member Info");
            System.out.println("4. Remove Member");
            System.out.println("5. Search Member");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> addMember();
                case 2 -> viewMembers();
                case 3 -> updateMember();
                case 4 -> removeMember();
                case 5 -> searchMember();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addMember() {
        System.out.println("Adding new member...");
        System.out.print("Enter member ID (5 digits): ");
        int id = Utilities.getIntInput();

        System.out.print("Enter member first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter member last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter member type: ");
        String type = scanner.nextLine();
        System.out.print("Enter member phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member join date (YYYY-MM-DD): ");
        String joinDate = scanner.nextLine();
        System.out.print("Enter member address: ");
        String address = scanner.nextLine();
        System.out.print("Enter member distance from warehouse (miles): ");
        int distance = Utilities.getIntInput();

        String sqlCustomer = "INSERT INTO CUSTOMERS (User_ID, F_Name, L_Name, User_Type, Join_Date, Address, Ware_Dist) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsCustomer = Database.executeUpdate(sqlCustomer, String.format("USR%05d", id), firstName, lastName, type, joinDate, address, distance);
        
        
        if (rowsCustomer > 0) {
            // Insert into CUSTOMER_PHONES table
            String sqlPhone = "INSERT INTO CUSTOMER_PHONES (User_ID, Phone) VALUES (?, ?)";
            Database.executeUpdate(sqlPhone, String.format("USR%05d", id), phone);
            
            // Insert into CUSTOMER_EMAILS table
            String sqlEmail = "INSERT INTO CUSTOMER_EMAILS (User_ID, Email) VALUES (?, ?)";
            Database.executeUpdate(sqlEmail, String.format("USR%05d", id), email);
            
            System.out.println("Member added successfully!");
        } else {
            System.out.println("Failed to add member.");
        }
    }

    private static void viewMembers() {
        System.out.println("Viewing all members...");
        String sql = "SELECT * FROM CUSTOMERS ORDER BY CAST(SUBSTR(User_ID, 4) AS INTEGER)";
        Database.runQuery(sql);
    }

    private static void updateMember() {
        System.out.println("Updating member information...");
        System.out.print("Enter member ID to update (5 digits): ");
        int id = Utilities.getIntInput();

        System.out.print("Enter updated member first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter updated member last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter updated member type: ");
        String type = scanner.nextLine();
        System.out.print("Enter updated member phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter updated member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter updated member join date (YYYY-MM-DD): ");
        String joinDate = scanner.nextLine();
        System.out.print("Enter updated member address: ");
        String address = scanner.nextLine();
        System.out.print("Enter updated member distance from warehouse (miles): ");
        double distance = Utilities.getDoubleInput();

        // Update CUSTOMERS table
        String sqlCustomer = "UPDATE CUSTOMERS SET F_Name = ?, L_Name = ?, User_Type = ?, Join_Date = ?, Address = ?, Ware_Dist = ? WHERE User_ID = ?";
        int rowsCustomer = Database.executeUpdate(sqlCustomer, firstName, lastName, type, joinDate, address, distance, String.format("USR%05d", id));
        
        if (rowsCustomer > 0) {
            // Update CUSTOMER_PHONES table
            String sqlPhone = "UPDATE CUSTOMER_PHONES SET Phone = ? WHERE User_ID = ?";
            Database.executeUpdate(sqlPhone, phone, String.format("USR%05d", id));
            
            // Update CUSTOMER_EMAILS table
            String sqlEmail = "UPDATE CUSTOMER_EMAILS SET Email = ? WHERE User_ID = ?";
            Database.executeUpdate(sqlEmail, email, String.format("USR%05d", id));
            
            System.out.println("Member ID " + id + " updated successfully!");
        } else {
            System.out.println("Member not found or failed to update.");
        }
    }

    private static void removeMember() {
        System.out.println("Removing member...");
        System.out.print("Enter member ID to remove (5 digits): ");
        int id = Utilities.getIntInput();
        
        // Delete from CUSTOMER_PHONES table (foreign key)
        String sqlPhone = "DELETE FROM CUSTOMER_PHONES WHERE User_ID = ?";
        Database.executeUpdate(sqlPhone, String.format("USR%05d", id));
        
        // Delete from CUSTOMER_EMAILS table (foreign key)
        String sqlEmail = "DELETE FROM CUSTOMER_EMAILS WHERE User_ID = ?";
        Database.executeUpdate(sqlEmail, String.format("USR%05d", id));
        
        // Delete from CUSTOMERS table
        String sqlCustomer = "DELETE FROM CUSTOMERS WHERE User_ID = ?";
        int rows = Database.executeUpdate(sqlCustomer, String.format("USR%05d", id));
        
        if (rows > 0) {
            System.out.println("Member removed successfully!");
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void searchMember() {
        System.out.print("Enter member ID (5 digits): ");
        int id = Utilities.getIntInput();
        
        String sql = "SELECT * FROM CUSTOMERS WHERE User_ID = ?";
        Database.runQuery(sql, String.format("USR%05d", id));
    }
}