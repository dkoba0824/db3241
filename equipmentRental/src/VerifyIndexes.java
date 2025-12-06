import database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class VerifyIndexes {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("=== EXISTING INDEXES ===\n");
            
            // List all indexes
            String sql = "SELECT name, tbl_name, sql FROM sqlite_master WHERE type='index' AND sql IS NOT NULL ORDER BY tbl_name";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                System.out.println("Index: " + rs.getString("name"));
                System.out.println("Table: " + rs.getString("tbl_name"));
                System.out.println("SQL: " + rs.getString("sql"));
                System.out.println();
            }
            
            System.out.println("\n=== PERFORMANCE TEST ===\n");
            
            // Test query performance WITH index
            long start = System.nanoTime();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM EQUIPMENT WHERE Model LIKE '%Pro%'");
            int count1 = 0;
            while (rs1.next()) count1++;
            long end = System.nanoTime();
            
            System.out.println("Query: SELECT * FROM EQUIPMENT WHERE Model LIKE '%Pro%'");
            System.out.println("Results found: " + count1);
            System.out.println("Time with index: " + (end - start) / 1000000.0 + " ms");
            System.out.println();
            
            // Test query on Drones
            start = System.nanoTime();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM Drones WHERE Model LIKE '%FP%'");
            int count2 = 0;
            while (rs2.next()) count2++;
            end = System.nanoTime();
            
            System.out.println("Query: SELECT * FROM Drones WHERE Model LIKE '%FP%'");
            System.out.println("Results found: " + count2);
            System.out.println("Time with index: " + (end - start) / 1000000.0 + " ms");
            System.out.println();
            
            // Test query on Customers
            start = System.nanoTime();
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM CUSTOMERS WHERE L_name LIKE 'S%'");
            int count3 = 0;
            while (rs3.next()) count3++;
            end = System.nanoTime();
            
            System.out.println("Query: SELECT * FROM CUSTOMERS WHERE L_name LIKE 'S%'");
            System.out.println("Results found: " + count3);
            System.out.println("Time with index: " + (end - start) / 1000000.0 + " ms");
            System.out.println();
            
            // Show EXPLAIN QUERY PLAN
            System.out.println("\n=== QUERY PLAN (shows if index is used) ===\n");
            ResultSet rs4 = stmt.executeQuery("EXPLAIN QUERY PLAN SELECT * FROM EQUIPMENT WHERE Model = 'Pro-Series-862'");
            while (rs4.next()) {
                System.out.println(rs4.getString("detail"));
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
