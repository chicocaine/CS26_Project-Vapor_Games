package User_Interface.PopUps;

import User_Interface.MainScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class InsufficientBalance {

    @FXML
    private Button Okay;

    @FXML
    private Button TopUP;
    private Runnable onPopupClosed;
    private Stage checkoutStage;


    @FXML
    private void initialize() {
        TopUP.setOnAction(event -> {
            System.out.println("TopUP button clicked.");
            redirectToWallet();
        });
        Okay.setOnAction(event -> {
            System.out.println("Okay button clicked.");
            closePopup();
        });
    }

    @FXML
    void handleMouseClicked(MouseEvent event) {
        System.out.println("Okay button clicked.");
    }
    public void setOnPopupClosed(Runnable callback) {
        this.onPopupClosed = callback;
    }
    public void setCheckoutStage(Stage stage) {
        this.checkoutStage = stage;
    }

    // In the InsufficientBalance class
public void redirectToWallet() {
    try {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        Parent mainRoot = mainLoader.load();
        MainScreenController mainController = mainLoader.getController();

        FXMLLoader walletLoader = new FXMLLoader(getClass().getResource("/WalletPage.fxml"));
        Parent walletRoot = walletLoader.load();
        mainController.setMainContent_Pane((Pane) walletRoot);

        // Close the current stage
        Stage currentStage = (Stage) Okay.getScene().getWindow();
        currentStage.close();

        if (checkoutStage != null) {
            checkoutStage.close();
        }

        // Open a new stage for the wallet page
        Stage newStage = new Stage();
        newStage.setScene(new Scene(mainRoot));
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("[ERROR] Failed to load WalletPage within MainScreen.");
    }
}
    public void showInsufficientBalancePopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InsufficientBalancePopUp.fxml"));
            Parent root = loader.load();
            InsufficientBalance controller = loader.getController();

            // Create a new stage for the insufficient balance popup
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            controller.setOnPopupClosed(() -> {
                System.out.println("Insufficient balance popup closed.");
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load insufficient balance popup.");
        }
    }
    // In the InsufficientBalance class
public void closePopup() {
    Stage stage = (Stage) Okay.getScene().getWindow();
    stage.close();

    // Trigger the callback when the popup is closed
    if (onPopupClosed != null) {
        onPopupClosed.run();
    }
}
}
