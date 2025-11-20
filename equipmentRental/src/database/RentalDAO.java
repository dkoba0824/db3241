package database;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Data Access Object for Rental operations
 * Uses PreparedStatements to prevent SQL injection attacks
 */
public class RentalDAO {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Rent equipment to a member
     * Creates a rental record and updates equipment status
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean rentEquipment(int memberId, int equipmentSerialNo, String dueDate) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            String userIdStr = String.format("U%08d", memberId);
            String equipmentIdStr = String.format("EQ%05d", equipmentSerialNo);
            String today = LocalDate.now().format(DATE_FORMAT);
            
            // Insert rental record
            String rentalSql = "INSERT INTO RENTAL (User_ID, Check_Out, Due_Date, Returns, Fees) " +
                              "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(rentalSql)) {
                pstmt.setString(1, userIdStr);
                pstmt.setString(2, today);
                pstmt.setString(3, dueDate);
                pstmt.setString(4, "No");
                pstmt.setDouble(5, 0.0);
                pstmt.executeUpdate();
            }
            
            // Insert HAS_RENTAL relationship
            String hasRentalSql = "INSERT INTO HAS_RENTAL (User_ID, Check_Out, SerialNo) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(hasRentalSql)) {
                pstmt.setString(1, userIdStr);
                pstmt.setString(2, today);
                pstmt.setString(3, equipmentIdStr);
                pstmt.executeUpdate();
            }
            
            // Update equipment status and quantity
            String updateEquipmentSql = "UPDATE EQUIPMENT SET Status = 'Rented', " +
                                       "Quantity = Quantity - 1 WHERE SerialNo = ? AND Quantity > 0";
            try (PreparedStatement pstmt = conn.prepareStatement(updateEquipmentSql)) {
                pstmt.setString(1, equipmentIdStr);
                int updated = pstmt.executeUpdate();
                if (updated == 0) {
                    throw new SQLException("Equipment not available or out of stock");
                }
            }
            
            conn.commit();
            System.out.println("Equipment rented successfully!");
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error renting equipment: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Return rented equipment
     * Updates rental record and equipment status
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean returnEquipment(int memberId, int equipmentSerialNo) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);
            
            String userIdStr = String.format("U%08d", memberId);
            String equipmentIdStr = String.format("EQ%05d", equipmentSerialNo);
            
            // Find the active rental
            String findRentalSql = "SELECT Check_Out, Due_Date FROM RENTAL " +
                                  "WHERE User_ID = ? AND Returns = 'No' " +
                                  "AND Check_Out IN (SELECT Check_Out FROM HAS_RENTAL " +
                                  "WHERE User_ID = ? AND SerialNo = ?) LIMIT 1";
            
            String checkOutDate;
            String dueDate;
            
            try (PreparedStatement pstmt = conn.prepareStatement(findRentalSql)) {
                pstmt.setString(1, userIdStr);
                pstmt.setString(2, userIdStr);
                pstmt.setString(3, equipmentIdStr);
                ResultSet rs = pstmt.executeQuery();
                
                if (!rs.next()) {
                    throw new SQLException("No active rental found for this equipment and member");
                }
                
                checkOutDate = rs.getString("Check_Out");
                dueDate = rs.getString("Due_Date");
            }
            
            // Calculate late fees if applicable
            LocalDate due = LocalDate.parse(dueDate, DATE_FORMAT);
            LocalDate today = LocalDate.now();
            double lateFee = 0.0;
            
            if (today.isAfter(due)) {
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(due, today);
                lateFee = daysLate * 5.0; // $5 per day late fee
            }
            
            // Update rental record
            String updateRentalSql = "UPDATE RENTAL SET Returns = 'Yes', Fees = ? " +
                                    "WHERE User_ID = ? AND Check_Out = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateRentalSql)) {
                pstmt.setDouble(1, lateFee);
                pstmt.setString(2, userIdStr);
                pstmt.setString(3, checkOutDate);
                pstmt.executeUpdate();
            }
            
            // Update equipment status and quantity
            String updateEquipmentSql = "UPDATE EQUIPMENT SET Status = 'Available', " +
                                       "Quantity = Quantity + 1 WHERE SerialNo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateEquipmentSql)) {
                pstmt.setString(1, equipmentIdStr);
                pstmt.executeUpdate();
            }
            
            conn.commit();
            
            if (lateFee > 0) {
                System.out.println("Equipment returned. Late fee: $" + String.format("%.2f", lateFee));
            } else {
                System.out.println("Equipment returned successfully!");
            }
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error returning equipment: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Get rental history for a member
     * Uses PreparedStatement to prevent SQL injection
     */
    public static void displayMemberRentals(int memberId) {
        String sql = "SELECT r.Check_Out, r.Due_Date, r.Returns, r.Fees, e.Descrip, e.SerialNo " +
                     "FROM RENTAL r " +
                     "JOIN HAS_RENTAL hr ON r.User_ID = hr.User_ID AND r.Check_Out = hr.Check_Out " +
                     "JOIN EQUIPMENT e ON hr.SerialNo = e.SerialNo " +
                     "WHERE r.User_ID = ? " +
                     "ORDER BY r.Check_Out DESC";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("U%08d", memberId));
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n=== Rental History for Member " + memberId + " ===");
            boolean hasRentals = false;
            
            while (rs.next()) {
                hasRentals = true;
                System.out.println("\nEquipment: " + rs.getString("Descrip"));
                System.out.println("Serial No: " + rs.getString("SerialNo"));
                System.out.println("Check Out: " + rs.getString("Check_Out"));
                System.out.println("Due Date: " + rs.getString("Due_Date"));
                System.out.println("Returned: " + rs.getString("Returns"));
                System.out.println("Fees: $" + String.format("%.2f", rs.getDouble("Fees")));
                System.out.println("---");
            }
            
            if (!hasRentals) {
                System.out.println("No rental history found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving rental history: " + e.getMessage());
        }
    }
}
