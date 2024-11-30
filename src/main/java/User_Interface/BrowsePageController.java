package User_Interface;

import Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrowsePageController {

    // Genre CheckBoxes
    @FXML private CheckBox Action_Genre, Adventure_Genre, Anime_Genre, Arcade_Genre, Atmospheric_Genre, Casual_Genre,
            Cute_Genre, Dark_Genre, Exploration_Genre, F2P_Genre, Fantasy_Genre, Horror_Genre,
            Indie_Genre, MMO_Genre, MPlayer_Genre, OpenWorld_Genre, PvE_Genre, RPG_Genre, Retro_Genre,
            SPlayer_Genre, Shooter_Genre, Simulation_Genre, Sports_Genre, Strategy_Genre;

    // Layouts
    @FXML private HBox HBox1, HBox2, HBox3, HBox4, HBox5;
    @FXML private Pane LibraryPageNext_Pane, LibraryPagePrevious_Pane, MainPage_Pane;

    // Search Fields and Buttons
    @FXML private TextField LibrarySearchField_TextField, LibraryPriceSearchMaximum_TextField, LibraryPriceSearchMinimum_TextField;
    @FXML private ImageView LibrarySearchButton_Image;

    private final List<Game> HBox1List = new ArrayList<>();
    private final List<Game> HBox2List = new ArrayList<>();
    private final List<Game> HBox3List = new ArrayList<>();
    private final List<Game> HBox4List = new ArrayList<>();
    private final List<Game> HBox5List = new ArrayList<>();

    // Store references to all VGGameTileController instances
    private final List<MediumGameTileController> gameTileControllers = new ArrayList<>();

    public void initialize() {
        initializeGameLists();
        populateGameTiles(HBox1, HBox1List);
        populateGameTiles(HBox2, HBox2List);
        populateGameTiles(HBox3, HBox3List);
        populateGameTiles(HBox4, HBox4List);
        populateGameTiles(HBox5, HBox5List);
    }

    private void populateGameTiles(HBox hbox, List<Game> gamesList) {
        hbox.getChildren().clear();

        for (int i = 0; i < Math.min(4, gamesList.size()); i++) {
            Game game = gamesList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MediumGameTile.fxml"));
                Pane gameTilePane = loader.load();
                MediumGameTileController tileController = loader.getController();

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
        HBox1List.add(new Game("Game 1", "10.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox1List.add(new Game("Game 2", "20.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox1List.add(new Game("Game 3", "15.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox1List.add(new Game("Game 4", "25.99", "/Image/SmallTileTestPicture.png", "Blank"));

        HBox2List.add(new Game("Recommended Game 1", "12.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox2List.add(new Game("Recommended Game 2", "18.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox2List.add(new Game("Recommended Game 3", "22.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox2List.add(new Game("Recommended Game 4", "35.99", "/Image/SmallTileTestPicture.png", "Blank"));

        HBox3List.add(new Game("Top Seller Game 1", "14.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox3List.add(new Game("Top Seller Game 2", "28.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox3List.add(new Game("Top Seller Game 3", "45.99", "/Image/SmallTileTestPicture.png", "Blank"));

        HBox4List.add(new Game("Recommended Game 1", "12.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox4List.add(new Game("Recommended Game 2", "18.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox4List.add(new Game("Recommended Game 3", "22.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox4List.add(new Game("Recommended Game 4", "35.99", "/Image/SmallTileTestPicture.png", "Blank"));

        HBox5List.add(new Game("Top Seller Game 1", "14.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox5List.add(new Game("Top Seller Game 2", "28.99", "/Image/SmallTileTestPicture.png", "Blank"));
        HBox5List.add(new Game("Top Seller Game 3", "45.99", "/Image/SmallTileTestPicture.png", "Blank"));
    }

    public List<MediumGameTileController> getMediumGameTileControllers() {
        return gameTileControllers;
    }

    // Event Handlers
    @FXML
    void HandlesClickedButton(MouseEvent event) {
        // Handle button click logic here
    }

    @FXML
    void HandlesGenreClickedButton(MouseEvent event) {
        // Handle genre button click logic here
    }

    public void setSearchQuery(String query) {
        if (LibrarySearchField_TextField != null) {
            LibrarySearchField_TextField.setText(query);
        }
    }
}
