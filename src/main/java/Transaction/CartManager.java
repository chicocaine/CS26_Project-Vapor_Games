package Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Accounts.User;
import Games.Games;
import Utility.DBConnectionPool;

public class CartManager {

    private ArrayList<Games> cart = new ArrayList<>();
    
    public CartManager () {}

    public void addToCart(User user, Games game) {
        int userID = user.getUserID();
        int gameID = game.getGameID(); // Assuming Games class has getGameID()
    
        String query = "INSERT INTO cart_games (userID, gameID) VALUES (?, ?)";
    
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, userID);
            stmt.setInt(2, gameID);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Game successfully added to the cart.");
            } else {
                System.out.println("Failed to add game to the cart.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removeFromCart(User user, Games game) {
        int userID = user.getUserID();
        int gameID = game.getGameID(); // Assuming Games class has getGameID()
    
        String query = "DELETE FROM cart_games WHERE userID = ? AND gameID = ?";
    
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, userID);
            stmt.setInt(2, gameID);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Game successfully removed from the cart.");
            } else {
                System.out.println("Failed to remove game from the cart. Game may not exist in the user's cart.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public void clearCart(User user) {
        int userID = user.getUserID();
        String query = "DELETE FROM cart_games WHERE userID = ?";
    
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, userID);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Cart cleared successfully for userID: " + userID);
            } else {
                System.out.println("No items found in the cart for userID: " + userID);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void loadCart (User user) {
        int userID = user.getUserID();
        String query = """
            SELECT 
                g.gameID,
                g.gameTitle,
                g.gameReleaseDate,
                g.description,
                g.price,
                g.available,
                g.pictureURL,
                gen.genreName AS genreName
            FROM 
                cart_games c
            INNER JOIN 
                games g ON c.gameID = g.gameID
            LEFT JOIN 
                genre_games gg ON g.gameID = gg.gameID
            LEFT JOIN 
                genres gen ON gg.genreID = gen.genreID
            WHERE 
                c.userID = ?;
        """;
    
        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, userID); // Bind the userID parameter
            
            try (ResultSet resultSet = statement.executeQuery()) {
                HashMap<Integer, Games> gamesMap = new HashMap<>();
    
                while (resultSet.next()) {
                    int gameID = resultSet.getInt("gameID");
                    String title = resultSet.getString("gameTitle");
                    String releaseDate = resultSet.getString("gameReleaseDate");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("price");
                    boolean isAvailable = resultSet.getBoolean("available");
                    String pictureURL = resultSet.getString("pictureURL");
                    String genreName = resultSet.getString("genreName");
    
                    Games game = gamesMap.getOrDefault(gameID,
                            new Games(gameID, title, releaseDate, description, price, new ArrayList<>(), isAvailable, pictureURL));
    
                    // Add the genre if it's not null
                    if (genreName != null) {
                        game.getGenreList().add(genreName);
                    }
    
                    // Put the game object back into the map
                    gamesMap.put(gameID, game);
                }
    
                // Convert the map values to a list of Games
                ArrayList<Games> gamesList = new ArrayList<>(gamesMap.values());
                this.cart = gamesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Games> getCart (User user) {
        loadCart(user);
        return this.cart;
    }

}
