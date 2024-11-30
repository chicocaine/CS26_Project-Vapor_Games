package User_Interface;

import Accounts.User;
import Accounts.UserManager;
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

        boolean test = userManager.userAuth(email,password);
        System.out.println(test);

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Email or password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        User user = userManager.loadUserSession(email, password);
        System.out.println(user);
        if (user != null) {
            showAlert("Success", "Welcome, " + user.getName() + "!", Alert.AlertType.INFORMATION);
            // Proceed to the next stage of the application
            proceedToDashboard(user);
        } else {
            showAlert("Authentication Failed", "Invalid email or password.", Alert.AlertType.ERROR);
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




    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
