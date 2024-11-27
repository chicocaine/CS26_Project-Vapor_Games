package User_Interface;

import Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class VGGameTileController {

    @FXML
    private Label HomePageDiscoverGamePrice_Label;

    @FXML
    private Label HomePageDiscoverGameTitle_Label;

    @FXML
    private ImageView HomePageDiscoverGame_Image;

    @FXML
    private Pane GameMediumTile;

    private Game game;
    private VGMainScreenController mainController; // Reference to the main screen controller

    // Setter for game details
    public void setGameDetails(Game game) {
        this.game = game;
        if (game != null) {
            HomePageDiscoverGameTitle_Label.setText(game.getTitle());
            HomePageDiscoverGamePrice_Label.setText(game.getPrice());
            String imagePath = (game.getImagePath() != null && !game.getImagePath().isEmpty()) ? game.getImagePath() : "/images/default-image.png";
            HomePageDiscoverGame_Image.setImage(new Image(imagePath));
        }
    }

    // Setter for the main controller
    public void setMainController(VGMainScreenController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) throws IOException {
        if (event.getSource() == GameMediumTile) {
            // Load the VGGamePage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VGGamePage.fxml"));
            Pane gamePagePane = loader.load();

            // Get the game page controller and pass the game details
            //VGGamePageController gamePageController = loader.getController();
            //gamePageController.displayGameDetails(game);

            // Update the MainContent_Pane via the main controller
            if (mainController != null) {
                mainController.setMainContent_Pane(gamePagePane);
            } else {
                System.err.println("[ERROR] Main controller reference is null!");
            }
        }
    }
}
