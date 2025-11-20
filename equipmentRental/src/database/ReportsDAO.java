package database;

import java.sql.*;

/**
 * Data Access Object for generating useful reports
 * Uses PreparedStatements to prevent SQL injection attacks
 */
public class ReportsDAO {
    
    /**
     * Report 1: Find total number of equipment items rented by a single member
     * Uses PreparedStatement to prevent SQL injection
     */
    public static void reportRentingCheckouts(int memberId) {
        String sql = "SELECT COUNT(*) as total_rentals, " +
                     "c.F_Name, c.L_Name " +
                     "FROM HAS_RENTAL hr " +
                     "JOIN CUSTOMERS c ON hr.User_ID = c.User_ID " +
                     "WHERE hr.User_ID = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("U%08d", memberId));
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n=== Renting Checkouts Report ===");
            
            if (rs.next()) {
                String name = rs.getString("F_Name") + " " + rs.getString("L_Name");
                int total = rs.getInt("total_rentals");
                
                System.out.println("Member: " + name + " (ID: " + memberId + ")");
                System.out.println("Total equipment items rented: " + total);
            } else {
                System.out.println("No rental data found for member ID: " + memberId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating renting checkouts report: " + e.getMessage());
        }
    }
    
    /**
     * Report 2: Find the most popular item in the database
     * Based on rental frequency and rental time
     */
    public static void reportMostPopularItem() {
        String sql = "SELECT e.SerialNo, e.Descrip, e.Type, " +
                     "COUNT(hr.SerialNo) as rental_count, " +
                     "AVG(julianday(r.Due_Date) - julianday(r.Check_Out)) as avg_rental_days " +
                     "FROM EQUIPMENT e " +
                     "JOIN HAS_RENTAL hr ON e.SerialNo = hr.SerialNo " +
                     "JOIN RENTAL r ON hr.User_ID = r.User_ID AND hr.Check_Out = r.Check_Out " +
                     "GROUP BY e.SerialNo, e.Descrip, e.Type " +
                     "ORDER BY rental_count DESC, avg_rental_days DESC " +
                     "LIMIT 1";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== Most Popular Item Report ===");
            
            if (rs.next()) {
                System.out.println("Equipment: " + rs.getString("Descrip"));
                System.out.println("Serial No: " + rs.getString("SerialNo"));
                System.out.println("Type: " + rs.getString("Type"));
                System.out.println("Times Rented: " + rs.getInt("rental_count"));
                System.out.println("Average Rental Duration: " + 
                                 String.format("%.1f", rs.getDouble("avg_rental_days")) + " days");
            } else {
                System.out.println("No rental data available.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating most popular item report: " + e.getMessage());
        }
    }
    
    /**
     * Report 3: Find the most frequent equipment manufacturer
     * Based on number of rented units
     */
    public static void reportMostPopularManufacturer() {
        String sql = "SELECT m.M_ID, m.M_Name, COUNT(hr.SerialNo) as units_rented " +
                     "FROM MANUFACTURER m " +
                     "JOIN EQUIPMENT e ON m.M_ID = e.M_ID " +
                     "JOIN HAS_RENTAL hr ON e.SerialNo = hr.SerialNo " +
                     "GROUP BY m.M_ID, m.M_Name " +
                     "ORDER BY units_rented DESC " +
                     "LIMIT 1";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== Most Popular Manufacturer Report ===");
            
            if (rs.next()) {
                System.out.println("Manufacturer: " + rs.getString("M_Name"));
                System.out.println("Manufacturer ID: " + rs.getString("M_ID"));
                System.out.println("Total Units Rented: " + rs.getInt("units_rented"));
            } else {
                System.out.println("No manufacturer data available.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating most popular manufacturer report: " + e.getMessage());
        }
    }
    
    /**
     * Report 4: Find the most used drone in the database
     * Based on flying distance and number of deliveries
     */
    public static void reportMostPopularDrone() {
        String sql = "SELECT d.Drone_ID, d.Model, " +
                     "COUNT(dd.Drone_ID) as delivery_count, " +
                     "SUM(c.Ware_Dist) as total_distance " +
                     "FROM DRONES d " +
                     "JOIN DRONE_DELIVERS dd ON d.Drone_ID = dd.Drone_ID " +
                     "JOIN CUSTOMERS c ON dd.User_ID = c.User_ID " +
                     "GROUP BY d.Drone_ID, d.Model " +
                     "ORDER BY delivery_count DESC, total_distance DESC " +
                     "LIMIT 1";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== Most Popular Drone Report ===");
            
            if (rs.next()) {
                System.out.println("Drone ID: " + rs.getString("Drone_ID"));
                System.out.println("Model: " + rs.getString("Model"));
                System.out.println("Total Deliveries: " + rs.getInt("delivery_count"));
                System.out.println("Total Distance Traveled: " + 
                                 String.format("%.2f", rs.getDouble("total_distance")) + " miles");
            } else {
                System.out.println("No drone delivery data available.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating most popular drone report: " + e.getMessage());
        }
    }
    
    /**
     * Report 5: Find the member who has rented out the most items
     */
    public static void reportMemberWithMostRentals() {
        String sql = "SELECT c.User_ID, c.F_Name, c.L_Name, COUNT(hr.SerialNo) as total_items " +
                     "FROM CUSTOMERS c " +
                     "JOIN HAS_RENTAL hr ON c.User_ID = hr.User_ID " +
                     "GROUP BY c.User_ID, c.F_Name, c.L_Name " +
                     "ORDER BY total_items DESC " +
                     "LIMIT 1";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== Member with Most Rentals Report ===");
            
            if (rs.next()) {
                String name = rs.getString("F_Name") + " " + rs.getString("L_Name");
                System.out.println("Member: " + name);
                System.out.println("Member ID: " + rs.getString("User_ID"));
                System.out.println("Total Items Rented: " + rs.getInt("total_items"));
            } else {
                System.out.println("No rental data available.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating member with most rentals report: " + e.getMessage());
        }
    }
    
    /**
     * Report 6: Find equipment descriptions by type released before a specified year
     * Uses PreparedStatement to prevent SQL injection
     */
    public static void reportEquipmentByTypeAndYear(String type, int year) {
        String sql = "SELECT SerialNo, Descrip, Type, Year, Model " +
                     "FROM EQUIPMENT " +
                     "WHERE Type = ? AND Year < ? " +
                     "ORDER BY Year DESC, Descrip";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, type);
            pstmt.setInt(2, year);
            
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n=== Equipment by Type and Year Report ===");
            System.out.println("Type: " + type);
            System.out.println("Released before: " + year);
            System.out.println();
            
            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                System.out.println("Serial No: " + rs.getString("SerialNo"));
                System.out.println("Description: " + rs.getString("Descrip"));
                System.out.println("Model: " + rs.getString("Model"));
                System.out.println("Year: " + rs.getInt("Year"));
                System.out.println("---");
            }
            
            if (!hasResults) {
                System.out.println("No equipment found matching the criteria.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating equipment by type/year report: " + e.getMessage());
        }
    }
    
    /**
     * Display all available report options
     */
    public static void displayReportMenu() {
        System.out.println("\n=== Available Reports ===");
        System.out.println("1. Renting Checkouts (by member)");
        System.out.println("2. Most Popular Item");
        System.out.println("3. Most Popular Manufacturer");
        System.out.println("4. Most Popular Drone");
        System.out.println("5. Member with Most Rentals");
        System.out.println("6. Equipment by Type and Year");
        System.out.println("0. Back to Main Menu");
    }
}
