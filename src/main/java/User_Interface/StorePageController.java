package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import Games.Games;
import Games.GamesManager;
import Transaction.CartManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private Games featuredGame;

    @FXML
    private Pane linearGradientPane;
    // Store references to all VGGameTileController instances
    private final List<GameTileController> gameTileControllers = new ArrayList<>();

    public void initialize() {
        initializeGameLists();
        populateGameTiles(DiscoverSomethingNew_HBox, discoverGamesList);
        populateGameTiles(Recommended_HBox, recommendedGamesList);
        populateGameTiles(TopSeller_HBox, topSellerGamesList);

        initializeFeaturedGame();
        setupBuyNowButton();
        applyLinearGradient();
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
        ArrayList<Games> allGames = loader.newGames();
        System.out.println("new games"+allGames);

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

    private void initializeFeaturedGame() {
        GamesManager loader = new GamesManager();
        ArrayList<Games> allGames = loader.getAllGames();

        if (allGames == null || allGames.isEmpty()) {
            System.err.println("[ERROR] GamesManager returned null or no games available.");
            return; // Exit to avoid further issues
        }

        Random random = new Random();
        featuredGame = allGames.get(random.nextInt(allGames.size()));

//        FeaturedGameTitle_Label.setText(featuredGame.getGameTitle());
//        FeaturedGameSubTitle_Label.setText(featuredGame.getGameDescription());
//        HomePageFeatureGamePrice_Label.setText(String.format("AGS %.2f", featuredGame.getGamePrice()));
        HomePageFeaturedGame_Picture.setImage(new Image(featuredGame.getShowcaseImagesURL().getFirst()));
    }

    private void setupBuyNowButton() {
        HomePageFeaturedGameBuyNow_Pane.setOnMouseClicked(event -> buyNow());
    }

    private void buyNow() {
        CartManager cartManager = new CartManager();
        User currentUser = UserSession.getInstance().getCurrentUser();

        if (currentUser == null || featuredGame == null) {
            System.err.println("[ERROR] Current user or featured game is null!");
            return;
        }

        if (cartManager.isGameInLibrary(currentUser, featuredGame)) {
            showGameAlreadyOwnedPopup();
        } else {
            if (!cartManager.isGameInCart(currentUser, featuredGame)) {
                cartManager.addToCart(currentUser, featuredGame);
            }
            loadCheckOutPage();
        }
    }

    private void showGameAlreadyOwnedPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameAlreadyOwnedPopUp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load GameAlreadyOwned popup.");
        }
    }

    private void showGameAlreadyOnCartPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameAlreadyOnCartPopUp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load GameAlreadyOnCart popup.");
        }
    }
    public void loadCheckOutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CheckOutPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) HomePageFeaturedGameBuyNow_Pane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load checkout page.");
        }
    }
    private void applyLinearGradient() {
        LinearGradient linearGradient = new LinearGradient(
                1, 0, 0, 0, // Start and end points (x1, y1, x2, y2)
                true, // Proportional
                CycleMethod.NO_CYCLE, // Cycle method
                new Stop(0, Color.rgb(0, 0, 0, 0.5)), // Dark color on the right with 50% opacity
                new Stop(1, Color.rgb(0, 0, 0, 0.0)) // Transparent on the left
        );

        linearGradientPane.setBackground(new Background(new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY)));
        linearGradientPane.setOpacity(0.5); // Set the overall pane opacity to 50%
    }
}
