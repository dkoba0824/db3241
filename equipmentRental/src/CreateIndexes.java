import database.Database;
import java.sql.Connection;
import java.sql.Statement;

public class CreateIndexes {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Creating indexes...");
            
            // Equipment Model Index
            stmt.execute("CREATE INDEX IF NOT EXISTS equipment_model_index ON EQUIPMENT(Model)");
            System.out.println("✓ Created equipment_model_index on EQUIPMENT(Model)");
            
            // Drone Model Index
            stmt.execute("CREATE INDEX IF NOT EXISTS drone_model_index ON Drones(Model)");
            System.out.println("✓ Created drone_model_index on Drones(Model)");
            
            // Customer Last Name Index
            stmt.execute("CREATE INDEX IF NOT EXISTS customer_name_index ON CUSTOMERS(L_name)");
            System.out.println("✓ Created customer_name_index on CUSTOMERS(L_name)");
            
            System.out.println("\nAll indexes created successfully!");
            
        } catch (Exception e) {
            System.out.println("Error creating indexes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
