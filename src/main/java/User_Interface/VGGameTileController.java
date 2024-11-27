package User_Interface;

import Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    public void setGameDetails(Game game) {
        this.game = game;
        if (game != null) {
            HomePageDiscoverGameTitle_Label.setText(game.getTitle());
            HomePageDiscoverGamePrice_Label.setText(game.getPrice());
            String imagePath = (game.getImagePath() != null && !game.getImagePath().isEmpty()) ? game.getImagePath() : "/images/default-image.png";
            HomePageDiscoverGame_Image.setImage(new Image(imagePath));
        }
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) throws IOException {
        if (event.getSource() == GameMediumTile) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VGGamePage.fxml"));
            Pane mainScreenPane = loader.load();

            VGGamePageController GPC = loader.getController();

            GPC.displayGameDetails(game);

            Stage stage = (Stage) GameMediumTile.getScene().getWindow();
            stage.getScene().setRoot(mainScreenPane);
        }
    }
}
