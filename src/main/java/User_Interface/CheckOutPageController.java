package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import Games.Games;
import Transaction.CartManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CheckOutPageController {

    @FXML
    private RadioButton AGSCoin_RadioButton;

    @FXML
    private Label AccountName_Label;

    @FXML
    private Label CurrentBalance_Label;

    @FXML
    private Button PlaceOrder_Button;

    @FXML
    private VBox PurchaseSummary_VBox;

    @FXML
    private Label TotalCost_Label;

    private User currentUser = UserSession.getInstance().getCurrentUser();
    private CartManager cartManager = new CartManager();

    @FXML
    private void initialize() {
        loadUserInfo();
        loadPurchaseSummary();
    }

    @FXML
    void HandlesMouseClicked(MouseEvent event) {
        // Handle mouse click events
    }

    public void loadUserInfo() {
        AccountName_Label.setText(currentUser.getName());
        CurrentBalance_Label.setText(String.valueOf(currentUser.getWallet().getBalance()));
    }

    public void loadPurchaseSummary() {
        List<Games> cartItems = cartManager.getCart(currentUser);
        PurchaseSummary_VBox.getChildren().clear();

        double totalCost = 0.0;
        for (Games game : cartItems) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CheckOutPageTile.fxml"));
                Pane gameTilePane = loader.load();
                CheckOutPageTileController tileController = loader.getController();

                tileController.CheckOutTileGameName_Label.setText(game.getGameTitle());
                tileController.CheckOutGamePrice_Label.setText(String.format("%.2f", game.getGamePrice()));
                // Set the game image if available
                String imagepath = game.getCardImageURL();
                tileController.GameCheckOutTile_Image.setImage(new Image(imagepath));

                PurchaseSummary_VBox.getChildren().add(gameTilePane);
                totalCost += game.getGamePrice();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load checkout tile.");
            }
        }

        TotalCost_Label.setText(String.format("%.2f", totalCost));
    }
}