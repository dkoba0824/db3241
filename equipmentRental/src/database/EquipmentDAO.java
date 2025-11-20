package database;

import entities.Equipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Equipment entity
 * Uses PreparedStatements to prevent SQL injection attacks
 */
public class EquipmentDAO {
    
    /**
     * Add new equipment to the database
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean addEquipment(Equipment equipment) {
        String sql = "INSERT INTO EQUIPMENT (SerialNo, Model, Quantity, Weight, Location, " +
                     "Status, Descrip, Dimensions, Year, Type, M_ID, OrderNo, Warranty_Exp) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set parameters using PreparedStatement (prevents SQL injection)
            pstmt.setString(1, String.format("EQ%05d", equipment.getSerialNumber()));
            pstmt.setString(2, equipment.getModel());
            pstmt.setInt(3, equipment.getQuantity());
            pstmt.setInt(4, equipment.getWeight());
            pstmt.setString(5, equipment.getLocation());
            pstmt.setString(6, equipment.isAvailable() ? "Available" : "Rented");
            pstmt.setString(7, equipment.getDescription());
            pstmt.setString(8, equipment.getDimensions());
            pstmt.setInt(9, equipment.getYear());
            pstmt.setString(10, equipment.getType());
            pstmt.setString(11, equipment.getManufacturerId());
            pstmt.setString(12, equipment.getOrderNumber());
            pstmt.setString(13, equipment.getWarrantyExp());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding equipment: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update existing equipment in the database
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean updateEquipment(Equipment equipment) {
        String sql = "UPDATE EQUIPMENT SET Model = ?, Quantity = ?, Weight = ?, Location = ?, " +
                     "Status = ?, Descrip = ?, Dimensions = ?, Year = ?, Type = ?, " +
                     "M_ID = ?, OrderNo = ?, Warranty_Exp = ? WHERE SerialNo = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, equipment.getModel());
            pstmt.setInt(2, equipment.getQuantity());
            pstmt.setInt(3, equipment.getWeight());
            pstmt.setString(4, equipment.getLocation());
            pstmt.setString(5, equipment.isAvailable() ? "Available" : "Rented");
            pstmt.setString(6, equipment.getDescription());
            pstmt.setString(7, equipment.getDimensions());
            pstmt.setInt(8, equipment.getYear());
            pstmt.setString(9, equipment.getType());
            pstmt.setString(10, equipment.getManufacturerId());
            pstmt.setString(11, equipment.getOrderNumber());
            pstmt.setString(12, equipment.getWarrantyExp());
            pstmt.setString(13, String.format("EQ%05d", equipment.getSerialNumber()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating equipment: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete equipment from the database by serial number
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean deleteEquipment(int serialNumber) {
        String sql = "DELETE FROM EQUIPMENT WHERE SerialNo = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("EQ%05d", serialNumber));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting equipment: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Search equipment by serial number
     * Uses PreparedStatement to prevent SQL injection
     */
    public static Equipment findBySerialNumber(int serialNumber) {
        String sql = "SELECT * FROM EQUIPMENT WHERE SerialNo = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("EQ%05d", serialNumber));
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractEquipmentFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error finding equipment: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Search equipment by description (partial match)
     * Uses PreparedStatement to prevent SQL injection
     */
    public static List<Equipment> searchByDescription(String description) {
        String sql = "SELECT * FROM EQUIPMENT WHERE Descrip LIKE ?";
        List<Equipment> equipmentList = new ArrayList<>();
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Use LIKE with wildcards, but parameterized to prevent injection
            pstmt.setString(1, "%" + description + "%");
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                equipmentList.add(extractEquipmentFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error searching equipment: " + e.getMessage());
        }
        
        return equipmentList;
    }
    
    /**
     * Search equipment by type
     * Uses PreparedStatement to prevent SQL injection
     */
    public static List<Equipment> searchByType(String type) {
        String sql = "SELECT * FROM EQUIPMENT WHERE Type = ?";
        List<Equipment> equipmentList = new ArrayList<>();
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, type);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                equipmentList.add(extractEquipmentFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error searching equipment by type: " + e.getMessage());
        }
        
        return equipmentList;
    }
    
    /**
     * Get all equipment from the database
     */
    public static List<Equipment> getAllEquipment() {
        String sql = "SELECT * FROM EQUIPMENT ORDER BY SerialNo";
        List<Equipment> equipmentList = new ArrayList<>();
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                equipmentList.add(extractEquipmentFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving equipment: " + e.getMessage());
        }
        
        return equipmentList;
    }
    
    /**
     * Helper method to extract Equipment object from ResultSet
     */
    private static Equipment extractEquipmentFromResultSet(ResultSet rs) throws SQLException {
        // Extract serial number from format "EQ00001" to integer 1
        String serialNoStr = rs.getString("SerialNo");
        int serialNo = Integer.parseInt(serialNoStr.substring(2));
        
        return new Equipment(
            serialNo,
            rs.getString("Descrip"),
            rs.getString("Type"),
            rs.getString("Model"),
            rs.getInt("Year"),
            rs.getString("Dimensions"),
            rs.getInt("Weight"),
            rs.getString("Location"),
            rs.getInt("Quantity"),
            rs.getString("Status").equals("Available"),
            rs.getString("M_ID"),
            rs.getString("OrderNo"),
            rs.getString("Warranty_Exp")
        );
    }
}
