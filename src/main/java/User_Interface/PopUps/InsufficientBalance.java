package User_Interface.PopUps;

import Accounts.User;
import Accounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class InsufficientBalance {

    @FXML
    private Button Okay;

    @FXML
    private Button TopUP;

    public User currentUser = UserSession.getInstance().getCurrentUser();

    private boolean ifTopUpWasClicked = false;

    private Runnable opPopupClosed;

    public boolean isTopUpWasClicked(){
        return ifTopUpWasClicked;
    }

    public void setOnPopupClosed(Runnable callback) {
        this.opPopupClosed = callback;
    }

    @FXML
    void handleMouseClicked(MouseEvent event) {
        Object source = event.getSource();

        if(source == TopUP){
            if(!ifTopUpWasClicked){
                ifTopUpWasClicked = true;
                System.out.println("Okay clicked. Current state: " + ifTopUpWasClicked);
                closePopup();
            }
        } else if(source == Okay){
            closePopup();
        }
    }


    private void closePopup() {
        System.out.println("Closing popup window.");
        Stage stage = (Stage) TopUP.getScene().getWindow();
        stage.close();

        // Trigger the callback when the popup is closed
        if (opPopupClosed != null) {
            opPopupClosed.run();
        }
    }
}