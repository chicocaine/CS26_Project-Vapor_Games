package User_Interface;

import Accounts.User;
import Utility.PasswordManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class AccountPageController {

    @FXML
    private Button ChangePassword_Button;

    @FXML
    private ImageView ChangeProfilePicture_Image;

    @FXML
    private TextField Email_TextField;

    @FXML
    private PasswordField NewPassword_TextField;

    @FXML
    private PasswordField OldPassword_TextField;

    @FXML
    private ImageView ProfilePicture_Image;

    @FXML
    private TextField Username_TextField;
    MainScreenController mainScreenController = new MainScreenController();

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        initializeUserData();
    }

    private void initializeUserData() {
        if (currentUser != null) {
            Username_TextField.setText(currentUser.getUserName());
            Email_TextField.setText(currentUser.getName());
        }
    }

    @FXML
    private boolean areFieldsNotEmpty() {
        // Check if all the fields have text or a password
        return !Email_TextField.getText().isEmpty() &&
                !Username_TextField.getText().isEmpty() &&
                !OldPassword_TextField.getText().isEmpty() &&
                !NewPassword_TextField.getText().isEmpty();
    }

    String Username, Email, OldPassword, NewPassword;

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void HandlesButtonClicked(MouseEvent event) {
        if (event.getSource() == ChangePassword_Button) {
            if (areFieldsNotEmpty()) {
                Username = Username_TextField.getText();
                Email = Email_TextField.getText();
                OldPassword = OldPassword_TextField.getText();
                NewPassword = NewPassword_TextField.getText();



                if (currentUser != null) {
                    verifyPassChange(currentUser);
                } else {
                    showAlert("Error", "Unable to retrieve user. Please try again.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Empty Input Box", "Missing or Empty Input Box", Alert.AlertType.ERROR);
            }
        } else if (event.getSource() == ChangeProfilePicture_Image) {
            // Add functionality for profile picture change
        }
    }


    public void setUserOnProfile(User user){

        if (user.getUserName() != null && user.getName() != null ){
            Username_TextField.setText(user.getUserName());
            Email_TextField.setText(user.getName());
            OldPassword_TextField.setText("");

        }else {
            Username_TextField.setText("Username");
            Email_TextField.setText("Email");
            OldPassword_TextField.setText("Old Password");
            NewPassword_TextField.setText("New Password");
        }
    }
    private void clearPasswordFields() {
        OldPassword_TextField.clear();
        NewPassword_TextField.clear();
    }

    public void verifyPassChange(User user) {
        PasswordManager manager = new PasswordManager();
        String storedPassword = manager.getStoredPassword(user.getUserName());

        if (manager.verifyPassword(OldPassword, storedPassword)) {
            String newHashedPassword = manager.updatePassword(OldPassword, storedPassword, NewPassword);

            if (newHashedPassword != null) {
                // Update password in the database
                boolean isUpdated = manager.updatePasswordInDatabase(user.getUserName(), newHashedPassword);

                if (isUpdated) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PasswordChangeSuccessPopUp.fxml"));
                        Parent parent = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(parent));
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    clearPasswordFields(); // Clear password fields after successful update
                } else {
                    showAlert("Password Change Failed", "Failed to update password in the database.", Alert.AlertType.ERROR);
                }
            } else {
                passFail();
            }
        } else {
            passFail();
        }
        clearPasswordFields(); // Clear password fields after verification attempt
    }
public void passFail(){
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PasswordChangeFailPopUp.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
