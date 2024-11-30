package User_Interface;

import Accounts.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField displayName;

    @FXML
    private PasswordField password;

    @FXML
    private Button signIn;

    @FXML
    private Button signUp;

    @FXML
    private TextField username;

    private UserManager userManager;

    public SignUpController() {
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

        signUp.setOnAction(event -> {
            try {
                handleSignUp();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Handle user registration (Sign Up)
    private void handleSignUp() throws IOException {
        // Get user input from the form
        String userName = username.getText().trim();
        String displayNameText = displayName.getText().trim();
        String passwordText = password.getText().trim();

        // Validate user input
        if (userName.isEmpty() || displayNameText.isEmpty() || passwordText.isEmpty()) {
            // Show an error message if fields are empty
            System.out.println("All fields must be filled.");
            return;
        }

        // Optionally, you can add further validation here (like checking if the username already exists)

        // Call the userManager to register the new user
        // You can pass a default wallet value (e.g., 0.0) and photo (File can be null for now)
        File defaultPhoto = new File("C:\\codes\\CS26\\CS26_Project-Vapor_Games\\src\\main\\resources\\Image\\ProfileTestPicture.png"); // You can add logic to handle profile photo upload
        userManager.registerUser(userName, passwordText, displayNameText, 0.0, defaultPhoto);

        // After successful registration, go to the Sign In page
        Stage stage = (Stage) signUp.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 428, 578);
        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
    }

    // Handle the Sign In page redirection
    private void handleSignIn() throws IOException {
        Stage stage = (Stage) signUp.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 428, 578);
        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
    }
}
