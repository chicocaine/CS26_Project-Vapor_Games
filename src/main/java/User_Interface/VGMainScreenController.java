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
    private Pane TopNavigationBar_Pane;

    @FXML
    private Pane leftNavigationBar_Pane;

    private Pane currentPane;

    @FXML
    void HandlesClickedButton(MouseEvent event) {

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
                // Make the current pane invisible and push it to the history stack
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