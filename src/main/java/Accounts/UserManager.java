package Accounts;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utility.DBConnectionPool;
import Utility.ProfilePictureUtility;
import org.mindrot.jbcrypt.BCrypt;

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

                if (BCrypt.checkpw(password, hashedPassword)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false;
    }

    public User loadUserSession(String username, String password) {
        if (userAuth(username, password)) {
            try (Connection conn = DBConnectionPool.getConnection()) {
                // Update query to include the pfpURL field (profile picture URL)
                String query = "SELECT userID, username, wallet, name, pfpURL FROM users WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                // If a user is found, create and return the User object
                if (rs.next()) {
                    int userID = rs.getInt("userID");
                    String retrievedUsername = rs.getString("username");
                    double wallet = rs.getDouble("wallet");
                    String name = rs.getString("name");
                    String pfpURL = rs.getString("pfpURL");  // Retrieve the profile picture URL (UUID or path)

                    // Create a new User object and return it
                    return new User(userID, retrievedUsername, wallet, name, pfpURL);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Register a new user
    public void registerUser(String username, String password, String name, double wallet, File photoFile) {
        final String localdir = "uploads/profile_pics";
        ProfilePictureUtility pfpu = new ProfilePictureUtility();
        String pfpURL = null;

        try {
            pfpURL = pfpu.saveProfilePicture(photoFile, localdir);
        } catch (IOException e) {
            throw new RuntimeException("Error saving profile picture: " + e.getMessage(), e);
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String query = "INSERT INTO users (username, password, name, wallet, pfpURL) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, name);
            stmt.setDouble(4, wallet);
            stmt.setString(5, pfpURL);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving user to the database: " + e.getMessage(), e);
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

        public boolean updateProfilePicture(int userID, File newPhotoFile) {
            final String localDir = "uploads/profile_pics";  // Directory where photos are saved
            ProfilePictureUtility pfpu = new ProfilePictureUtility();

            String newPfpURL = null;
            try {
                newPfpURL = pfpu.saveProfilePicture(newPhotoFile, localDir);
            } catch (IOException e) {
                throw new RuntimeException("Error saving profile picture: " + e.getMessage(), e);
            }

            String updateQuery = "UPDATE users SET pfpURL = ? WHERE userID = ?";

            try (Connection conn = DBConnectionPool.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

                stmt.setString(1, newPfpURL);
                stmt.setInt(2, userID);

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    deleteOldProfilePicture(userID);
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        private void deleteOldProfilePicture(int userID) {
            String selectQuery = "SELECT pfpURL FROM users WHERE userID = ?";

            try (Connection conn = DBConnectionPool.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

                stmt.setInt(1, userID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String oldPfpURL = rs.getString("pfpURL");
                    File oldFile = new File("uploads/profile_pics/" + oldPfpURL);
                    if (oldFile.exists() && oldFile.isFile()) {
                        oldFile.delete();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
