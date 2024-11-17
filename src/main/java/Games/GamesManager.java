package Games;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utility.DBConnectionPool;

public class GamesManager {

    private ArrayList<Games> game_list;

    public void GamesLoader () {
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
                games g
            LEFT JOIN 
                genre_games gg ON g.gameID = gg.gameID
            LEFT JOIN 
                genres gen ON gg.genreID = gen.genreID;
        """;

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

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
            this.game_list = gamesList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Games> getAllGames () { 
        return this.game_list;
    }

    public Games searchGames(String gameTitle) {
        String query = """
            SELECT g.gameID, g.gameTitle, g.gameReleaseDate, g.description, g.price, g.available, g.pictureURL, 
                GROUP_CONCAT(ge.genreName) AS genres
            FROM games g
            JOIN genre_games gg ON g.gameID = gg.gameID
            JOIN genres ge ON gg.genreID = ge.genreID
            WHERE g.gameTitle = ?
            GROUP BY g.gameID
        """;

        Games queriedGame = null;

        try (Connection connection = DBConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, gameTitle);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    queriedGame = new Games(
                        resultSet.getInt("gameID"),
                        resultSet.getString("gameTitle"),
                        resultSet.getString("gameReleaseDate"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        new ArrayList<>(List.of(resultSet.getString("genres").split(","))),
                        resultSet.getBoolean("available"),
                        resultSet.getString("pictureURL")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queriedGame;
    }

    public ArrayList<Games> filterByGenre(ArrayList<String> genres) {
        ArrayList<Games> gameList = new ArrayList<>();
        String query = """
            SELECT g.gameID, g.gameTitle, g.gameReleaseDate, g.description, g.price, g.available, g.pictureURL, 
                   GROUP_CONCAT(DISTINCT ge.genreName) AS genres
            FROM games g
            JOIN genre_games gg ON g.gameID = gg.gameID
            JOIN genres ge ON gg.genreID = ge.genreID
            WHERE ge.genreName IN (%s)
            GROUP BY g.gameID
        """;
    
        // Generate placeholders for genres (e.g., ?, ?, ?)
        String placeholders = String.join(", ", genres.stream().map(g -> "?").toList());
        query = String.format(query, placeholders);
    
        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            for (int i = 0; i < genres.size(); i++) {
                statement.setString(i + 1, genres.get(i));
            }
    
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Games game = new Games(
                        resultSet.getInt("gameID"),
                        resultSet.getString("gameTitle"),
                        resultSet.getString("gameReleaseDate"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        new ArrayList<>(List.of(resultSet.getString("genres").split(","))),
                        resultSet.getBoolean("available"),
                        resultSet.getString("pictureURL")
                    );
                    gameList.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return gameList;
    }    

    public ArrayList<Games> filterByPriceRange(double minPrice, double maxPrice) {
        ArrayList<Games> gameList = new ArrayList<>();
        String query = """
            SELECT g.gameID, g.gameTitle, g.gameReleaseDate, g.description, g.price, g.available, g.pictureURL, 
                   GROUP_CONCAT(DISTINCT ge.genreName) AS genres
            FROM games g
            JOIN genre_games gg ON g.gameID = gg.gameID
            JOIN genres ge ON gg.genreID = ge.genreID
            WHERE g.price BETWEEN ? AND ?
            GROUP BY g.gameID
        """;
    
        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Games game = new Games(
                        resultSet.getInt("gameID"),
                        resultSet.getString("gameTitle"),
                        resultSet.getString("gameReleaseDate"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        new ArrayList<>(List.of(resultSet.getString("genres").split(","))),
                        resultSet.getBoolean("available"),
                        resultSet.getString("pictureURL")
                    );
                    gameList.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return gameList;
    }    

    public void addGame(Games game) { // add game to database
        String insertGameQuery = """
            INSERT INTO games (gameTitle, gameReleaseDate, description, price, available, pictureURL)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        String insertGenreQuery = """
            INSERT INTO genre_games (gameID, genreID)
            VALUES (?, (SELECT genreID FROM genres WHERE genreName = ?))
        """;

        String getGameID = """
            SELECT gameID FROM games WHERE gameTitle = ?;    
        """;

        int gameID_add = -1;

        try (Connection connection = DBConnectionPool.getConnection()) {
            // Insert game details
            try (PreparedStatement gameStatement = connection.prepareStatement(insertGameQuery)) {
                gameStatement.setString(1, game.getGameTitle());
                gameStatement.setString(2, game.getGameReleaseDate());
                gameStatement.setString(3, game.getGameDescription());
                gameStatement.setDouble(4, game.getGamePrice());
                gameStatement.setBoolean(5, game.isAvailable());
                gameStatement.setString(6, game.getGamePictureURL());

                int rowsAffected = gameStatement.executeUpdate();
                System.out.printf("Inserted %d row(s) into Games table.%n", rowsAffected);
            }

            // Get gameID of recently added game
            try (PreparedStatement gameIDStatement = connection.prepareStatement(getGameID)) {
                gameIDStatement.setString(1, game.getGameTitle());
                ResultSet resultSet = gameIDStatement.executeQuery();
                if (resultSet.next()) { // Move cursor to the first row
                    gameID_add = resultSet.getInt("gameID");
                } else {
                    throw new SQLException("No gameID found for the gameTitle: " + game.getGameTitle());
                }
            }

            // Insert genres for the game
            try (PreparedStatement genreStatement = connection.prepareStatement(insertGenreQuery)) {
                for (String genre : game.getGenreList()) {
                    genreStatement.setInt(1, gameID_add);
                    genreStatement.setString(2, genre);
                    genreStatement.addBatch(); // Add to batch for optimization
                }
                int[] rowsAffected = genreStatement.executeBatch();
                System.out.printf("Inserted %d row(s) into Genre_Games table.%n", rowsAffected.length);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding the game to the database.");
        }
    }
    
    public void removeGame(Games game) {
        String deleteGameQuery = "DELETE FROM Games WHERE gameID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteGameQuery)) {

            // Set the gameID parameter
            deleteStatement.setInt(1, game.getGameID());

            // Execute the delete operation
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Successfully deleted game with ID %d.%n", game.getGameID());
            } else {
                System.out.printf("No game found with ID %d.%n", game.getGameID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error removing the game from the database.");
        }
    }
}