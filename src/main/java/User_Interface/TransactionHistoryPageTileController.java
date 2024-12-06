package User_Interface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class TransactionHistoryPageTileController {

    @FXML
    private Label Date_Label;

    @FXML
    private Label OrderID_Label;

    @FXML
    private Label TotalPrice_Label;

    public void setTransactionDetails(String date, int orderId, double totalPrice) {
        Date_Label.setText(date);
        OrderID_Label.setText(String.valueOf(orderId));
        TotalPrice_Label.setText(String.format("$%.2f", totalPrice));
    }
    @FXML
    private void HandlesButtonClicked(MouseEvent event) {
        // Handle the button click event
        System.out.println("Button clicked!");
    }
}