package User_Interface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import Games.Games;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryPageController {

    // Genre Checkboxes
    @FXML private CheckBox Action_Genre, Adventure_Genre, Anime_Genre, Arcade_Genre, Atmospheric_Genre,
            Casual_Genre, Cute_Genre, Dark_Genre, Exploration_Genre, F2P_Genre, Fantasy_Genre,
            Horror_Genre, Indie_Genre, MMO_Genre, MPlayer_Genre, OpenWorld_Genre, PvE_Genre,
            RPG_Genre, Retro_Genre, SPlayer_Genre, Shooter_Genre, Simulation_Genre, Sports_Genre, Strategy_Genre;

    // Search TextFields
    @FXML
    private TextField LibrarySearchField_TextField, LibraryPriceSearchMinimum_TextField, LibraryPriceSearchMaximum_TextField;

    // Navigation Buttons (ImageViews)
    @FXML
    private ImageView LibrarySearchButton_Image, RefreshLibrary_Image;

    // Layout Elements (HBox, Pane)
    @FXML
    private HBox HBox1, HBox2, HBox3, HBox4, HBox5;
    @FXML
    private Pane LibraryPageNext_Pane, LibraryPagePrevious_Pane, MainPage_Pane;

    private final List<Games> HBox1List = new ArrayList<>();
    private final List<Games> HBox2List = new ArrayList<>();
    private final List<Games> HBox3List = new ArrayList<>();
    private final List<Games> HBox4List = new ArrayList<>();
    private final List<Games> HBox5List = new ArrayList<>();

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

    private void populateGameTiles(HBox hbox, List<Games> gamesList) {
        hbox.getChildren().clear();

        for (int i = 0; i < Math.min(4, gamesList.size()); i++) {
            Games game = gamesList.get(i);
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

    }

    public List<MediumGameTileController> getMediumGameTileControllers() {
        return gameTileControllers;
    }


    @FXML
    void HandlesClickedButton(MouseEvent event) {
        // Handle button clicks
    }

    @FXML
    void HandlesGenreClickedButton(MouseEvent event) {
        // Handle genre-related button clicks
    }
}

