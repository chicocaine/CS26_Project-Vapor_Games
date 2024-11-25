package User_Interface;

import Model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

    private VGMainScreenController mainScreenController;

    public void setMainScreenController(VGMainScreenController controller) {
        this.mainScreenController = controller;
    }

    public void setGameDetails(Game game) {
        this.game = game;
        if (game != null) {
            HomePageDiscoverGameTitle_Label.setText(game.getTitle());
            HomePageDiscoverGamePrice_Label.setText(game.getPrice());
            String imagePath = (game.getImagePath() != null && !game.getImagePath().isEmpty()) ? game.getImagePath() : "/images/default-image.png";
            try {
                HomePageDiscoverGame_Image.setImage(new Image(imagePath));
            } catch (Exception e) {
                System.out.println("[ERROR] Failed to load image: " + imagePath);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) {
        if (event.getSource() == GameMediumTile) {
            if (mainScreenController != null) {
                mainScreenController.loadPane("/VGGamePage.fxml");
            } else {
                System.out.println("[ERROR] MainScreenController is not set!");
            }
        }
    }
}
