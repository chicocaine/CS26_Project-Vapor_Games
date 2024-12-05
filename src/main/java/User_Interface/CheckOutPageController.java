package User_Interface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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

    // Handle mouse clicks
    @FXML
    void HandlesMouseClicked(MouseEvent event) throws IOException {
        Object source = event.getSource();

        if (source == CloseOrReturn_Image) {
            if (stage != null) {
                stage.close();
            } else {
                System.err.println("Stage is not set!");
            }
        } else if (source == PlaceOrder_Button) {
            stage.close();
            PaymentSuccessPopUP();
        }
    }

    // Open payment success pop-up
    @FXML
    void PaymentSuccessPopUP() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PaymentSuccessPopUP.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 423, 578);

        // Create a new stage
        Stage newStage1 = new Stage();
        newStage1.setTitle("Vapor Games");
        newStage1.setScene(scene1);
        newStage1.setResizable(false);
        newStage1.initStyle(StageStyle.UNDECORATED);

        // Show the new stage
        newStage1.show();

        // Pass the new stage to the controller
        PaymentSuccessPopUPController controller1 = fxmlLoader.getController();
        controller1.setStage(newStage1);
    }

    //ADD FUNCTIONS WHERE IT POPULATE THE VBOX USING THE CHCEKOUTPAGETILE.FXML

    // Set the current stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
