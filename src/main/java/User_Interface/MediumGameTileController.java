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

public class MediumGameTileController {

    @FXML
    private Pane MediumGameTile;

    @FXML
    private ImageView MediumGameTile_Image;

    @FXML
    private Label MediumGameTile_Name;

    @FXML
    private Label MediumGameTile_Price;

    private Game game;
    private MainScreenController mainController; // Reference to the main screen controller

    // Setter for game details
    public void setGameDetails(Game game) {
        this.game = game;
        if (game != null) {
            MediumGameTile_Name.setText(game.getTitle());
            MediumGameTile_Price.setText(game.getPrice());
            String imagePath = (game.getImagePath() != null && !game.getImagePath().isEmpty()) ? game.getImagePath() : "/images/default-image.png";
            MediumGameTile_Image.setImage(new Image(imagePath));
        }
    }

    // Setter for the main controller
    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) throws IOException {
        if (event.getSource() == MediumGameTile) {
            // Load the GamePage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GamePage.fxml"));
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
