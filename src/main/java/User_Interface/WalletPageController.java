package User_Interface;

import Accounts.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    public void initialize() {
        System.out.println("WalletPageController initialized");
    }

    @FXML
void HandlesButtonClicked(MouseEvent event) {
    // Initialize components
    WalletCurrentAGSCoin_Label.setText("0");
    WalletInputCode_TextField.clear();
}

    public void setUserOnWallet(User user) {
        if (user != null && user.getWallet() != null) {
            WalletCurrentAGSCoin_Label.setText(String.valueOf(user.getWallet().getBalance()));
            System.out.println(WalletCurrentAGSCoin_Label.getText());
        } else {
            WalletCurrentAGSCoin_Label.setText("test");
        }
        WalletInputCode_TextField.clear();
    }


}
