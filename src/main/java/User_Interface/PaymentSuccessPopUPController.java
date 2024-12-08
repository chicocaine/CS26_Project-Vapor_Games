package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PaymentSuccessPopUPController {

    @FXML
    private Button BackToStore_Button;

    @FXML
    private Button DownloadReciept_Button;

    public User currentUser = UserSession.getInstance().getCurrentUser();

    private boolean ifBackToStoreClicked = false;

    // Declare a callback to notify when the popup closes
    private Runnable onPopupClosed;

    public boolean isBackToStoreClicked() {
        return ifBackToStoreClicked;
    }

    // Set the callback for when the popup closes
    public void setOnPopupClosed(Runnable callback) {
        this.onPopupClosed = callback;
    }

    @FXML
    void handleMouseClicked(MouseEvent event) {
        Object source = event.getSource();

        if (source == BackToStore_Button) {
            if (!ifBackToStoreClicked) {
                ifBackToStoreClicked = true;
                System.out.println("BackToStore_Button clicked. Current state: " + ifBackToStoreClicked);
                closePopup();
            }
        } else if (source == DownloadReciept_Button) {
            // ADD FUNCTIONS FOR RECEIPT if needed
        }
    }

    private void closePopup() {
        System.out.println("Closing popup window.");
        Stage stage = (Stage) BackToStore_Button.getScene().getWindow();
        stage.close();

        // Trigger the callback when the popup is closed
        if (onPopupClosed != null) {
            onPopupClosed.run();
        }
    }
}
