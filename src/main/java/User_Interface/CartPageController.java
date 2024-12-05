// src/main/java/User_Interface/CartPageController.java
package User_Interface;

import Accounts.UserSession;
import Games.Games;
import Transaction.CartManager;
import Accounts.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartPageController {

    private final List<Games> MyCart = new ArrayList<>();
    private final List<CartPageTileController> gameTileControllers = new ArrayList<>();

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

    public void setUser(User user, CartManager cartManager) {
        this.currentUser = user;
        this.cartManager = cartManager;
        System.out.println("[INFO] User set in CartPageController: " + currentUser);
        System.out.println("[INFO] CartManager set in CartPageController: " + cartManager);
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) {
        if (event.getSource() == CheckOut_Button) {
            loadCheckOutPage();
        }
    }

    @FXML
    public void initialize() {
        loadUserCart();
        populateGameTiles(CartHBox_HBox, MyCart);
        totalCostPrice();
    }

    private void totalCostPrice() {
        if (currentUser != null && cartManager != null) {
            double totalPrice = cartManager.getTotalPrice(cartManager.getCart(currentUser));
            TotalCostPrice_Label.setText(String.format("%.2f", totalPrice));
        }
    }

    private void populateGameTiles(VBox CartHBox_HBox, List<Games> gamesList) {
        CartHBox_HBox.getChildren().clear();
        gameTileControllers.clear();

        for (int i = 0; i < Math.min(6, gamesList.size()); i++) {
            Games game = gamesList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CartPageTile.fxml"));
                Pane cartTilePane = loader.load();
                CartPageTileController tileController = loader.getController();

                gameTileControllers.add(tileController);

                tileController.setGameDetails(game);
                tileController.setCartManager(cartManager);
                tileController.setCurrentUser(currentUser);
                tileController.setCartPageController(this);

                CartHBox_HBox.getChildren().add(cartTilePane);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load game tile.");
            }
        }
    }

    private void loadUserCart() {
        if (currentUser != null && cartManager != null) {
            MyCart.clear();
            MyCart.addAll(cartManager.getCart(currentUser));
            StoreCreditsBalance_Label.setText(String.valueOf(currentUser.getWallet().getBalance()));
        }
    }

    public void refreshCart() {
        loadUserCart();
        populateGameTiles(CartHBox_HBox, MyCart);
        totalCostPrice();
    }

    public List<CartPageTileController> getCartPageTileControllers() {
        return gameTileControllers;
    }

    private void loadCheckOutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CheckOutPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) CheckOut_Button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load checkout page.");
        }
    }
}