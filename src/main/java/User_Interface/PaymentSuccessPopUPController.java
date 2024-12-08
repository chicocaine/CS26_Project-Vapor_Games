// src/main/java/User_Interface/PaymentSuccessPopUPController.java
package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentSuccessPopUPController {

    @FXML
    private Stage stage;

    @FXML
    private Button BackToStore_Button;

    @FXML
    private Button DownloadReciept_Button;

    public User currentUser = UserSession.getInstance().getCurrentUser();

    @FXML
    void HandlesMouseClicked(MouseEvent event) {
        Object source = event.getSource();

        if (source == BackToStore_Button) {
            try {
                // Close the popup
                Stage popupStage = (Stage) BackToStore_Button.getScene().getWindow();
                popupStage.close();

                // Close the checkout page
                Stage checkoutStage = (Stage) stage.getOwner();
                checkoutStage.close();

                // Load and show the main screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
                Parent root = loader.load();
                Stage mainStage = new Stage();
                mainStage.setScene(new Scene(root));
                MainScreenController mainScreenController = loader.getController();
                mainScreenController.setUser(currentUser);
                mainScreenController.setUserOnDashboard(currentUser);
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to load MainScreen.");
            }
        } else if (source == DownloadReciept_Button) {
            // ADD FUNCTIONS FOR RECEIPT
        }
    }

    public void setStage(Stage newStage) {
        this.stage = newStage;
    }
}