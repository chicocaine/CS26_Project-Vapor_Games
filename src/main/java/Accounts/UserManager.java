package Accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.mindrot.jbcrypt.BCrypt;
import Utility.DBConnectionPool;

public class UserManager {
    
    public UserManager () {}

    public boolean userAuth(String username, String password) {
        try (Connection conn = DBConnectionPool.getConnection();) {
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                //if (BCrypt.checkpw(password, hashedPassword)) {
                    //return true;
                //}
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false;
    }

    public User loadUserSession(String username, String password) {
        if (userAuth(username, password)) {
            try (Connection conn = DBConnectionPool.getConnection()) {
                String query = "SELECT userID, username, wallet, name FROM users WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    int userID = rs.getInt("userID");
                    String retrievedUsername = rs.getString("username");
                    double wallet = rs.getDouble("wallet");
                    String name = rs.getString("name");
    
                    //User user = new User(userID, retrievedUsername, wallet, name);
                    //return user;
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
        return null;
    }

    public void registerUser(String username, String password, String name, double wallet) {
        //String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hash the password
        try (Connection conn = DBConnectionPool.getConnection()) {
            String query = "INSERT INTO users (username, password, name, wallet) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            //stmt.setString(2, hashedPassword);
            stmt.setString(3, name);
            stmt.setDouble(4, wallet);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(User user, String password) {
        String username = user.getUserName();
        if (userAuth(username, password)) {
            int userID = user.getUserID();
            try (Connection conn = DBConnectionPool.getConnection()) {
                String deleteQuery = "DELETE FROM users WHERE userID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                    stmt.setInt(1, userID);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User removed successfully.");
                    } else {
                        System.out.println("Failed to remove user. User may not exist.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Authentication failed. Unable to remove user.");
        }
    }
    
    public boolean checkUsernameExists(String username) {
        try (Connection conn = DBConnectionPool.getConnection()) {
            String query = "SELECT userID FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false;
    }    

    public class UserUpdates {

        public UserUpdates() {}
    
        public void updatePassword(User user, String newPassword) {
            int userID = user.getUserID();
            String query = "UPDATE users SET password = ? WHERE userID = ?";
    
            try (Connection conn = DBConnectionPool.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                
                stmt.setString(1, hashedPassword);
                stmt.setInt(2, userID);
    
                int rowsAffected = stmt.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Password updated successfully.");
                } else {
                    System.out.println("Failed to update password. User may not exist.");
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        public void updateWalletBalance(int userID, double newWalletBalance) {
            String query = "UPDATE users SET wallet = ? WHERE userID = ?";
    
            try (Connection conn = DBConnectionPool.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
    
                stmt.setDouble(1, newWalletBalance);
                stmt.setInt(2, userID);
    
                int rowsAffected = stmt.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Wallet balance updated successfully.");
                } else {
                    System.out.println("Failed to update wallet balance. User may not exist.");
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        // Method to update the user's name
        public void updateName(User user, String newName) {
            int userID = user.getUserID();
            String query = "UPDATE users SET name = ? WHERE userID = ?";
    
            try (Connection conn = DBConnectionPool.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
    
                stmt.setString(1, newName);
                stmt.setInt(2, userID);
    
                int rowsAffected = stmt.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Name updated successfully.");
                } else {
                    System.out.println("Failed to update name. User may not exist.");
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
