package User_Interface;

import Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

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
    private boolean isStoreButtonClicked = true;

    @FXML
    public void initialize() {
        LoadHomePage();
        highlightSelectedButton(LibraryButton, StoreButton, false, true);
        updateAccountInfo();
    }

    @FXML
    void HandlesClickedButton(MouseEvent event) {
        Object source = event.getSource();

        if (source == LogoutButton) {
            handleLogout();
        } else if (source == DiscoverButton_Pane) {
            handleStoreButton();
        } else if (source == LibraryButton) {
            handleLibraryButton();
        } else if (source == StoreButton) {
            handleStoreButton();
        } else if (source == SearchButton_Button) {
            handleSearchButton();
        } else if (source == AccountDropDown_Image) {
            handleAccountDropdown();
        }
    }

    private void handleLogout() {
        System.out.println("[DEBUG] Logout button clicked.");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    private void handleLibraryButton() {
        if (!isLibraryButtonClicked) {
            System.out.println("[DEBUG] Library button clicked.");
            toggleButtonState(true, false);
            highlightSelectedButton(LibraryButton, StoreButton, true, false);
            LoadLibraryPage();
        }
    }

    private void handleStoreButton() {
        if (!isStoreButtonClicked) {
            System.out.println("[DEBUG] Store button clicked.");
            toggleButtonState(false, true);
            highlightSelectedButton(LibraryButton, StoreButton, false, true);
            LoadHomePage();
        }
    }

    private void handleSearchButton() {
        String query = SearchField_TextField.getText();
        if (!query.isEmpty()) {
            performSearch(query);
        }
    }

    private void handleAccountDropdown() {
        System.out.println("[DEBUG] Account dropdown clicked.");
    }

    private void performSearch(String query) {
        System.out.println("[DEBUG] Performing search for: " + query);
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

    private void updateAccountInfo() {
        String username = "march";
        AccountUser_Label.setText(username);
        String profileImagePath = "/Image/ProfileTestPicture.png";
        Image profileImage = new Image(profileImagePath);
        AccountPicture_Image.setImage(profileImage);
    }

    public void setMainContentPane(Pane newPane) {
        if (currentPane != null) {
            MainContent_Pane.getChildren().remove(currentPane);
        }
        MainContent_Pane.getChildren().add(newPane);
        currentPane = newPane;
    }

    public void loadPane(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = loader.load();

            // Set the mainScreenController before adding the newPane
            VGStorePageController storePageController = loader.getController();
            storePageController.setMainScreenController(this);  // Set the controller here

            setMainContentPane(newPane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load pane: " + fxmlFile);
        }
    }

}
