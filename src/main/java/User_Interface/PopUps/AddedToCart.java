package User_Interface.PopUps;

import Accounts.User;
import Accounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddedToCart {

    @FXML
    private Button ContinueSopping;

    @FXML
    private Button ViewCart;
    private Runnable onPopupClosed;
    public User currentUser = UserSession.getInstance().getCurrentUser();
    private Runnable onPopUpClosed;
    private boolean ifViewMyCartWasClicked = false;

    public boolean isViewMyCartWasClicked(){
        return ifViewMyCartWasClicked;
    }
    public void setOnPopupClosed(Runnable callback) {
        this.onPopupClosed = callback;
    }

    @FXML
    void handleMouseClicked(MouseEvent event) {
        Object source = event.getSource();
        if(source == ViewCart){
            if(!ifViewMyCartWasClicked){
                ifViewMyCartWasClicked = true;
                System.out.println("View Cart clicked. Current state: " + ifViewMyCartWasClicked);
                closePopup();
            }
        } else if(source == ContinueSopping){
            closePopup();
        }
    }
    private void closePopup() {
        System.out.println("Closing popup window.");
        Stage stage = (Stage) ContinueSopping.getScene().getWindow();
        stage.close();

        // Trigger the callback when the popup is closed
        if (onPopupClosed != null) {
            onPopupClosed.run();
        }

    }



}
