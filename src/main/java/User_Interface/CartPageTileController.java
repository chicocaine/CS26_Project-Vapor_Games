// src/main/java/User_Interface/CartPageTileController.java
package User_Interface;

import Games.Games;
import Transaction.CartManager;
import Accounts.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CartPageTileController {

    private Games game;
    private MainScreenController mainController;
    private CartManager cartManager;
    private User currentUser;
    private CartPageController cartPageController;

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
        if (event.getSource() == GameTileRemove_Button) {
            removeGameFromCart();
        }
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setGameDetails(Games game) {
        this.game = game;
        if (game != null) {
            GameName_Label.setText(game.getGameTitle());
            GameTilePrice_Label.setText(String.format("%.2f", game.getGamePrice()));
            String imagePath = (game.getCardImageURL() != null && !game.getCardImageURL().isEmpty()) ? game.getCardImageURL() : "/images/default-image.png";
            GameTileImage_Image.setImage(new Image(imagePath));
        }
    }

    public void setCartManager(CartManager cartManager) {
        this.cartManager = cartManager;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCartPageController(CartPageController cartPageController) {
        this.cartPageController = cartPageController;
    }

    private void removeGameFromCart() {
        if (cartManager != null && currentUser != null && game != null) {
            cartManager.removeFromCart(currentUser, game);
            cartPageController.refreshCart();
        }
    }
}