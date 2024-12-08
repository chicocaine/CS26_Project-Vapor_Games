package User_Interface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Games.Games;

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

    private Games game;
    private MainScreenController mainController; // Reference to the main screen controller

    // Setter for game details
    public void setGameDetails(Games game) {
        this.game = game;
        if (game != null) {
            MediumGameTile_Name.setText(game.getGameTitle());
            double gamePrice = game.getConvertedGamePrice();
            MediumGameTile_Price.setText(String.format("%.2f", game.getConvertedGamePrice()));
            String imagePath = (game.getCardImageURL() != null && !game.getCardImageURL().isEmpty()) ? game.getCardImageURL() : "/images/default-image.png";
            System.out.println(imagePath);
            try {
                MediumGameTile_Image.setImage(new Image(imagePath, true));
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to load image: " + imagePath);
                MediumGameTile_Image.setImage(new Image("/images/default-image.png"));
            }
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

            if (mainController != null) {
                mainController.setMainContent_Pane(gamePagePane);
            } else {
                System.err.println("[ERROR] Main controller reference is null!");
            }
        }
    }
}