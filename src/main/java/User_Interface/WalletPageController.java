package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import GameCredit.Redeem;
import Games.Games;
import Utility.DBConnectionPool;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletPageController implements PageController {

    @FXML
    private Label WalletCurrentAGSCoin_Label;
    @FXML
    private Label test;

    @FXML
    private Button WalletInputCodeButton_Button;

    @FXML
    private TextField WalletInputCode_TextField;

    @FXML
    public Text codeAlreadyRedeemed;

    @FXML
    public Text codeInvalid;

    @FXML
    public Text codeSuccess;

    private boolean needsRefresh = false;

    private User currentUser = UserSession.getInstance().getCurrentUser();
    private Games currentGame;

    @FXML
    public void initialize() {
        WalletInputCodeButton_Button.setOnAction(event -> processCode());
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) {
        WalletInputCode_TextField.clear();
    }

    public void setUserOnWallet(User user) {
        if (user != null && user.getWallet() != null) {
            WalletCurrentAGSCoin_Label.setText(String.format("%.2f", user.getWallet().getBalance()));
            System.out.println(WalletCurrentAGSCoin_Label.getText());
        } else {
            WalletCurrentAGSCoin_Label.setText("test");
        }
        WalletInputCode_TextField.clear();
    }

    private void processCode() {
        User currentUser = UserSession.getInstance().getCurrentUser();
        System.out.println("Current user in WalletPageController: " + currentUser);
        if (currentUser != null) {
            Redeem redeem = new Redeem(this);
            boolean success = redeem.redeemCode(WalletInputCode_TextField.getText(), currentUser);
            if (success) {
                codeSuccess.setText("Code Redeemed Successfully (Page will Reload in 5 Seconds)");
                codeSuccess.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                pause.setOnFinished(event -> refreshWallet());
                pause.play();
            } else {
                showNotification("Code is Invalid or Already Redeemed", "error");
            }
        }
    }

    @Override
    public void refreshWallet() {
        User currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser != null) {
            User updatedUser = fetchUpdatedUser(currentUser.getUserID());
            if (updatedUser != null) {
                UserSession.getInstance().setCurrentUser(updatedUser);
                needsRefresh = true;
                Platform.runLater(() -> {
                    setUserOnWallet(updatedUser);
                    if (needsRefresh) {
                        needsRefresh = false;
                        reloadWalletPage();
                    }
                });
            }
        }
    }

    private void reloadWalletPage() {
        loadMainScreen();
    }

    @FXML
    void loadMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) WalletCurrentAGSCoin_Label.getScene().getWindow();
            stage.setScene(new Scene(root));
            MainScreenController mainScreenController = loader.getController();
            mainScreenController.currentUser = currentUser;
            mainScreenController.setUserOnDashboard(currentUser);
            mainScreenController.setWalletPageRefresh(needsRefresh);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load MainScreen.");
        }
    }

    private User fetchUpdatedUser(int userId) {
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM VaporGames.users WHERE userID = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = UserSession.getInstance().getCurrentUser();
                user.setUserID(rs.getInt("userID"));
                user.getWallet().updateBalance(rs.getDouble("wallet"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void showNotification(String message, String type) {
        Platform.runLater(() -> {
            codeSuccess.setVisible(false);
            codeAlreadyRedeemed.setVisible(false);
            codeInvalid.setVisible(false);

            switch (type) {
                case "success":
                    codeSuccess.setText(message);
                    codeSuccess.setVisible(true);
                    break;
                case "error":
                    codeInvalid.setText(message);
                    codeInvalid.setVisible(true);
                    break;
                case "alreadyRedeemed":
                    codeAlreadyRedeemed.setText(message);
                    codeAlreadyRedeemed.setVisible(true);
                    break;
            }
        });
    }
}