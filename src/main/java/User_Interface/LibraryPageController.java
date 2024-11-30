package User_Interface;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LibraryPageController {

    // All Genre
    @FXML private CheckBox Action_Genre, Adventure_Genre, Anime_Genre, Arcade_Genre, Atmospheric_Genre, Casual_Genre,
            Cute_Genre, Dark_Genre, Exploration_Genre, F2P_Genre, Fantasy_Genre, Horror_Genre, Indie_Genre, MMO_Genre,
            MPlayer_Genre, OpenWorld_Genre, PvE_Genre, RPG_Genre, Retro_Genre, SPlayer_Genre, Shooter_Genre,
            Simulation_Genre, Sports_Genre, Strategy_Genre;

    // Game Tiles
    @FXML private Label LibraryGamePrice_T2, LibraryGameTileName_T1, LibraryGameTileName_T2, LibraryGameTileName_T3,
            LibraryGameTileName_T4, LibraryGameTileName_T5, LibraryGameTilePrice_T1, LibraryGameTilePrice_T3,
            LibraryGameTilePrice_T4, LibraryGameTilePrice_T5;

    @FXML private ImageView LibraryGameTileThumbnail_T1, LibraryGameTileThumbnail_T2, LibraryGameTileThumbnail_T3,
            LibraryGameTileThumbnail_T4, LibraryGameTileThumbnail_T5, LibrarySearchButton_Image, RefreshLibrary_Image;

    // Game Tile Panes
    @FXML private Pane LibraryGameTile_T1, LibraryGameTile_T2, LibraryGameTile_T3, LibraryGameTile_T4, LibraryGameTile_T5,
            LibraryPageNext_Pane, LibraryPagePrevious_Pane, MainPage_Pane;

    // Price and Search Fields
    @FXML private TextField LibraryPriceSearchMaximum_TextField, LibraryPriceSearchMinimum_TextField,
            LibrarySearchField_TextField;

    @FXML void HandlesClickedButton(MouseEvent event) {

    }

    @FXML void HandlesGenreClickedButton(MouseEvent event) {

    }

}
