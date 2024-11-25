package User_Interface;

import Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class VGStorePageController {

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
    private ImageView HomePageRecommendedGamesThumbnail_Image;

    @FXML
    private Pane MainPage_Pane;

    @FXML
    private HBox Recommended_HBox;

    @FXML
    private HBox TopSeller_HBox;

    private VGMainScreenController mainScreenController;

    public void setMainScreenController(VGMainScreenController controller) {
        this.mainScreenController = controller;
    }

    private List<Game> discoverGamesList = new ArrayList<>();
    private List<Game> recommendedGamesList = new ArrayList<>();
    private List<Game> topSellerGamesList = new ArrayList<>();

    public void initialize() {
        loadGamePage();
        initializeGameLists();
        populateGameTiles(DiscoverSomethingNew_HBox, discoverGamesList);
        populateGameTiles(Recommended_HBox, recommendedGamesList);
        populateGameTiles(TopSeller_HBox, topSellerGamesList);
    }

    private void populateGameTiles(HBox hbox, List<Game> gamesList) {
        hbox.getChildren().clear();

        for (int i = 0; i < Math.min(5, gamesList.size()); i++) {
            Game game = gamesList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/VGGameTile.fxml"));
                Pane gameTilePane = loader.load();
                VGGameTileController tileController = loader.getController();
                tileController.setMainScreenController(mainScreenController); // Set the controller reference
                tileController.setGameDetails(game);
                hbox.getChildren().add(gameTilePane);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load game tile.");
            }
        }
    }

    private void loadGamePage() {
        if (mainScreenController != null) {
            System.out.println("[DEBUG] Loading VGGamePage from VGStorePage");
            mainScreenController.loadPane("/VGGamePage.fxml");
        } else {
            System.out.println("[ERROR] MainScreenController not set!");
        }
    }

    private void initializeGameLists() {
        discoverGamesList.add(new Game("Game 1", "10.99", "/Image/SmallTileTestPicture.png", "Blank"));
        discoverGamesList.add(new Game("Game 2", "20.99", "/Image/SmallTileTestPicture.png", "Blank"));
        discoverGamesList.add(new Game("Game 3", "15.99", "/Image/SmallTileTestPicture.png", "Blank"));
        discoverGamesList.add(new Game("Game 4", "25.99", "/Image/SmallTileTestPicture.png", "Blank"));
        discoverGamesList.add(new Game("Game 5", "30.99", "/Image/SmallTileTestPicture.png", "Blank"));
        discoverGamesList.add(new Game("Game 6", "40.99", "/Image/SmallTileTestPicture.png", "Blank"));

        recommendedGamesList.add(new Game("Recommended Game 1", "12.99", "/Image/SmallTileTestPicture.png", "Blank"));
        recommendedGamesList.add(new Game("Recommended Game 2", "18.99", "/Image/SmallTileTestPicture.png", "Blank"));
        recommendedGamesList.add(new Game("Recommended Game 3", "22.99", "/Image/SmallTileTestPicture.png", "Blank"));
        recommendedGamesList.add(new Game("Recommended Game 4", "35.99", "/Image/SmallTileTestPicture.png", "Blank"));

        topSellerGamesList.add(new Game("Top Seller Game 1", "14.99", "/Image/SmallTileTestPicture.png", "Blank"));
        topSellerGamesList.add(new Game("Top Seller Game 2", "28.99", "/Image/SmallTileTestPicture.png", "Blank"));
        topSellerGamesList.add(new Game("Top Seller Game 3", "45.99", "/Image/SmallTileTestPicture.png", "Blank"));
    }
}
