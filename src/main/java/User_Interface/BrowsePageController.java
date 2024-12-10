package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import Games.Games;
import Games.GamesManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowsePageController {

    private User CurrentUser = UserSession.getInstance().getCurrentUser();

    private List<String> selectedGenres = new ArrayList<>();
    private Map<CheckBox, String> genreMap = new HashMap<>();

    @FXML
    private CheckBox Action_Genre, Adventure_Genre, ArtGame_Genre, BattleRoyale_Genre, BoardGame_Genre,
            BulletHell_Genre, BusinessSimulator_Genre, CardGame_Genre, Casual_Genre, CityBuilder_Genre,
            CoOp_Genre, Comedy_Genre, DatingSimulator_Genre, Educational_Genre, FPS_Genre, Fighting_Genre,
            HackAndSlash_Genre, Horror_Genre, IndieGame_Genre, InteractiveStory_Genre, LifeSimulator_Genre,
            MMO_Genre, MOBA_Genre, Metroidvania_Genre, OpenWorld_Genre, Party_Genre, Platformer_Genre,
            Puzzle_Genre, Racing_Genre, RealTimeStrategy_Genre;

    @FXML
    private HBox HBox1, HBox2, HBox3, HBox4, HBox5;

    @FXML
    private TextField LibraryPriceSearchMaximum_TextField, LibraryPriceSearchMinimum_TextField, LibrarySearchField_TextField;

    @FXML
    private ImageView LibrarySearchButton_Image;

    @FXML
    private Pane MainPage_Pane;

    private final List<Games> HBox1List = new ArrayList<>();
    private final List<Games> HBox2List = new ArrayList<>();
    private final List<Games> HBox3List = new ArrayList<>();
    private final List<Games> HBox4List = new ArrayList<>();
    private final List<Games> HBox5List = new ArrayList<>();

    // Store references to all MediumGameTileController instances
    private final List<MediumGameTileController> gameTileControllers = new ArrayList<>();

    public void initialize() {
        initializeGameLists();
        populateGameTiles(HBox1, HBox1List);
        populateGameTiles(HBox2, HBox2List);
        populateGameTiles(HBox3, HBox3List);
        populateGameTiles(HBox4, HBox4List);
        populateGameTiles(HBox5, HBox5List);

        genreMap.put(Action_Genre, "Action");
        genreMap.put(Adventure_Genre, "Adventure");
        genreMap.put(ArtGame_Genre, "Art Game");
        genreMap.put(BattleRoyale_Genre, "Battle Royale");
        genreMap.put(BoardGame_Genre, "Board Game");
        genreMap.put(BulletHell_Genre, "Bullet Hell");
        genreMap.put(BusinessSimulator_Genre, "Business Simulation");
        genreMap.put(CardGame_Genre, "Card Game");
        genreMap.put(Casual_Genre, "Casual");
        genreMap.put(CityBuilder_Genre, "City Builder");
        genreMap.put(CoOp_Genre, "Co-op");
        genreMap.put(Comedy_Genre, "Comedy");
        genreMap.put(DatingSimulator_Genre, "Dating Simulator");
        genreMap.put(Educational_Genre, "Educational");
        genreMap.put(FPS_Genre, "First-Person Shooter (FPS)");
        genreMap.put(Fighting_Genre, "Fighting");
        genreMap.put(HackAndSlash_Genre, "Hack and Slash");
        genreMap.put(Horror_Genre, "Horror");
        genreMap.put(IndieGame_Genre, "Idle Game");
        genreMap.put(InteractiveStory_Genre, "Interactive Story");
        genreMap.put(LifeSimulator_Genre, "Life Simulation");
        genreMap.put(MMO_Genre, "Massively Multiplayer Online (MMO)");
        genreMap.put(MOBA_Genre, "Multiplayer Online Battle Arena (MOBA)");
        genreMap.put(Metroidvania_Genre, "Metroidvania");
        genreMap.put(OpenWorld_Genre, "Open World");
        genreMap.put(Party_Genre, "Party");
        genreMap.put(Platformer_Genre, "Platformer");
        genreMap.put(Puzzle_Genre, "Puzzle");
        genreMap.put(Racing_Genre, "Racing");
        genreMap.put(RealTimeStrategy_Genre, "Real-Time Strategy (RTS)");

        for (CheckBox checkBox : genreMap.keySet()) {
            checkBox.setOnAction(event -> updateSelectedGenres());
        }
    }

    public void updateSelectedGenres() {
        selectedGenres.clear();

        for (Map.Entry<CheckBox, String> entry : genreMap.entrySet()) {
            if (entry.getKey().isSelected()) {
                selectedGenres.add(entry.getValue());
            }
        }

        filterAndDisplayGamesByGenres();
    }

    private void filterAndDisplayGamesByGenres() {
        GamesManager gamesManager = new GamesManager();
        ArrayList<Games> filteredGames;

        if (selectedGenres.isEmpty()) {
            filteredGames = gamesManager.getAllGames();
        } else {
            filteredGames = gamesManager.filterByGenre(new ArrayList<>(selectedGenres));
        }

        // Clear existing lists
        HBox1List.clear();
        HBox2List.clear();
        HBox3List.clear();
        HBox4List.clear();
        HBox5List.clear();

        // Distribute filtered games into HBox lists
        for (int i = 0; i < filteredGames.size(); i++) {
            Games game = filteredGames.get(i);
            if (i % 5 == 0) {
                HBox1List.add(game);
            } else if (i % 5 == 1) {
                HBox2List.add(game);
            } else if (i % 5 == 2) {
                HBox3List.add(game);
            } else if (i % 5 == 3) {
                HBox4List.add(game);
            } else {
                HBox5List.add(game);
            }
        }

        // Repopulate HBoxes with filtered games
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
        GamesManager loader = new GamesManager();
        ArrayList<Games> allGames = loader.getAllGames();

        if (allGames == null || allGames.isEmpty()) {
            System.err.println("[ERROR] GamesManager returned null or no games available.");
            return; // Exit to avoid further issues
        }

        for (int i = 0; i < allGames.size(); i++) {
            Games game = allGames.get(i);

            // Categorize games based on indices or attributes
            if (i % 5 == 0) {
                HBox1List.add(convertToGame(game)); // Convert to your Game model
            } else if (i % 5 == 1) {
                HBox2List.add(convertToGame(game));
            } else if (i % 5 == 2) {
                HBox3List.add(convertToGame(game));
            } else if (i % 5 == 3) {
                HBox4List.add(convertToGame(game));
            } else {
                HBox5List.add(convertToGame(game));
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

    public List<MediumGameTileController> getMediumGameTileControllers() {
        return gameTileControllers;
    }

    // Event Handlers
    @FXML
    void HandlesClickedButton(MouseEvent event) {
        Object source = event.getSource();

        if (source == LibrarySearchButton_Image) {
            System.out.println(selectedGenres);
        }
    }

    public void setSearchQuery(String query) {
        if (LibrarySearchField_TextField != null) {
            LibrarySearchField_TextField.setText(query);
        }
    }
}