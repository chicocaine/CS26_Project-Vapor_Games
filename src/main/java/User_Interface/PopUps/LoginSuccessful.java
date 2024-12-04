package User_Interface.PopUps;

import Accounts.User;
import User_Interface.MainScreenController;
import User_Interface.WalletPageController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginSuccessful {
    private User currentUser; // Add a field to store the User object

    @FXML
    private Button continueToStore;

    @FXML
    private void initialize() {
        continueToStore.setOnKeyPressed(Key -> {
    if (Key.getCode() == javafx.scene.input.KeyCode.ENTER) {
        try {
            proceedToDashboard();
            System.out.println("continueToStore button clicked");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
});
        continueToStore.setOnAction(event -> {
            try {
                proceedToDashboard();
                System.out.println("continueToStore button clicked");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setUser(User user) {
        this.currentUser = user;
        System.out.println("User set in LoginSuccessful: " + currentUser);
    }

    private void proceedToDashboard() throws IOException {
        Stage stage = (Stage) continueToStore.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 760);
        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);

        MainScreenController mainController = fxmlLoader.getController();
        mainController.setUserOnDashboard(currentUser);
        mainController.setUser(currentUser);

        stage.show();
    }

}
