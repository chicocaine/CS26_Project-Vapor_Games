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
import java.net.URISyntaxException;

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
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Handle user registration (Sign Up)
    private void handleSignUp() throws IOException, URISyntaxException {
        // Get user input from the form
        String userName = username.getText().trim();
        String displayNameText = displayName.getText().trim();
        String passwordText = password.getText().trim();

        // Validate user input
        if (userName.isEmpty() || displayNameText.isEmpty() || passwordText.isEmpty()) {
            // Show an error message if fields are empty
            System.out.println("Error: All fields must be filled.");
            return;
        }

        // Check if the username already exists (optional)
        if (userManager.checkUsernameExists(userName)) { // Assuming userManager has a method `isUsernameTaken`
            System.out.println("Error: Username already exists. Please choose a different one.");
            return;
        }

        // Set default profile photo
        // Use a relative path for portability
        File defaultPhoto = new File("C:\\codes\\CS26\\CS26_Project-Vapor_Games\\src\\main\\resources\\Image\\ProfileTestPicture.png");

        // Register the user
        try {
            userManager.registerUser(userName, passwordText, displayNameText, 0.0, defaultPhoto);
            System.out.println("User registered successfully.");

            // Show success popup
            successPopup();
        } catch (Exception e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
        }
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

    private void successPopup() throws IOException {
        Stage stage = (Stage) signIn.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateAccountSuccessPopUp.fxml"));
        Scene scene = new Scene(loader.load(), 428, 578);
        stage.setTitle("Vapor Games");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
