package User_Interface;

import Accounts.User;
import Accounts.UserManager;
import User_Interface.PopUps.LoginSuccessful;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class SignInController {

    @FXML
    private Button Register;

    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Button signIn;

    private UserManager userManager;

    public SignInController() {
        this.userManager = new UserManager();
    }

    @FXML
    private void initialize() {
        // Attach event listeners to buttons
        signIn.setOnAction(event -> {
            try {
                handleSignIn();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Register.setOnAction(event -> {
            try {
                handleRegister();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void handleSignIn() throws IOException {
        String email = inputEmail.getText().trim();
        String password = inputPassword.getText().trim();

        boolean test = userManager.userAuth(email, password);
        System.out.println(test);

        if (email.isEmpty() || password.isEmpty()) {
            showCustomPopup();
            return;
        }

        User user = userManager.loadUserSession(email, password);
        System.out.println(user);
        if (user != null) {
            proceedToDashboard(user);
        } else {
            showCustomPopup();
        }
    }

    private void handleRegister() throws IOException {
        Stage stage = (Stage) signIn.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 428, 578);
        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
    }

    private void proceedToDashboard(User user) throws IOException {
        Stage stage = (Stage) signIn.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginSuccessfulPopUP.fxml"));
        Scene scene = new Scene(loader.load(), 428, 578);
        LoginSuccessful loginController = loader.getController();

        // Pass the User object to the LoginSuccessful controller
        loginController.setUser(user);

        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showCustomPopup() throws IOException {
        Stage stage = (Stage) signIn.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignInErrorPopUp.fxml"));
        Scene scene = new Scene(loader.load(), 428, 578);
        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    private void showCustomPopupWithUser(User user) throws IOException {
        Stage stage = (Stage) signIn.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginSuccessfulPopUP.fxml"));
        Stage popupStage = new Stage();
        popupStage.setScene(new Scene(loader.load(), 428, 578));
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setResizable(false);

        // Pass the user data to the controller
        LoginSuccessful controller = loader.getController();
        controller.setUser(user);

        popupStage.show();
    }

}

