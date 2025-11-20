package database;

import entities.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Member/Customer entity
 * Uses PreparedStatements to prevent SQL injection attacks
 */
public class MemberDAO {
    
    /**
     * Add new member to the database
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean addMember(Member member) {
        String sql = "INSERT INTO CUSTOMERS (User_ID, User_Type, F_Name, L_Name, " +
                     "Join_Date, Ware_Dist, Address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("U%08d", member.getId()));
            pstmt.setString(2, member.getType());
            pstmt.setString(3, member.getFirstName());
            pstmt.setString(4, member.getLastName());
            pstmt.setString(5, member.getJoinDate());
            pstmt.setDouble(6, member.getWarehouseDistance());
            pstmt.setString(7, member.getAddress());
            
            int rowsAffected = pstmt.executeUpdate();
            
            // Add email and phone separately if needed
            if (rowsAffected > 0 && member.getEmail() != null) {
                addMemberEmail(member.getId(), member.getEmail());
            }
            if (rowsAffected > 0 && member.getPhone() != 0) {
                addMemberPhone(member.getId(), member.getPhone());
            }
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add member email (CUSTOMER_EMAILS table)
     */
    private static void addMemberEmail(int memberId, String email) {
        String sql = "INSERT INTO CUSTOMER_EMAILS (User_ID, Email) VALUES (?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("U%08d", memberId));
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error adding member email: " + e.getMessage());
        }
    }
    
    /**
     * Add member phone (CUSTOMER_PHONES table)
     */
    private static void addMemberPhone(int memberId, int phone) {
        String sql = "INSERT INTO CUSTOMER_PHONES (User_ID, Phone_No) VALUES (?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("U%08d", memberId));
            pstmt.setInt(2, phone);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error adding member phone: " + e.getMessage());
        }
    }
    
    /**
     * Update existing member in the database
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean updateMember(Member member) {
        String sql = "UPDATE CUSTOMERS SET User_Type = ?, F_Name = ?, L_Name = ?, " +
                     "Join_Date = ?, Ware_Dist = ?, Address = ? WHERE User_ID = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, member.getType());
            pstmt.setString(2, member.getFirstName());
            pstmt.setString(3, member.getLastName());
            pstmt.setString(4, member.getJoinDate());
            pstmt.setDouble(5, member.getWarehouseDistance());
            pstmt.setString(6, member.getAddress());
            pstmt.setString(7, String.format("U%08d", member.getId()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating member: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete member from the database by ID
     * Uses PreparedStatement to prevent SQL injection
     */
    public static boolean deleteMember(int memberId) {
        String sql = "DELETE FROM CUSTOMERS WHERE User_ID = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String userIdStr = String.format("U%08d", memberId);
            
            // Delete related emails and phones first
            deleteMemberEmails(userIdStr);
            deleteMemberPhones(userIdStr);
            
            pstmt.setString(1, userIdStr);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting member: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete member emails
     */
    private static void deleteMemberEmails(String userId) {
        String sql = "DELETE FROM CUSTOMER_EMAILS WHERE User_ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Ignore if table doesn't exist or no records
        }
    }
    
    /**
     * Delete member phones
     */
    private static void deleteMemberPhones(String userId) {
        String sql = "DELETE FROM CUSTOMER_PHONES WHERE User_ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Ignore if table doesn't exist or no records
        }
    }
    
    /**
     * Find member by ID
     * Uses PreparedStatement to prevent SQL injection
     */
    public static Member findById(int memberId) {
        String sql = "SELECT * FROM CUSTOMERS WHERE User_ID = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("U%08d", memberId));
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractMemberFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error finding member: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Search members by name (partial match on first or last name)
     * Uses PreparedStatement to prevent SQL injection
     */
    public static List<Member> searchByName(String name) {
        String sql = "SELECT * FROM CUSTOMERS WHERE F_Name LIKE ? OR L_Name LIKE ?";
        List<Member> members = new ArrayList<>();
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + name + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                members.add(extractMemberFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error searching members: " + e.getMessage());
        }
        
        return members;
    }
    
    /**
     * Get all members from the database
     */
    public static List<Member> getAllMembers() {
        String sql = "SELECT * FROM CUSTOMERS ORDER BY User_ID";
        List<Member> members = new ArrayList<>();
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                members.add(extractMemberFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving members: " + e.getMessage());
        }
        
        return members;
    }
    
    /**
     * Helper method to extract Member object from ResultSet
     */
    private static Member extractMemberFromResultSet(ResultSet rs) throws SQLException {
        // Extract user ID from format "U00000001" to integer 1
        String userIdStr = rs.getString("User_ID");
        int userId = Integer.parseInt(userIdStr.substring(1));
        
        // Get email and phone from separate tables
        String email = getMemberEmail(userIdStr);
        int phone = getMemberPhone(userIdStr);
        
        return new Member(
            userId,
            rs.getString("F_Name"),
            rs.getString("L_Name"),
            rs.getString("User_Type"),
            phone,
            email,
            rs.getString("Join_Date"),
            rs.getString("Address"),
            (int) rs.getDouble("Ware_Dist")
        );
    }
    
    /**
     * Get member email from CUSTOMER_EMAILS table
     */
    private static String getMemberEmail(String userId) {
        String sql = "SELECT Email FROM CUSTOMER_EMAILS WHERE User_ID = ? LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Email");
            }
        } catch (SQLException e) {
            // Return empty if no email found
        }
        return "";
    }
    
    /**
     * Get member phone from CUSTOMER_PHONES table
     */
    private static int getMemberPhone(String userId) {
        String sql = "SELECT Phone_No FROM CUSTOMER_PHONES WHERE User_ID = ? LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Phone_No");
            }
        } catch (SQLException e) {
            // Return 0 if no phone found
        }
        return 0;
    }
}
