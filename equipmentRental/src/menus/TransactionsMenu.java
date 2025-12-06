package menus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import database.Database;
import utilities.Utilities;

public class TransactionsMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void transactionsMenu() {
        int choice;
        do {
            System.out.println("\n--- Transactions Menu ---");
            System.out.println("1. Update Customer Email");
            System.out.println("2. Update Customer Phone");
            System.out.println("3. Add New Review");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter choice: ");

            choice = Utilities.getIntInput();

            switch (choice) {
                case 1 -> updateCustomerEmail();
                case 2 -> updateCustomerPhone();
                case 3 -> addNewReview();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    private static void updateCustomerEmail() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();

        String sql = "UPDATE CUSTOMER_EMAILS SET Email = ? WHERE USER_ID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); 

            stmt.setString(1, newEmail);
            stmt.setString(2, userId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                conn.commit();
                System.out.println("Transaction complete. Email updated.");
            } else {
                conn.rollback();
                System.out.println("Transaction rolled back. No rows updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateCustomerPhone() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter new phone number: ");
        String newPhone = scanner.nextLine();

        String sql = "UPDATE CUSTOMER_PHONES SET Phone = ? WHERE USER_ID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); 

            stmt.setString(1, newPhone);
            stmt.setString(2, userId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                conn.commit();
                System.out.println("Transaction complete. Phone updated.");
            } else {
                conn.rollback();
                System.out.println("Transaction rolled back. No rows updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addNewReview() {
        try (Connection conn = Database.getConnection();
            Scanner cin = new Scanner(System.in)) {

            conn.setAutoCommit(false);

            // Ask user for review info
            System.out.print("Enter Review ID (unique): ");
            String reviewID = cin.nextLine();
            System.out.print("Enter Rating (integer 1-5): ");
            int rating = Integer.parseInt(cin.nextLine());
            System.out.print("Enter Comment: ");
            String comment = cin.nextLine();
            System.out.print("Enter User ID: ");
            String userID = cin.nextLine();
            System.out.print("Enter Equipment Serial: ");
            String eqSerial = cin.nextLine();

            String insertReviewSQL = "INSERT INTO REVIEWS (R_ID, Rating, Comment, User_ID, EQ_Serial) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement reviewStatement = conn.prepareStatement(insertReviewSQL);
            reviewStatement.setString(1, reviewID);
            reviewStatement.setInt(2, rating);
            reviewStatement.setString(3, comment);
            reviewStatement.setString(4, userID);
            reviewStatement.setString(5, eqSerial);

            int rowsInserted = reviewStatement.executeUpdate();

            if (rowsInserted > 0) {
                conn.commit();
                System.out.println("Transaction complete. Review added.");
            } else {
                conn.rollback();
                System.out.println("Transaction rolled back. No review added.");
            }

            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}