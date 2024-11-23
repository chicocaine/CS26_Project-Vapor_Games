package User_Interface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class VGMainScreenController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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

    private Pane currentPane;

    private boolean isLibraryButtonClicked = false;
    private boolean isStoreButtonClicked = true; // Default to StoreButton

    @FXML
    public void initialize() {
        // Set the initial page and highlight
        LoadHomePage();
        highlightSelectedButton(LibraryButton, StoreButton, false, true);
    }

    @FXML
    void HandlesClickedButton(MouseEvent event) {
        Object source = event.getSource();

        if (source == LogoutButton) {
            handleLogout();
        } else if (source == DiscoverButton_Pane) {
            System.out.println("Nothing In Placed");
        } else if (source == LibraryButton) {
            handleLibraryButton();
        } else if (source == StoreButton) {
            handleStoreButton();
        }
    }

    private void handleLogout() {
        System.out.println("[DEBUG] Logout button clicked.");
        try {
            // Perform any cleanup tasks before exiting, if necessary
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] Logout operation failed.");
        }
    }

    private void handleLibraryButton() {
        if (!isLibraryButtonClicked) {
            System.out.println("[DEBUG] Library button clicked.");
            toggleButtonState(true, false);
            highlightSelectedButton(LibraryButton, StoreButton, true, false);
            LoadLibraryPage(); // Load the library page regardless of whether it's already loaded
        }
    }

    private void handleStoreButton() {
        if (!isStoreButtonClicked) {
            System.out.println("[DEBUG] Store button clicked.");
            toggleButtonState(false, true);
            highlightSelectedButton(LibraryButton, StoreButton, false, true);
            LoadHomePage(); // Load the home page when Store is clicked
        }
    }

    private void toggleButtonState(boolean libraryState, boolean storeState) {
        isLibraryButtonClicked = libraryState;
        isStoreButtonClicked = storeState;
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

    private void LoadHomePage() {
        loadPane("/VGStorePage.fxml");
    }

    private void LoadLibraryPage() {
        loadPane("/VGLibraryPage.fxml");
    }

    private void loadPane(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(VGMainProgramApplication.class.getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();

            // No need to check if the pane is the same
            if (currentPane != null) {
                MainContent_Pane.getChildren().remove(currentPane); // Ensure previous pane is removed
            }

            MainContent_Pane.getChildren().add(newPane);
            currentPane = newPane;

            System.out.println("[DEBUG] Loaded pane: " + fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load pane: " + fxmlFile);
        }
    }
}
