package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import GameCredit.Redeem;
import Utility.DBConnectionPool;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletPageController {

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
            WalletCurrentAGSCoin_Label.setText(String.format("%.2f",user.getWallet().getBalance()));
            System.out.println(WalletCurrentAGSCoin_Label.getText());
        } else {
            WalletCurrentAGSCoin_Label.setText("test");
        }
        WalletInputCode_TextField.clear();
    }

    public void processCode() {
        User currentUser = UserSession.getInstance().getCurrentUser();
        System.out.println("Current user in WalletPageController: " + currentUser);
        if (currentUser != null) {
            Redeem redeem = new Redeem(this);
            redeem.redeemCode(WalletInputCode_TextField.getText(), currentUser);
            refreshWallet();
        }
    }

    private void refreshWallet() {
        User currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser != null) {
            User updatedUser = fetchUpdatedUser(currentUser.getUserID());
            if (updatedUser != null) {
                UserSession.getInstance().setCurrentUser(updatedUser);
                Platform.runLater(() -> setUserOnWallet(updatedUser));
            }
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