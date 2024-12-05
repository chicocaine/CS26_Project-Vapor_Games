package User_Interface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PaymentSuccessPopUPController {

    @FXML
    private Stage stage;

    @FXML
    private Button BackToStore_Button;

    @FXML
    private Button DownloadReciept_Button;

    @FXML
    void HandlesMouseClicked(MouseEvent event) {
        Object source = event.getSource();

        if (source == BackToStore_Button){
            stage.close();
        } else if (source == DownloadReciept_Button){
            //ADD FUNCTIONS FOR RECIEPT
        }
    }

    public void setStage(Stage newStage) {
        this.stage = newStage;
    }
}
