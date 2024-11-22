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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class VGMainScreenController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private ImageView AccountDropDown_Image;

    @FXML
    private ImageView AccountPicture_Image;

    @FXML
    private Label AccountUser_Label;

    @FXML
    private Pane BrowsePane_Button;

    @FXML
    private Pane CartButton_Pane;

    @FXML
    private Pane DiscoverButton_Pane;

    @FXML
    private HBox LibraryButton;

    @FXML
    private Pane MainContent_Pane;

    @FXML
    private Pane ReturnButton_Pane;

    @FXML
    private ImageView SearchButton_Button;

    @FXML
    private TextField SearchField_TextField;

    @FXML
    private HBox StoreButton;

    @FXML
    private HBox LogoutButton;

    private Pane currentPane;

    boolean isLibraryButtonClicked = false;
    boolean isStoreButtonClicked = false;

    private void highlightButtonClicked(boolean x, boolean y){
        if(x && !y){
            System.out.println("[DEBUG] Library Button is Highlighted");
            // Set the Library Button to be highlighted.

        } else if (!x && y){
            System.out.println("[DEBUG] Store Button is Highlighted");
            // Set the Store Button to be highlighted.
        } else {
            System.out.println("[INFO] I Don't know how you get to this.");
        }
    }

    @FXML
    void HandlesClickedButton(@NotNull MouseEvent event) {
        // Logout Button
        if(event.getSource() == LogoutButton){
            System.out.println("[DEBUG] Logout button clicked");
            System.exit(0);

            // Discover Button
        } else if (event.getSource() == DiscoverButton_Pane){
            System.out.println("[DEBUG] Discover button clicked");
            LoadHomePage();
            System.out.println("[DEBUG] (LoadHomePage();) Loaded!");

            // Library Button
        } else if (event.getSource() == LibraryButton){
            System.out.println("[DEBUG] Library button clicked");
            isLibraryButtonClicked = true;
            System.out.println("[DEBUG] isLibraryButtonClicked set to TRUE");
            isStoreButtonClicked = false;
            System.out.println("[DEBUG] isStoreButtonClicked set to FALSE");
            if(!isStoreButtonClicked && isLibraryButtonClicked){
                highlightButtonClicked(true, false);
            } else {
                System.out.println("[INFO] I Don't know how you get to this.");
            }

            // Store Button
        } else if (event.getSource() == StoreButton){
            System.out.println("[DEBUG] Store button clicked");
            isStoreButtonClicked = true;
            System.out.println("[DEBUG] isStoreButtonClicked set to TRUE");
            isLibraryButtonClicked = false;
            System.out.println("[DEBUG] isLibraryButtonClicked set to FALSE");
            if(isStoreButtonClicked && !isLibraryButtonClicked){
                highlightButtonClicked(false, true);
            } else {
                System.out.println("[INFO] I Don't know how you get to this.");
            }
        }
    }

    @FXML
    public void initialize() {
        LoadHomePage();
    }

    private void LoadHomePage() {
        loadPane("/VGHomePage.fxml");
    }

    private void loadPane(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(VGMainProgramApplication.class.getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();

            if (currentPane != null && currentPane.getId().equals(newPane.getId())) {
                System.out.println("[DEBUG]: The same pane is already loaded.");
                return;
            }

            if (currentPane != null) {
                currentPane.setVisible(false);
            }

            MainContent_Pane.getChildren().add(newPane);
            currentPane = newPane;
            currentPane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR]: Failed to load the pane.");
        }
    }
}