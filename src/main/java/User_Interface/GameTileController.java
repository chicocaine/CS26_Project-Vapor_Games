// src/main/java/User_Interface/GameTileController.java
package User_Interface;

import Accounts.UserSession;
import User_Interface.PopUps.LoginSuccessful;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Games.Games;
import Accounts.User;

import java.io.IOException;

public class GameTileController {

    @FXML
    private Label HomePageDiscoverGamePrice_Label;

    @FXML
    private Label HomePageDiscoverGameTitle_Label;

    @FXML
    private ImageView HomePageDiscoverGame_Image;

    @FXML
    private Pane GameMediumTile;

    private Games game;
    private MainScreenController mainController;

    public void setGameDetails(Games game) {
        this.game = game;
        if (game != null) {
            HomePageDiscoverGameTitle_Label.setText(game.getGameTitle());
            HomePageDiscoverGamePrice_Label.setText(toString().valueOf(game.getGamePrice()));
            String imagePath = (game.getCardImageURL() != null && !game.getCardImageURL().isEmpty()) ? game.getCardImageURL() : "/images/default-image.png";
            HomePageDiscoverGame_Image.setImage(new Image(imagePath));
        }
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) throws IOException {
        if (event.getSource() == GameMediumTile) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GamePage.fxml"));
            Pane gamePagePane = loader.load();

            GamePageController gamePageController = loader.getController();
            gamePageController.displayGameDetails(game);

            // Get the current user from UserSession
            User currentUser = UserSession.getInstance().getCurrentUser();
            System.out.println("Current user in GameTileController: " + currentUser);
            gamePageController.setCurrentUser(currentUser);

            if (mainController != null) {
                mainController.setMainContent_Pane(gamePagePane);
            } else {
                System.err.println("[ERROR] Main controller reference is null!");
            }
        }
    }
}