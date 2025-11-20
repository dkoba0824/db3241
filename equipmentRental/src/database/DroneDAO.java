package database;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Data Access Object for Drone delivery operations
 * Uses PreparedStatements to prevent SQL injection attacks
 */
public class DroneDAO {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Schedule delivery of rented equipment using a drone
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean scheduleDelivery(int memberId, int equipmentSerialNo, int droneId) {
        String sql = "INSERT INTO DRONE_DELIVERS (Drone_ID, User_ID, SerialNo, Delivery_Date) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String droneIdStr = String.format("D%07d", droneId);
            String userIdStr = String.format("U%08d", memberId);
            String equipmentIdStr = String.format("EQ%05d", equipmentSerialNo);
            String deliveryDate = LocalDate.now().format(DATE_FORMAT);
            
            // Check if drone exists and is available
            if (!isDroneAvailable(droneId)) {
                System.out.println("Drone is not available for delivery.");
                return false;
            }
            
            pstmt.setString(1, droneIdStr);
            pstmt.setString(2, userIdStr);
            pstmt.setString(3, equipmentIdStr);
            pstmt.setString(4, deliveryDate);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Delivery scheduled successfully!");
                System.out.println("Drone " + droneIdStr + " will deliver equipment to member " + memberId);
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println("Error scheduling delivery: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Schedule pickup of returned equipment using a drone
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean schedulePickup(int memberId, int equipmentSerialNo, int droneId) {
        // For this implementation, pickup is similar to delivery
        // In a real system, you might have a separate DRONE_PICKUPS table
        String sql = "INSERT INTO DRONE_DELIVERS (Drone_ID, User_ID, SerialNo, Delivery_Date) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String droneIdStr = String.format("D%07d", droneId);
            String userIdStr = String.format("U%08d", memberId);
            String equipmentIdStr = String.format("EQ%05d", equipmentSerialNo);
            String pickupDate = LocalDate.now().format(DATE_FORMAT);
            
            if (!isDroneAvailable(droneId)) {
                System.out.println("Drone is not available for pickup.");
                return false;
            }
            
            pstmt.setString(1, droneIdStr);
            pstmt.setString(2, userIdStr);
            pstmt.setString(3, equipmentIdStr);
            pstmt.setString(4, pickupDate);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Pickup scheduled successfully!");
                System.out.println("Drone " + droneIdStr + " will pick up equipment from member " + memberId);
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println("Error scheduling pickup: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Check if a drone is available for assignment
     * Uses PreparedStatement to prevent SQL injection
     */
    private static boolean isDroneAvailable(int droneId) {
        String sql = "SELECT Status FROM DRONES WHERE Drone_ID = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("D%07d", droneId));
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String status = rs.getString("Status");
                return "Available".equalsIgnoreCase(status) || 
                       "Operational".equalsIgnoreCase(status);
            }
            
        } catch (SQLException e) {
            System.out.println("Error checking drone availability: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get list of available drones
     */
    public static void displayAvailableDrones() {
        String sql = "SELECT Drone_ID, Model, Battery_Cap, Fly_Dist, Status " +
                     "FROM DRONES " +
                     "WHERE Status IN ('Available', 'Operational') " +
                     "ORDER BY Drone_ID";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== Available Drones ===");
            boolean hasDrones = false;
            
            while (rs.next()) {
                hasDrones = true;
                String droneIdStr = rs.getString("Drone_ID");
                int droneId = Integer.parseInt(droneIdStr.substring(1));
                
                System.out.println("\nDrone ID: " + droneId);
                System.out.println("Model: " + rs.getString("Model"));
                System.out.println("Battery Capacity: " + rs.getDouble("Battery_Cap") + "%");
                System.out.println("Flight Distance: " + rs.getDouble("Fly_Dist") + " miles");
                System.out.println("Status: " + rs.getString("Status"));
                System.out.println("---");
            }
            
            if (!hasDrones) {
                System.out.println("No available drones found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving available drones: " + e.getMessage());
        }
    }
    
    /**
     * Display delivery history for a member
     * Uses PreparedStatement to prevent SQL injection
     */
    public static void displayMemberDeliveries(int memberId) {
        String sql = "SELECT dd.Drone_ID, dd.SerialNo, dd.Delivery_Date, e.Descrip " +
                     "FROM DRONE_DELIVERS dd " +
                     "JOIN EQUIPMENT e ON dd.SerialNo = e.SerialNo " +
                     "WHERE dd.User_ID = ? " +
                     "ORDER BY dd.Delivery_Date DESC";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("U%08d", memberId));
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n=== Delivery History for Member " + memberId + " ===");
            boolean hasDeliveries = false;
            
            while (rs.next()) {
                hasDeliveries = true;
                System.out.println("\nDrone ID: " + rs.getString("Drone_ID"));
                System.out.println("Equipment: " + rs.getString("Descrip"));
                System.out.println("Serial No: " + rs.getString("SerialNo"));
                System.out.println("Delivery Date: " + rs.getString("Delivery_Date"));
                System.out.println("---");
            }
            
            if (!hasDeliveries) {
                System.out.println("No delivery history found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving delivery history: " + e.getMessage());
        }
    }
}
