package User_Interface.PopUps;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpSuccessful {

    @FXML
    private Button continueToLogin;

    @FXML
    private void initialize() {
        continueToLogin.setOnAction(event -> {
            try {
                proceedToLogin();
                System.out.println("continueToLogin button clicked");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void proceedToLogin() throws IOException {
        Stage stage = (Stage) continueToLogin.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
        Scene scene = new Scene(loader.load(), 428, 578);

        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
