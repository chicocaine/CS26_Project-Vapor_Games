package User_Interface.PopUps;

import Accounts.User;
import Accounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameAlreadyOwned {

    @FXML
    private Button continueShopping;

    @FXML
    private Button viewLibrary;
    private Runnable onPopupClosed;
    public User currentUser = UserSession.getInstance().getCurrentUser();
    private Runnable onPopUpClosed;
    private boolean ifViewLibraryWasClicked = false;

    public boolean ifViewLibraryWasClicked(){
        return ifViewLibraryWasClicked;
    }
    public void setOnPopupClosed(Runnable callback) {
        this.onPopupClosed = callback;
    }


    @FXML
    void handleMouseClicked(MouseEvent event) {
        Object source = event.getSource();
        if(source == viewLibrary){
            if(!ifViewLibraryWasClicked){
                ifViewLibraryWasClicked = true;
                System.out.println("View Cart clicked. Current state: " + ifViewLibraryWasClicked);
                closePopUp();
            }
        }else if (source == continueShopping){
            closePopUp();
        }

    }
    private void closePopUp() {
        System.out.println("Closing popup window.");
        Stage stage = (Stage) viewLibrary.getScene().getWindow();
        stage.close();

        // Trigger the callback when the popup is closed
        if (onPopupClosed != null) {
            onPopupClosed.run();
        }

    }
}
