package User_Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainLogin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the SignIn screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 428, 578); // Adjust width and height as needed

        // Set the stage properties
        primaryStage.setTitle("Sign In");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
