package User_Interface.PopUps;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginUnsuccessful {
    @FXML
    private Button goBack;

    @FXML
    private void initialize() {
        goBack.setOnAction(event -> {
            try {
                setGoBack();
                System.out.println("goBack Button clicked");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
        Scene scene = new Scene(loader.load(), 428, 578);

        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
