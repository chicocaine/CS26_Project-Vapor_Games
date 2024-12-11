package User_Interface.PopUps;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LogOutPopUp {

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    void handleMouseClicked(MouseEvent event) {
        if (event.getSource() == cancel) {
            closePopup();
        } else if (event.getSource() == confirm) {
            exitSystem();
        }
    }

    private void closePopup() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void exitSystem() {
        System.exit(0);
    }
}