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
import javafx.stage.StageStyle;

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

    private boolean isAGSCoinSelected;

    @FXML
    private void initialize() {
        loadUserInfo();
        loadPurchaseSummary();
        this.isAGSCoinSelected = false;
        PlaceOrder_Button.setOnAction(event -> placeOrder());
        AGSCoin_RadioButton.setOnAction(event -> handleAGSCoinSelection());
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
        CurrentBalance_Label.setText(String.format("%.2f",currentUser.getWallet().getBalance()));
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
                tileController.CheckOutGamePrice_Label.setText(String.format("%.2f", game.getConvertedGamePrice()));
                String imagepath = game.getCardImageURL();
                tileController.GameCheckOutTile_Image.setImage(new Image(imagepath));

                PurchaseSummary_VBox.getChildren().add(gameTilePane);
                totalCost += game.getConvertedGamePrice();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load checkout tile.");
            }
        }

        TotalCost_Label.setText(String.format("%.2f", totalCost));
    }

    private void placeOrder() {

        // checks if a payment method is selected
        if (!isAGSCoinSelected) {
            System.out.println("No payment method selected.");
            return;
        }

        List<Games> cartItems = cartManager.getCart(currentUser);
        double totalCost = cartItems.stream().mapToDouble(Games::getConvertedGamePrice).sum();

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
                currentUser.getWallet().setBalance(currentUser.getWallet().getBalance() - totalCost);
                loadUserInfo();
                loadPurchaseSummary();
                showPaymentSuccessPopup();
                System.out.println("Order placed successfully!");

                // Record the transaction
                Transaction transaction = new Transaction(currentUser);
                transaction.confirmTransaction(true);
                transaction.recordTransaction();

                // Clear the cart for the user after transaction
                cartManager.clearCart(currentUser);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to place order.");
            }
        } else {
            System.out.println("Insufficient balance to place the order.");
        }
    }
    private void showPaymentSuccessPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentSuccessPopUP.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load payment success popup.");
        }
    }

    private void handleAGSCoinSelection() {
        this.isAGSCoinSelected = AGSCoin_RadioButton.isSelected();
    }

}