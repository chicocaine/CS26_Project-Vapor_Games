package Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Accounts.User;
import Games.Games;
import Utility.DBConnectionPool;

public class LibraryManager {

    private ArrayList<Games> library_games;

    public void loadGameLibrary(User user) {
        int userID = user.getUserID();
        String query = """
            SELECT 
                g.gameID,
                g.gameTitle,
                g.gameReleaseDate,
                g.description,
                g.price,
                g.available,
                giCard.imageURL AS cardImageURL,
                giShowcase.imageURL AS showcaseImageURL,
                gen.genreName AS genreName
            FROM 
                libraries l
            INNER JOIN 
                games g ON l.gameID = g.gameID
            LEFT JOIN 
                genre_games gg ON g.gameID = gg.gameID
            LEFT JOIN 
                genres gen ON gg.genreID = gen.genreID
            LEFT JOIN 
                game_images giCard ON g.gameID = giCard.gameID AND giCard.imageType = 'CARD'
            LEFT JOIN 
                game_images giShowcase ON g.gameID = giShowcase.gameID AND giShowcase.imageType = 'SHOWCASE'
            WHERE 
                l.userID = ?
        """;

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);

            try (ResultSet resultSet = statement.executeQuery()) {
                HashMap<Integer, Games> gamesMap = new HashMap<>();

                while (resultSet.next()) {
                    int gameID = resultSet.getInt("gameID");
                    String title = resultSet.getString("gameTitle");
                    String releaseDate = resultSet.getString("gameReleaseDate");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("price");
                    boolean isAvailable = resultSet.getBoolean("available");
                    String cardImageURL = resultSet.getString("cardImageURL");
                    String showcaseImageURL = resultSet.getString("showcaseImageURL");
                    String genreName = resultSet.getString("genreName");

                    // Retrieve or create the game object
                    Games game = gamesMap.getOrDefault(gameID, new Games(
                            gameID,
                            title,
                            releaseDate,
                            description,
                            price,
                            new ArrayList<>(),
                            isAvailable,
                            cardImageURL,
                            new ArrayList<>()
                    ));

                    // Add the genre if it's not null
                    if (genreName != null) {
                        game.getGenreList().add(genreName);
                    }

                    // Add the showcase image if it's not null and not already in the list
                    if (showcaseImageURL != null && !game.getShowcaseImagesURL().contains(showcaseImageURL)) {
                        game.getShowcaseImagesURL().add(showcaseImageURL);
                    }

                    // Put the game object back into the map
                    gamesMap.put(gameID, game);
                }

                // Convert the map values to a list of Games
                ArrayList<Games> gamesList = new ArrayList<>(gamesMap.values());
                this.library_games = gamesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Games> getGameLibrary (User user) {
        loadGameLibrary(user);
        return this.library_games;
    }
    

    public void addGameToLibrary(User user, Games game) {
        int userID = user.getUserID();
        int gameID = game.getGameID(); 
        
        String query = "INSERT INTO libraries (userID, gameID) VALUES (?, ?)";
    
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, userID);
            stmt.setInt(2, gameID);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Game successfully added to the user's library.");
            } else {
                System.out.println("Failed to add game to the library.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removeGameFromLibrary(User user, Games game) {
        int userID = user.getUserID();
        int gameID = game.getGameID(); 
        
        String query = "DELETE FROM libraries WHERE userID = ? AND gameID = ?";
    
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, userID);
            stmt.setInt(2, gameID);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Game successfully removed from the user's library.");
            } else {
                System.out.println("Failed to remove game from the library. Game may not exist in user's library.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isGameOwned (User user, Games game) { // what is the point of this one?
        return true;
    } 
    
}
