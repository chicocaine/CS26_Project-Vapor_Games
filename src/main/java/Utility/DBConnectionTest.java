package Utility;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) {
        try (Connection connection = DBConnectionPool.getConnection()) {
            if (connection != null) {
                System.out.println("Successfully connected to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database.");
        } finally {
            DBConnectionPool.close(); // Ensure resources are cleaned up when shutting down
        }
    }
}
