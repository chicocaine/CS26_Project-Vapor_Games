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

public class VGMainScreenController {

    private Stage stage;

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
            toggleButtonState(true, false);
            highlightSelectedButton(LibraryButton, StoreButton, true, false);
            LoadLibraryPage();
        }
    }

    private void handleStoreButton() {
        if (!isStoreButtonClicked) {
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
        // Add functionality to show account options or settings here
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
        String username = "march"; // Replace with actual username retrieval logic
        AccountUser_Label.setText(username);

        String profileImagePath = "/Image/ProfileTestPicture.png"; // Replace with actual path
        AccountPicture_Image.setImage(new Image(profileImagePath));
    }

    public void loadPane(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = loader.load();
            MainContent_Pane.getChildren().setAll(newPane);
            currentPane = newPane;

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
