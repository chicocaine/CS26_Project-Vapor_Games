package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import Games.Games;
import Library.LibraryManager;
import Transaction.Transaction;
import Transaction.CartManager;
import Utility.DBConnectionPool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CheckOutPageController {

    @FXML
    private ImageView CloseOrReturn_Image;

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
    private LibraryManager libraryManager = new LibraryManager();

    @FXML
    private void initialize() {
        loadUserInfo();
        loadPurchaseSummary();
        PlaceOrder_Button.setOnAction(event -> placeOrder());
    }

    @FXML
    void HandlesMouseClicked(MouseEvent event) {
        if (event.getSource() == CloseOrReturn_Image) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) CloseOrReturn_Image.getScene().getWindow();
                stage.setScene(new Scene(root));
                MainScreenController mainScreenController = loader.getController();
                mainScreenController.currentUser = currentUser;
                mainScreenController.setUserOnDashboard(currentUser);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load MainScreen.");
            }
        }
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

    private void placeOrder() {
        List<Games> cartItems = cartManager.getCart(currentUser);
        double totalCost = cartItems.stream().mapToDouble(Games::getGamePrice).sum();

        if (currentUser.getWallet().getBalance() >= totalCost) {
            try (Connection conn = DBConnectionPool.getConnection()) {
                conn.setAutoCommit(false);

                for (Games game : cartItems) {
                    libraryManager.addGameToLibrary(currentUser, game);
                }

                String updateWalletQuery = "UPDATE VaporGames.users SET wallet = wallet - ? WHERE userID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateWalletQuery)) {
                    stmt.setDouble(1, totalCost);
                    stmt.setInt(2, currentUser.getUserID());
                    stmt.executeUpdate();
                }

                conn.commit();
                currentUser.getWallet().updateBalance(currentUser.getWallet().getBalance() - totalCost);
                cartManager.clearCart(currentUser);
                loadUserInfo();
                loadPurchaseSummary();
                System.out.println("Order placed successfully!");

                // Record the transaction
                Transaction transaction = new Transaction();
                transaction.loadTransaction(currentUser);
                transaction.confirmTransaction(true);
                transaction.recordTransaction();

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to place order.");
            }
        } else {
            System.out.println("Insufficient balance to place the order.");
        }
    }
}