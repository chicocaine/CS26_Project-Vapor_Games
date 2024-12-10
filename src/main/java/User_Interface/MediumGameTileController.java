package User_Interface;

import Accounts.UserSession;
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

public class MediumGameTileController {

    @FXML
    private Label MediumGameTile_Price;

    @FXML
    private Label MediumGameTile_Name;

    @FXML
    private ImageView MediumGameTile_Image;

    @FXML
    private Pane MediumGameTile;

    private Games game;
    private MainScreenController mainController;

    public void setGameDetails(Games game) {
        this.game = game;
        if (game != null) {
            MediumGameTile_Name.setText(game.getGameTitle());
            MediumGameTile_Price.setText(String.format("%.2f", game.getConvertedGamePrice()));
            String imagePath = (game.getCardImageURL() != null && !game.getCardImageURL().isEmpty()) ? game.getCardImageURL() : "/images/default-image.png";
            MediumGameTile_Image.setImage(new Image(imagePath));
        }
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) throws IOException {
        if (event.getSource() == MediumGameTile) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GamePage.fxml"));
            Pane gamePagePane = loader.load();

            GamePageController gamePageController = loader.getController();
            gamePageController.displayGameDetails(game);

            // Get the current user from UserSession
            User currentUser = UserSession.getInstance().getCurrentUser();
            System.out.println("Current user in MediumGameTileController: " + currentUser);
            gamePageController.setCurrentUser(currentUser);

            if (mainController != null) {
                mainController.setMainContent_Pane(gamePagePane);
            } else {
                System.err.println("[ERROR] Main controller reference is null!");
            }
        }
    }
}