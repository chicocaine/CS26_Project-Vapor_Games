package User_Interface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckOutPageController {

    private Stage stage;

    @FXML
    private RadioButton AGSCoin_RadioButton;

    @FXML
    private Label AccountName_Label;

    @FXML
    private ImageView CloseOrReturn_Image;

    @FXML
    private Label CurrentBalance_Label;

    @FXML
    private Button PlaceOrder_Button;

    @FXML
    private VBox PurchaseSummary_VBox;

    @FXML
    private Label TotalCost_Label;

    @FXML
    void HandlesMouseClicked(MouseEvent event) {
        Object source = event.getSource();

        if(source == CloseOrReturn_Image){
            stage.close();
        }
    }
public void setStage(Stage stage) {
        this.stage = stage;
    }
}
