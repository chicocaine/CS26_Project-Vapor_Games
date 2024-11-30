package User_Interface.PopUps;

import Accounts.User;
import User_Interface.MainScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginSuccessful {

    @FXML
    private Button continueToStore;


    private void proceedToDashboard(User user) throws IOException {
        Stage stage = (Stage) continueToStore.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 760);
        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);

        // Load the icon
        URL resourceUrl = getClass().getResource("/Image/icon.jpg");
        if (resourceUrl != null) {
            Image iconImage = new Image(resourceUrl.toExternalForm());
            stage.getIcons().add(iconImage); // Directly add the Image
        } else {
            System.out.println("[ERROR]: Icon image not found at the specified path.");
            System.out.println("Resource URL: " + resourceUrl);
        }

        // Show the stage
        stage.show();

        // Center the stage on the screen
        stage.centerOnScreen();

        // Pass the stage to the controller
        MainScreenController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }
}


