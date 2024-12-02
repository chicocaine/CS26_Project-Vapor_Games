package User_Interface;

import Accounts.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


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
        if(event.getSource() == ChangePassword_Button){
            if (areFieldsNotEmpty()) {
                Username = Username_TextField.getText();
                Email = Email_TextField.getText();
                OldPassword = OldPassword_TextField.getText();
                NewPassword = NewPassword_TextField.getText();
            } else {
                showAlert("Empty Input Box", "Missing or Empty Input Box", Alert.AlertType.ERROR);
            }
        } else if (event.getSource() == ChangeProfilePicture_Image){
            //Add Functioons Here
        }
    }
    public void setUserOnProfile(User user){
        if (user.getUserName() != null && user.getName() != null ){
            Username_TextField.setText(user.getUserName());
            Email_TextField.setText(user.getName());
            OldPassword_TextField.setText(OldPassword);
            NewPassword_TextField.setText(NewPassword);
        }else {
            Username_TextField.setText("Username");
            Email_TextField.setText("Email");
            OldPassword_TextField.setText("Old Password");
            NewPassword_TextField.setText("New Password");
        }
    }
}
