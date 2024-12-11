package User_Interface.PopUps;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PasswordFail {

    @FXML
    private Button Okay;

    @FXML
    void handleMouseClicked(MouseEvent event) {
        Okay.setOnAction(event1 -> {
            closePopup();
        });
    }
    private void closePopup() {
        Stage stage = (Stage) Okay.getScene().getWindow();
        stage.close();
    }

}
