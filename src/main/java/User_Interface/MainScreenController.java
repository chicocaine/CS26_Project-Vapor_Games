package User_Interface;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class MainScreenController {

    private Stage stage;

    // === UI COMPONENTS ===
    @FXML
    private ImageView AccountDropDown_Image, AccountPicture_Image, SearchButton_Button;

    @FXML
    private Label AccountUser_Label;

    @FXML
    private Pane BrowsePane_Button, CartButton_Pane, DiscoverButton_Pane, MainContent_Pane, ReturnButton_Pane, LogoutButton;

    @FXML
    private HBox LibraryButton, StoreButton;

    @FXML
    private TextField SearchField_TextField;

    private boolean isLibraryButtonClicked = false;
    private boolean isStoreButtonClicked = true;

    // === INITIALIZATION & SETUP ===
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // This is the first function called when the controller is loaded
    @FXML
    public void initialize() {
        LoadHomePage();  // Load the initial home page (store page)
        highlightSelectedButton(LibraryButton, StoreButton, false, true);  // Highlight the StoreButton
        updateAccountInfo();  // Set the user account info (e.g., username, profile image)
    }

    // === BUTTON HANDLERS ===
    @FXML
    void HandlesClickedButton(MouseEvent event) {
        Object source = event.getSource();

        if (source == LogoutButton) {
            handleLogout();  // Handle user logout
        } else if (source == BrowsePane_Button) {
            handleBrowseButton();  // Handle browsing
        } else if (source == LibraryButton) {
            handleLibraryButton();  // Handle library button click
        } else if (source == StoreButton || source == DiscoverButton_Pane) {
            handleStoreButton();  // Handle store button click
        } else if (source == SearchButton_Button) {
            handleSearchButton();  // Handle search button click
        } else if (source == AccountDropDown_Image) {
            handleAccountDropdown();  // Handle account dropdown interaction
        }
    }

    // === HANDLE SPECIFIC BUTTON ACTIONS ===
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);  // Exit the program
        }
    }

    private void handleLibraryButton() {
        if (!isLibraryButtonClicked) {
            toggleButtonState(true, false);  // Set library button clicked state
            highlightSelectedButton(LibraryButton, StoreButton, true, false);  // Highlight the LibraryButton
            LoadLibraryPage();  // Load the library page
        }
    }

    private void handleStoreButton() {
        if (!isStoreButtonClicked) {
            toggleButtonState(false, true);  // Set store button clicked state
            highlightSelectedButton(LibraryButton, StoreButton, false, true);  // Highlight the StoreButton
            LoadHomePage();  // Load the home page (store page)
        }
    }

    private void handleBrowseButton() {
        LoadBrowsePage(SearchField_TextField.getText());  // Perform search and load browse page
        SearchField_TextField.setText("");  // Clear the search field
    }

    private void handleSearchButton() {
        String query = SearchField_TextField.getText();
        if (!query.isEmpty()) {
            performSearch(query);  // Perform search if query is not empty
        }
    }

    private void handleAccountDropdown() {
        // Add functionality for account options dropdown
    }

    // === HELPER METHODS ===
    private void performSearch(String query) {
        handleBrowseButton();  // Handle search by calling browse button handler
    }

    private void toggleButtonState(boolean libraryState, boolean storeState) {
        isLibraryButtonClicked = libraryState;  // Set the library button state
        isStoreButtonClicked = storeState;  // Set the store button state
    }

    private void highlightSelectedButton(HBox library, HBox store, boolean isLibrary, boolean isStore) {
        if (isLibrary) {
            library.getStyleClass().add("LNB_SelectionHBoxHighlighted");
            store.getStyleClass().remove("LNB_SelectionHBoxHighlighted");
        } else if (isStore) {
            store.getStyleClass().add("LNB_SelectionHBoxHighlighted");
            library.getStyleClass().remove("LNB_SelectionHBoxHighlighted");
        }
    }

    // === PAGE LOADING METHODS ===
    private void LoadHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StorePage.fxml"));
            Pane storePagePane = loader.load();
            setMainContent_Pane(storePagePane);  // Set the main content to store page

            // Access the controller for the loaded page
            StorePageController storePageController = loader.getController();
            for (GameTileController tileController : storePageController.getGameTileControllers()) {
                tileController.setMainController(this);  // Set the main controller for each tile
            }

        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    private void LoadBrowsePage(String query) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrowsePage.fxml"));
            Pane browsePagePane = loader.load();
            setMainContent_Pane(browsePagePane);  // Set the main content to browse page

            BrowsePageController browsePageController = loader.getController();
            if (query != null && !query.isEmpty()) {
                browsePageController.setSearchQuery(query);  // Pass search query to the browse page
            }

            // Set the main controller for each MediumGameTileController, if applicable
            for (MediumGameTileController tileController : browsePageController.getMediumGameTileControllers()) {
                tileController.setMainController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    private void LoadLibraryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryPage.fxml"));
            Pane libraryPagePane = loader.load();
            setMainContent_Pane(libraryPagePane);  // Set the main content to library page

            LibraryPageController libraryPageController = loader.getController();

            // Set the main controller for each tile in the library page
            for (MediumGameTileController tileController : libraryPageController.getMediumGameTileControllers()) {
                tileController.setMainController(this);  // Set the main controller for each tile
            }

        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    // === ACCOUNT INFO UPDATE ===
    private void updateAccountInfo() {
        String username = "march";  // Replace with actual username retrieval logic
        AccountUser_Label.setText(username);

        String profileImagePath = "/Image/ProfileTestPicture.png";  // Replace with actual image path
        AccountPicture_Image.setImage(new Image(profileImagePath));  // Set profile image
    }

    // === GENERIC PAGE LOADING METHOD ===
    public void loadPane(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = loader.load();
            setMainContent_Pane(newPane);  // Set the main content to the new pane
        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    // === FADE IN/OUT TRANSITIONS ===
    public void setMainContent_Pane(Pane newPane) {
        Pane currentPane = (MainContent_Pane.getChildren().isEmpty()) ? null : (Pane) MainContent_Pane.getChildren().get(0);

        if (currentPane != null) {
            // Fade out the current pane
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), currentPane);
            fadeOut.setFromValue(1.0);  // Fully visible
            fadeOut.setToValue(0.0);    // Fully transparent

            fadeOut.setOnFinished(event -> {
                // Remove the current pane and add the new one with opacity set to 0 initially
                MainContent_Pane.getChildren().setAll(newPane);
                newPane.setOpacity(0.0);  // Set the new pane as invisible initially

                // Fade in the new pane
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newPane);
                fadeIn.setFromValue(0.0);  // Start fully transparent
                fadeIn.setToValue(1.0);    // End fully visible

                fadeIn.play();
            });

            fadeOut.play();
        } else {
            // No current pane, directly show the new one with fade-in (initially invisible)
            MainContent_Pane.getChildren().setAll(newPane);
            newPane.setOpacity(0.0);  // Set the new pane as invisible initially

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newPane);
            fadeIn.setFromValue(0.0);  // Start fully transparent
            fadeIn.setToValue(1.0);    // End fully visible

            fadeIn.play();
        }
    }
}
