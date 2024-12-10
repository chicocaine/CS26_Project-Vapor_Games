package Games;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Utility.DBConnectionPool;

public class GamesManager {

    public GamesManager () {}

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
                img.imageURL AS pictureURL,
                img.imageType AS imageType,
                gen.genreName AS genreName
            FROM 
                games g
            LEFT JOIN 
                genre_games gg ON g.gameID = gg.gameID
            LEFT JOIN 
                genres gen ON gg.genreID = gen.genreID
            LEFT JOIN 
                game_images img ON g.gameID = img.gameID
            ORDER BY 
                g.gameID, img.imageType;
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
                String imageType = resultSet.getString("imageType");
                String imageURL = resultSet.getString("pictureURL");
                String genreName = resultSet.getString("genreName");

                // Retrieve or initialize the Games object
                Games game = gamesMap.getOrDefault(gameID,
                        new Games(gameID, title, releaseDate, description, price, new ArrayList<>(), isAvailable, null, new ArrayList<>()));

                // Set the card image URL if it's a CARD image
                if ("CARD".equalsIgnoreCase(imageType) && imageURL != null) {
                    game.setCardImageURL(imageURL);
                }

                // Add to showcase images if it's a SHOWCASE image
                if ("SHOWCASE".equalsIgnoreCase(imageType) && imageURL != null) {
                    game.getShowcaseImagesURL().add(imageURL);
                }

                // Add the genre if it's not null
                if (genreName != null) {
                    game.getGenreList().add(genreName);
                }

