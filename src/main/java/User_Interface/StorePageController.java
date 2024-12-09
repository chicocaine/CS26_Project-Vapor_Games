package User_Interface;

import Games.Games;
import Games.GamesManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorePageController {

    @FXML
    private HBox DiscoverSomethingNew_HBox;

    @FXML
    private Label FeaturedGameDescription_Label;

    @FXML
    private Label FeaturedGameSubTitle_Label;

    @FXML
    private Label FeaturedGameTitle_Label;

    @FXML
    private Label HomePageFeatureGamePrice_Label;

    @FXML
    private Pane HomePageFeaturedGameBuyNow_Pane;

    @FXML
    private Pane HomePageFeaturedGame_Pane;

    @FXML
    private ImageView HomePageFeaturedGame_Picture;

    @FXML
    private Pane MainPage_Pane;

    @FXML
    private HBox Recommended_HBox;

    @FXML
    private HBox TopSeller_HBox;

    private final List<Games> discoverGamesList = new ArrayList<>();
    private final List<Games> recommendedGamesList = new ArrayList<>();
    private final List<Games> topSellerGamesList = new ArrayList<>();

    // Store references to all VGGameTileController instances
    private final List<GameTileController> gameTileControllers = new ArrayList<>();

    public void initialize() {
        initializeGameLists();
        populateGameTiles(DiscoverSomethingNew_HBox, discoverGamesList);
        populateGameTiles(Recommended_HBox, recommendedGamesList);
        populateGameTiles(TopSeller_HBox, topSellerGamesList);
    }

    private void populateGameTiles(HBox hbox, List<Games> gamesList) {
        hbox.getChildren().clear();

        for (int i = 0; i < Math.min(6, gamesList.size()); i++) {
            Games game = gamesList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameTile.fxml"));
                Pane gameTilePane = loader.load();
                GameTileController tileController = loader.getController();

                // Store the tile controller for future reference
                gameTileControllers.add(tileController);

                tileController.setGameDetails(game);
                hbox.getChildren().add(gameTilePane);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load game tile.");
            }
        }
    }

    private void initializeGameLists() {
        GamesManager loader = new GamesManager();
        ArrayList<Games> allGames = loader.getAllGames();

        if (allGames == null || allGames.isEmpty()) {
            System.err.println("[ERROR] GamesManager returned null or no games available.");
            return; // Exit to avoid further issues
        }

        for (int i = 0; i < allGames.size(); i++) {
            Games game = allGames.get(i);

            // Categorize games based on indices or attributes
            //change pa later wtf para dynamic
            if (i % 3 == 0) {
                discoverGamesList.add(convertToGame(game)); // Convert to your Game model
            } else if (i % 3 == 1) {
                recommendedGamesList.add(convertToGame(game));
            } else {
                topSellerGamesList.add(convertToGame(game));
            }
        }
    }


    private Games convertToGame(Games games) {
        return new Games(
                games.getGameID(),
                games.getGameTitle(),
                games.getGameReleaseDate(),
                games.getGameDescription(),
                games.getGamePrice(),
                games.getGenreList(),
                games.isAvailable(),
                games.getCardImageURL(),
                games.getShowcaseImagesURL()
        );
    }

    public List<GameTileController> getGameTileControllers() {
        return gameTileControllers;
    }
}
