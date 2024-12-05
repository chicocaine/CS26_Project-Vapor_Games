package User_Interface;

import Accounts.UserSession;
import Games.Games;
import Transaction.CartManager;
import Accounts.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartPageController {

    private final List<Games> MyCart = new ArrayList<>();
    private final List<CartPageTileController> gameTileControllers = new ArrayList<>();

    // Add a User reference here
    private User currentUser;
    private CartManager cartManager;

    @FXML
    private VBox CartHBox_HBox;

    @FXML
    private Button CheckOut_Button;

    @FXML
    private Label StoreCreditsBalance_Label;

    @FXML
    private Label TotalCostPrice_Label;

    // Pass the User object and CartManager instance to this controller
    public void setUser(User user, CartManager cartManager) {
        this.currentUser = user;
        this.cartManager = cartManager;
        System.out.println("[INFO] User set in CartPageController: " + currentUser);
        System.out.println("[INFO] CartManager set in CartPageController: " + cartManager);
    }


    @FXML
    void HandlesButtonClicked(MouseEvent event) {
        // Handle button click (e.g., checkout)
    }

    @FXML
    public void initialize() {
        loadUserCart();
        populateGameTiles(CartHBox_HBox, MyCart);
        totalCostPrice();
        System.out.println("[INFO] user CartPageController initialized. "+ currentUser);
        System.out.println("[INFO] user CartPageController initialized. "+ cartManager);
    }

    private void totalCostPrice() {
        if (currentUser != null && cartManager != null) {
            TotalCostPrice_Label.setText(String.valueOf(cartManager.getTotalPrice(cartManager.getCart(currentUser))));
        }
    }

    private void populateGameTiles(VBox CartHBox_HBox, List<Games> gamesList) {
        CartHBox_HBox.getChildren().clear();

        for (int i = 0; i < Math.min(6, gamesList.size()); i++) {
            Games game = gamesList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CartPageTile.fxml"));
                Pane cartTilePane = loader.load();
                CartPageTileController tileController = loader.getController();

                // Store the tile controller for future reference
                gameTileControllers.add(tileController);

                tileController.setGameDetails(game);
                CartHBox_HBox.getChildren().add(cartTilePane);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load game tile.");
            }
        }
    }

    private void loadUserCart() {
        // Use the cartManager to load the cart for the current currentUser
        if (currentUser != null && cartManager != null) {
            MyCart.clear();  // Clear any existing items in MyCart
            MyCart.addAll(cartManager.getCart(currentUser)); // Get cart for the currentUser and add it to MyCart
        }
    }

    private Games convertToGame(Games games) {
        return new Games(
                games.getGameID(),
                games.getGameTitle(),
                games.getGameReleaseDate(),
                games.getGameDescription(),
                games.getGamePrice(),
                games.getGenreList(),
                games.isAvailable(),
                games.getCardImageURL(),
                games.getShowcaseImagesURL()
        );
    }

    public List<CartPageTileController> getCartPageTileControllers() {
        return gameTileControllers;
    }
}