                // Put the game object back into the map
                gamesMap.put(gameID, game);
            }

            // Convert the map values to a list of Games
            ArrayList<Games> gamesList;
            gamesList = new ArrayList<>(gamesMap.values());
            this.game_list = gamesList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Games> getAllGames () {
        GamesLoader();
        return this.game_list;
    }

    public ArrayList<Games> searchGames(String gameTitle) {
        ArrayList<Games> gameList = new ArrayList<>();
        String query = """
        SELECT 
            g.gameID, 
            g.gameTitle, 
            g.gameReleaseDate, 
            g.description, 
            g.price, 
            g.available, 
            img.imageURL, 
            img.imageType, 
            GROUP_CONCAT(DISTINCT ge.genreName) AS genres
        FROM 
            games g
        LEFT JOIN 
            genre_games gg ON g.gameID = gg.gameID
        LEFT JOIN 
            genres ge ON gg.genreID = ge.genreID
        LEFT JOIN 
            game_images img ON g.gameID = img.gameID
        WHERE 
            g.gameTitle LIKE ?
        GROUP BY 
            g.gameID, img.imageURL, img.imageType
    """;

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + gameTitle + "%");

            HashMap<Integer, Games> gamesMap = new HashMap<>();
            HashSet<String> showcaseImages = new HashSet<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int gameID = resultSet.getInt("gameID");

                    Games game = gamesMap.getOrDefault(gameID, new Games(
                            gameID,
                            resultSet.getString("gameTitle"),
                            resultSet.getString("gameReleaseDate"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            new ArrayList<>(), // Genres to be populated
                            resultSet.getBoolean("available"),
                            null, // Card image to be set
                            new ArrayList<>() // Showcase images to be added
                    ));

                    // Populate genres
                    String genreString = resultSet.getString("genres");
                    if (genreString != null) {
                        game.setGameGenres(new ArrayList<>(List.of(genreString.split(","))));
                    }

                    // Populate images
                    String imageType = resultSet.getString("imageType");
                    String imageURL = resultSet.getString("imageURL");
                    if (imageType != null && imageURL != null) {
                        if ("CARD".equalsIgnoreCase(imageType)) {
                            game.setCardImageURL(imageURL);
                        } else if ("SHOWCASE".equalsIgnoreCase(imageType)) {
                            showcaseImages.add(imageURL);
                        }
                    }

                    game.setShowcaseImagesURL(new ArrayList<>(showcaseImages));
                    gamesMap.put(gameID, game);
                }
            }

            gameList = new ArrayList<>(gamesMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    public ArrayList<Games> filterByGenre(ArrayList<String> genres) {
        ArrayList<Games> gameList = new ArrayList<>();
        String query = """
            SELECT 
                g.gameID, 
                g.gameTitle, 
                g.gameReleaseDate, 
                g.description, 
                g.price, 
                g.available, 
                img.imageURL, 
                img.imageType,
                GROUP_CONCAT(DISTINCT ge.genreName) AS genres
            FROM 
                games g
            LEFT JOIN 
                genre_games gg ON g.gameID = gg.gameID
            LEFT JOIN 
                genres ge ON gg.genreID = ge.genreID
            LEFT JOIN 
                game_images img ON g.gameID = img.gameID
            WHERE 
                ge.genreName IN (%s)
            GROUP BY 
                g.gameID, img.imageURL, img.imageType
        """;

        // Generate placeholders for the query (e.g., ?, ?, ?)
        String placeholders = String.join(", ", genres.stream().map(g -> "?").toList());
        query = String.format(query, placeholders);

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the genre parameters in the query
            for (int i = 0; i < genres.size(); i++) {
                statement.setString(i + 1, genres.get(i));
            }

            HashMap<Integer, Games> gamesMap = new HashMap<>();
            HashSet<String> showcaseImages = new HashSet<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int gameID = resultSet.getInt("gameID");

                    Games game = gamesMap.getOrDefault(gameID, new Games(
                            gameID,
                            resultSet.getString("gameTitle"),
                            resultSet.getString("gameReleaseDate"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            new ArrayList<>(), // Genres to be populated
                            resultSet.getBoolean("available"),
                            null, // Card image to be set
                            new ArrayList<>() // Showcase images to be added
                    ));

                    // Populate genres
                    String genreString = resultSet.getString("genres");
                    if (genreString != null) {
                        game.setGameGenres(new ArrayList<>(List.of(genreString.split(","))));
                    }

                    // Populate images
                    String imageType = resultSet.getString("imageType");
                    String imageURL = resultSet.getString("imageURL");
                    if (imageType != null && imageURL != null) {
                        if ("CARD".equalsIgnoreCase(imageType)) {
                            game.setCardImageURL(imageURL);
                        } else if ("SHOWCASE".equalsIgnoreCase(imageType)) {
                            showcaseImages.add(imageURL);
                        }
                    }

                    game.setShowcaseImagesURL(new ArrayList<>(showcaseImages));
                    gamesMap.put(gameID, game);
                }
            }

            gameList = new ArrayList<>(gamesMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    public ArrayList<Games> searchGamesByTitleAndPriceRange(String title, double minPrice, double maxPrice) {
        ArrayList<Games> gameList = new ArrayList<>();
        String query = """
        SELECT
            g.gameID,
            g.gameTitle,
            g.gameReleaseDate,
            g.description,
            g.price,
            g.available,
            img.imageURL,
            img.imageType,
            GROUP_CONCAT(DISTINCT ge.genreName) AS genres
        FROM
            games g
        LEFT JOIN
            genre_games gg ON g.gameID = gg.gameID
        LEFT JOIN
            genres ge ON gg.genreID = ge.genreID
        LEFT JOIN
            game_images img ON g.gameID = img.gameID
        WHERE
            g.gameTitle LIKE ? AND g.price BETWEEN ? AND ?
        GROUP BY
            g.gameID, img.imageURL, img.imageType
    """;

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + title + "%");
            statement.setDouble(2, minPrice);
            statement.setDouble(3, maxPrice);

            HashMap<Integer, Games> gamesMap = new HashMap<>();
            HashSet<String> showcaseImages = new HashSet<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int gameID = resultSet.getInt("gameID");

                    Games game = gamesMap.getOrDefault(gameID, new Games(
                            gameID,
                            resultSet.getString("gameTitle"),
                            resultSet.getString("gameReleaseDate"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            new ArrayList<>(), // Genres to be populated
                            resultSet.getBoolean("available"),
                            null, // Card image to be set
                            new ArrayList<>() // Showcase images to be added
                    ));

                    // Populate genres
                    String genreString = resultSet.getString("genres");
                    if (genreString != null) {
                        game.setGameGenres(new ArrayList<>(List.of(genreString.split(","))));
                    }

                    // Populate images
                    String imageType = resultSet.getString("imageType");
                    String imageURL = resultSet.getString("imageURL");
                    if (imageType != null && imageURL != null) {
                        if ("CARD".equalsIgnoreCase(imageType)) {
                            game.setCardImageURL(imageURL);
                        } else if ("SHOWCASE".equalsIgnoreCase(imageType)) {
                            showcaseImages.add(imageURL);
                        }
                    }

                    game.setShowcaseImagesURL(new ArrayList<>(showcaseImages));
                    gamesMap.put(gameID, game);
                }
            }

            gameList = new ArrayList<>(gamesMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    public ArrayList<Games> filterByPriceRange(double minPrice, double maxPrice) {
        ArrayList<Games> gameList = new ArrayList<>();
        String query = """
        SELECT
            g.gameID,
            g.gameTitle,
            g.gameReleaseDate,
            g.description,
            g.price,
            g.available,
            img.imageURL,
            img.imageType,
            GROUP_CONCAT(DISTINCT ge.genreName) AS genres
        FROM
            games g
        LEFT JOIN
            genre_games gg ON g.gameID = gg.gameID
        LEFT JOIN
            genres ge ON gg.genreID = ge.genreID
        LEFT JOIN
            game_images img ON g.gameID = img.gameID
        WHERE
            g.price BETWEEN ? AND ?
        GROUP BY
            g.gameID, img.imageURL, img.imageType
    """;

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);

            HashMap<Integer, Games> gamesMap = new HashMap<>();
            HashSet<String> showcaseImages = new HashSet<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int gameID = resultSet.getInt("gameID");

                    Games game = gamesMap.getOrDefault(gameID, new Games(
                            gameID,
                            resultSet.getString("gameTitle"),
                            resultSet.getString("gameReleaseDate"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            new ArrayList<>(), // Genres to be populated
                            resultSet.getBoolean("available"),
                            null, // Card image to be set
                            new ArrayList<>() // Showcase images to be added
                    ));

                    // Populate genres
                    String genreString = resultSet.getString("genres");
                    if (genreString != null) {
                        game.setGameGenres(new ArrayList<>(List.of(genreString.split(","))));
                    }

                    // Populate images
                    String imageType = resultSet.getString("imageType");
                    String imageURL = resultSet.getString("imageURL");
                    if (imageType != null && imageURL != null) {
                        if ("CARD".equalsIgnoreCase(imageType)) {
                            game.setCardImageURL(imageURL);
                        } else if ("SHOWCASE".equalsIgnoreCase(imageType)) {
                            showcaseImages.add(imageURL);
                        }
                    }

                    game.setShowcaseImagesURL(new ArrayList<>(showcaseImages));
                    gamesMap.put(gameID, game);
                }
            }

            gameList = new ArrayList<>(gamesMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    public void addGame(Games game) { // Add game to the database
        String insertGameQuery = """
        INSERT INTO games (gameTitle, gameReleaseDate, description, price, available)
        VALUES (?, ?, ?, ?, ?)
    """;

        String insertGenreQuery = """
        INSERT INTO genre_games (gameID, genreID)
        VALUES (?, (SELECT genreID FROM genres WHERE genreName = ?))
    """;

        String insertImageQuery = """
        INSERT INTO game_images (gameID, imageURL, imageType)
        VALUES (?, ?, ?)
    """;

        String getGameIDQuery = """
        SELECT gameID FROM games WHERE gameTitle = ?
    """;

        int gameID = -1;

        try (Connection connection = DBConnectionPool.getConnection()) {
            // Enable transactions to ensure atomicity
            connection.setAutoCommit(false);

            try {
                // Insert game details
                try (PreparedStatement gameStatement = connection.prepareStatement(insertGameQuery)) {
                    gameStatement.setString(1, game.getGameTitle());
                    gameStatement.setString(2, game.getGameReleaseDate());
                    gameStatement.setString(3, game.getGameDescription());
                    gameStatement.setDouble(4, game.getConvertedGamePrice());
                    gameStatement.setBoolean(5, game.isAvailable());

                    int rowsAffected = gameStatement.executeUpdate();
                    System.out.printf("Inserted %d row(s) into Games table.%n", rowsAffected);
                }

                // Get gameID of recently added game
                try (PreparedStatement gameIDStatement = connection.prepareStatement(getGameIDQuery)) {
                    gameIDStatement.setString(1, game.getGameTitle());
                    try (ResultSet resultSet = gameIDStatement.executeQuery()) {
                        if (resultSet.next()) {
                            gameID = resultSet.getInt("gameID");
                        } else {
                            throw new SQLException("No gameID found for the gameTitle: " + game.getGameTitle());
                        }
                    }
                }

                // Insert genres for the game
                try (PreparedStatement genreStatement = connection.prepareStatement(insertGenreQuery)) {
                    for (String genre : game.getGenreList()) {
                        genreStatement.setInt(1, gameID);
                        genreStatement.setString(2, genre);
                        genreStatement.addBatch(); // Add to batch for optimization
                    }
                    int[] rowsAffected = genreStatement.executeBatch();
                    System.out.printf("Inserted %d row(s) into Genre_Games table.%n", rowsAffected.length);
                }

                // Insert images for the game
                try (PreparedStatement imageStatement = connection.prepareStatement(insertImageQuery)) {
                    // Insert card image
                    imageStatement.setInt(1, gameID);
                    imageStatement.setString(2, game.getCardImageURL());
                    imageStatement.setString(3, "CARD");
                    imageStatement.addBatch();

                    // Insert showcase images
                    for (String imageURL : game.getShowcaseImagesURL()) {
                        imageStatement.setInt(1, gameID);
                        imageStatement.setString(2, imageURL);
                        imageStatement.setString(3, "SHOWCASE");
                        imageStatement.addBatch();
                    }
                    int[] rowsAffected = imageStatement.executeBatch();
                    System.out.printf("Inserted %d row(s) into Game_Images table.%n", rowsAffected.length);
                }

                // Commit transaction
                connection.commit();
            } catch (SQLException e) {
                // Roll back transaction if any error occurs
                connection.rollback();
                e.printStackTrace();
                System.err.println("Error adding the game to the database. Changes rolled back.");
            } finally {
                connection.setAutoCommit(true); // Reset auto-commit mode
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error.");
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

//    public static void main(String[] args) {
//        GamesManager manager = new GamesManager();
//        manager.GamesLoader();
//        Games uploadgame = new Games();
//        manager.addGame(uploadgame.initBloonsTD6());
//        manager.addGame(uploadgame.initDontStarveTogether());
//        manager.addGame(uploadgame.initPlantsVsZombies());
//
//        // Example usage
//        ArrayList<Games> allGames = manager.getAllGames();
//        for (Games game : allGames) {
//            System.out.println(game);
//        }
//    }
}