package User_Interface.PopUps;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpUnsuccessful {

    @FXML
    private Button tryAgain;

    @FXML
    private void initialize() {
        tryAgain.setOnKeyPressed(Key -> {
            if (Key.getCode() == javafx.scene.input.KeyCode.ENTER) {
                try {
                    goback();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tryAgain.setOnAction(event -> {
            try {
                goback();
                System.out.println("Try Again button clicked");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }


        private void goback() throws IOException {
            Stage stage = (Stage) tryAgain.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Scene scene = new Scene(loader.load(), 428, 578);

            stage.setTitle("Vapor Games");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        }
    }


