package User_Interface;

import Games.Games;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CartPageTileController {

    private Games game;
    private MainScreenController mainController;

    @FXML
    private Label GameName_Label;

    @FXML
    private ImageView GameTileImage_Image;

    @FXML
    private Label GameTilePrice_Label;

    @FXML
    private Button GameTileRemove_Button;

    @FXML
    void HandlesMouseClicked(MouseEvent event) {

    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setGameDetails(Games game) {
        this.game = game;
        if (game != null) {
            GameName_Label.setText(game.getGameTitle());
            GameTilePrice_Label.setText(toString().valueOf(game.getGamePrice()));
            String imagePath = (game.getCardImageURL() != null && !game.getCardImageURL().isEmpty()) ? game.getCardImageURL() : "/images/default-image.png";
            GameTileImage_Image.setImage(new Image(imagePath));
        }

    }
}
