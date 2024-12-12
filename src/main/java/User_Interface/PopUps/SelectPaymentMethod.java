package User_Interface.PopUps;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SelectPaymentMethod {

    @FXML
    private Button Okay;
    private Runnable onPopupClosed;

    @FXML
    private void initialize(){
        Okay.setOnAction(event -> {
            System.out.println("Okay button clicked.");
            closePopup();
        });
    }
    @FXML
    void handleMouseClicked(MouseEvent event) {
        System.out.println("Closing popup window.");
    }
    private void closePopup() {

        Stage stage = (Stage) Okay.getScene().getWindow();
        stage.close();

        // Trigger the callback when the popup is closed
        if (onPopupClosed != null) {
            onPopupClosed.run();
        }
    }
}
