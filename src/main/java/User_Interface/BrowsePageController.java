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
    private MainScreenController mainScreenController;

    private List<String> selectedGenres = new ArrayList<>();
    private Map<CheckBox, String> genreMap = new HashMap<>();

    @FXML
    private CheckBox Action_Genre, Adventure_Genre, ArtGame_Genre, BattleRoyale_Genre,
            BoardGame_Genre, BulletHell_Genre, BusinessSimulator_Genre, CardGame_Genre,
            Casual_Genre, CityBuilder_Genre, CoOp_Genre, Comedy_Genre, Crafting_Genre,
            DatingSimulator_Genre, Educational_Genre, FPS_Genre, Fighting_Genre,
            HackAndSlash_Genre, Horror_Genre, IdleGame_Genre, InteractiveStory_Genre,
            LifeSimulator_Genre, MMO_Genre, MOBA_Genre, Metroidvania_Genre, Multiplayer_Genre,
            Mystery_Genre, OpenWorld_Genre, Party_Genre, Platformer_Genre, Puzzle_Genre,
            Racing_Genre, RealTimeStrategy_Genre, Rhythm_Genre, Roguelike_Genre, Roguelite_Genre,
            RolePlaying_Genre, SandBox_Genre, Simulation_Genre, SinglePlayer_Genre, Sports_Genre,
            Stealth_Genre, Strategy_Genre, SurvivalHorror_Genre, Survival_Genre, TacticalRPG_Genre,
            TextAdventure_Genre, ThirdPersonShooter_Genre, TowerDefense_Genre, TurnBased_Genre,
            TwoDimension_Genre, Tycoon_Genre, VisualNovel_Genre, Zombie_Genre, Shooter_Genre;

    @FXML
    private HBox HBox1, HBox2, HBox3, HBox4, HBox5, HBox6, HBox7, HBox8, HBox9, HBox10,
            HBox11, HBox12, HBox13, HBox14, HBox15, HBox16, HBox17, HBox18, HBox19, HBox20;

    @FXML
    private TextField LibraryPriceSearchMaximum_TextField, LibraryPriceSearchMinimum_TextField, LibrarySearchField_TextField;

    @FXML
    private ImageView LibrarySearchButton_Image;

    @FXML
    private Pane MainPage_Pane;

    private final List<Games> HBoxList1 = new ArrayList<>();
    private final List<Games> HBoxList2 = new ArrayList<>();
    private final List<Games> HBoxList3 = new ArrayList<>();
    private final List<Games> HBoxList4 = new ArrayList<>();
    private final List<Games> HBoxList5 = new ArrayList<>();
    private final List<Games> HBoxList6 = new ArrayList<>();
    private final List<Games> HBoxList7 = new ArrayList<>();
    private final List<Games> HBoxList8 = new ArrayList<>();
    private final List<Games> HBoxList9 = new ArrayList<>();
    private final List<Games> HBoxList10 = new ArrayList<>();
    private final List<Games> HBoxList11 = new ArrayList<>();
    private final List<Games> HBoxList12 = new ArrayList<>();
    private final List<Games> HBoxList13 = new ArrayList<>();
    private final List<Games> HBoxList14 = new ArrayList<>();
    private final List<Games> HBoxList15 = new ArrayList<>();
    private final List<Games> HBoxList16 = new ArrayList<>();
    private final List<Games> HBoxList17 = new ArrayList<>();
    private final List<Games> HBoxList18 = new ArrayList<>();
    private final List<Games> HBoxList19 = new ArrayList<>();
    private final List<Games> HBoxList20 = new ArrayList<>();

    // Store references to all MediumGameTileController instances
    private final List<MediumGameTileController> gameTileControllers = new ArrayList<>();

    public void initialize() {
        initializeGameLists();
        populateGameTiles(HBox1, HBoxList1);
        populateGameTiles(HBox2, HBoxList2);
        populateGameTiles(HBox3, HBoxList3);
        populateGameTiles(HBox4, HBoxList4);
        populateGameTiles(HBox5, HBoxList5);
        populateGameTiles(HBox6, HBoxList6);
        populateGameTiles(HBox7, HBoxList7);
        populateGameTiles(HBox8, HBoxList8);
        populateGameTiles(HBox9, HBoxList9);
        populateGameTiles(HBox10, HBoxList10);
        populateGameTiles(HBox11, HBoxList11);
        populateGameTiles(HBox12, HBoxList12);
        populateGameTiles(HBox13, HBoxList13);
        populateGameTiles(HBox14, HBoxList14);
        populateGameTiles(HBox15, HBoxList15);
        populateGameTiles(HBox16, HBoxList16);
        populateGameTiles(HBox17, HBoxList17);
        populateGameTiles(HBox18, HBoxList18);
        populateGameTiles(HBox19, HBoxList19);
        populateGameTiles(HBox20, HBoxList20);

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
        genreMap.put(Fighting_Genre, "Fighting");
        genreMap.put(FPS_Genre, "First-Person Shooter (FPS)");
        genreMap.put(HackAndSlash_Genre, "Hack and Slash");
        genreMap.put(Horror_Genre, "Horror");
        genreMap.put(IdleGame_Genre, "Idle Game");
        genreMap.put(InteractiveStory_Genre, "Interactive Story");
        genreMap.put(LifeSimulator_Genre, "Life Simulation");
        genreMap.put(MMO_Genre, "Massively Multiplayer Online (MMO)");
        genreMap.put(Metroidvania_Genre, "Metroidvania");
        genreMap.put(MOBA_Genre, "Multiplayer Online Battle Arena (MOBA)");
        genreMap.put(Mystery_Genre, "Mystery");
        genreMap.put(OpenWorld_Genre, "Open World");
        genreMap.put(Party_Genre, "Party");
        genreMap.put(Platformer_Genre, "Platformer");
        genreMap.put(Puzzle_Genre, "Puzzle");
        genreMap.put(Racing_Genre, "Racing");
        genreMap.put(RealTimeStrategy_Genre, "Real-Time Strategy (RTS)");
        genreMap.put(Rhythm_Genre, "Rhythm");
        genreMap.put(Roguelike_Genre, "Roguelike");
        genreMap.put(Roguelite_Genre, "Roguelite");
        genreMap.put(RolePlaying_Genre, "Role-Playing (RPG)");
        genreMap.put(SandBox_Genre, "Sandbox");
        genreMap.put(Shooter_Genre, "Shooter");
        genreMap.put(Simulation_Genre, "Simulation");
        genreMap.put(SinglePlayer_Genre, "Single Player");
        genreMap.put(Sports_Genre, "Sports");
        genreMap.put(Stealth_Genre, "Stealth");
        genreMap.put(Strategy_Genre, "Strategy");
        genreMap.put(Survival_Genre, "Survival");
        genreMap.put(SurvivalHorror_Genre, "Survival Horror");
        genreMap.put(TacticalRPG_Genre, "Tactical RPG");
        genreMap.put(TextAdventure_Genre, "Text Adventure");
        genreMap.put(ThirdPersonShooter_Genre, "Third-Person Shooter");
        genreMap.put(TowerDefense_Genre, "Tower Defense");
        genreMap.put(TurnBased_Genre, "Turn-Based Strategy");
        genreMap.put(Tycoon_Genre, "Tycoon");
        genreMap.put(VisualNovel_Genre, "Visual Novel");
        genreMap.put(TwoDimension_Genre, "2D");
        genreMap.put(Crafting_Genre, "Crafting");
        genreMap.put(Multiplayer_Genre, "Multiplayer");
        genreMap.put(Zombie_Genre, "Zombie");

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

    private void categorizeGames(List<Games> gamesList) {
        // Clear existing lists
        HBoxList1.clear();
        HBoxList2.clear();
        HBoxList3.clear();
        HBoxList4.clear();
        HBoxList5.clear();
        HBoxList6.clear();
        HBoxList7.clear();
        HBoxList8.clear();
        HBoxList9.clear();
        HBoxList10.clear();
        HBoxList11.clear();
        HBoxList12.clear();
        HBoxList13.clear();
        HBoxList14.clear();
        HBoxList15.clear();
        HBoxList16.clear();
        HBoxList17.clear();
        HBoxList18.clear();
        HBoxList19.clear();
        HBoxList20.clear();

        for (int i = 0; i < gamesList.size(); i++) {
            Games game = gamesList.get(i);
            int hboxIndex = i / 4; // Determine which HBox to use

            switch (hboxIndex) {
                case 0:
                    HBoxList1.add(convertToGame(game));
                    break;
                case 1:
                    HBoxList2.add(convertToGame(game));
                    break;
                case 2:
                    HBoxList3.add(convertToGame(game));
                    break;
                case 3:
                    HBoxList4.add(convertToGame(game));
                    break;
                case 4:
                    HBoxList5.add(convertToGame(game));
                    break;
                case 5:
                    HBoxList6.add(convertToGame(game));
                    break;
                case 6:
                    HBoxList7.add(convertToGame(game));
                    break;
                case 7:
                    HBoxList8.add(convertToGame(game));
                    break;
                case 8:
                    HBoxList9.add(convertToGame(game));
                    break;
                case 9:
                    HBoxList10.add(convertToGame(game));
                    break;
                case 10:
                    HBoxList11.add(convertToGame(game));
                    break;
                case 11:
                    HBoxList12.add(convertToGame(game));
                    break;
                case 12:
                    HBoxList13.add(convertToGame(game));
                    break;
                case 13:
                    HBoxList14.add(convertToGame(game));
                    break;
                case 14:
                    HBoxList15.add(convertToGame(game));
                    break;
                case 15:
                    HBoxList16.add(convertToGame(game));
                    break;
                case 16:
                    HBoxList17.add(convertToGame(game));
                    break;
                case 17:
                    HBoxList18.add(convertToGame(game));
                    break;
                case 18:
                    HBoxList19.add(convertToGame(game));
                    break;
                case 19:
                    HBoxList20.add(convertToGame(game));
                    break;
                default:
                    System.out.println("No more HBoxes available.");
                    break;
            }
        }
    }

    private void filterAndDisplayGamesByGenres() {
        GamesManager gamesManager = new GamesManager();
        ArrayList<Games> filteredGames;

        if (selectedGenres.isEmpty()) {
            filteredGames = gamesManager.getAllGames();
        } else {
            filteredGames = gamesManager.filterByGenre(new ArrayList<>(selectedGenres));
        }

        categorizeGames(filteredGames);

        // Repopulate HBoxes with filtered games
        populateGameTiles(HBox1, HBoxList1);
        populateGameTiles(HBox2, HBoxList2);
        populateGameTiles(HBox3, HBoxList3);
        populateGameTiles(HBox4, HBoxList4);
        populateGameTiles(HBox5, HBoxList5);
        populateGameTiles(HBox6, HBoxList6);
        populateGameTiles(HBox7, HBoxList7);
        populateGameTiles(HBox8, HBoxList8);
        populateGameTiles(HBox9, HBoxList9);
        populateGameTiles(HBox10, HBoxList10);
        populateGameTiles(HBox11, HBoxList11);
        populateGameTiles(HBox12, HBoxList12);
        populateGameTiles(HBox13, HBoxList13);
        populateGameTiles(HBox14, HBoxList14);
        populateGameTiles(HBox15, HBoxList15);
        populateGameTiles(HBox16, HBoxList16);
        populateGameTiles(HBox17, HBoxList17);
        populateGameTiles(HBox18, HBoxList18);
        populateGameTiles(HBox19, HBoxList19);
        populateGameTiles(HBox20, HBoxList20);
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
                tileController.setMainController(mainScreenController); // Set the main controller
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

        categorizeGames(allGames);
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

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    void HandlesClickedButton(MouseEvent event) {
        if (event.getSource() == LibrarySearchButton_Image) {
            String query = LibrarySearchField_TextField.getText();
            String minPriceText = LibraryPriceSearchMinimum_TextField.getText();
            String maxPriceText = LibraryPriceSearchMaximum_TextField.getText();
            GamesManager gamesManager = new GamesManager();
            ArrayList<Games> searchResults = new ArrayList<>();

            boolean hasQuery = query != null && !query.isEmpty();
            boolean hasMinPrice = minPriceText != null && !minPriceText.isEmpty();
            boolean hasMaxPrice = maxPriceText != null && !maxPriceText.isEmpty();

            // Debugging statements
            System.out.println("Query: " + query);
            System.out.println("Min Price Text: " + minPriceText);
            System.out.println("Max Price Text: " + maxPriceText);
            System.out.println("Has Query: " + hasQuery);
            System.out.println("Has Min Price: " + hasMinPrice);
            System.out.println("Has Max Price: " + hasMaxPrice);

            try {
                if (hasQuery && hasMinPrice && hasMaxPrice) {
                    double minPrice = Double.parseDouble(minPriceText) * 6.9;
                    double maxPrice = Double.parseDouble(maxPriceText) * 6.9;
                    System.out.println("Searching by title and price range...");
                    searchResults = gamesManager.searchGamesByTitleAndPriceRange(query, minPrice, maxPrice);
                } else if (hasQuery) {
                    System.out.println("Searching by title...");
                    searchResults = gamesManager.searchGames(query);
                } else if (hasMinPrice && hasMaxPrice) {
                    double minPrice = Double.parseDouble(minPriceText) * 6.9;
                    double maxPrice = Double.parseDouble(maxPriceText) * 6.9;
                    System.out.println("Filtering by price range...");
                    searchResults = gamesManager.filterByPriceRange(minPrice, maxPrice);
                    System.out.println(minPrice + " " + maxPrice);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("Invalid price range input.");
            }

            System.out.println("Search Results: " + searchResults.size() + " games found.");

            categorizeGames(searchResults);

            // Repopulate HBoxes with search results
            populateGameTiles(HBox1, HBoxList1);
            populateGameTiles(HBox2, HBoxList2);
            populateGameTiles(HBox3, HBoxList3);
            populateGameTiles(HBox4, HBoxList4);
            populateGameTiles(HBox5, HBoxList5);
            populateGameTiles(HBox6, HBoxList6);
            populateGameTiles(HBox7, HBoxList7);
            populateGameTiles(HBox8, HBoxList8);
            populateGameTiles(HBox9, HBoxList9);
            populateGameTiles(HBox10, HBoxList10);
            populateGameTiles(HBox11, HBoxList11);
            populateGameTiles(HBox12, HBoxList12);
            populateGameTiles(HBox13, HBoxList13);
            populateGameTiles(HBox14, HBoxList14);
            populateGameTiles(HBox15, HBoxList15);
            populateGameTiles(HBox16, HBoxList16);
            populateGameTiles(HBox17, HBoxList17);
            populateGameTiles(HBox18, HBoxList18);
            populateGameTiles(HBox19, HBoxList19);
            populateGameTiles(HBox20, HBoxList20);
        }
    }

    public void setSearchQuery(String query) {
        if (LibrarySearchField_TextField != null) {
            LibrarySearchField_TextField.setText(query);
        }
    }
}