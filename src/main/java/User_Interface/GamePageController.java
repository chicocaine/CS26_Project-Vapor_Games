package User_Interface;

import Accounts.User;
import Transaction.CartManager;
import User_Interface.PopUps.AddedToCart;
import User_Interface.PopUps.GameAlreadyOwned;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import Games.Games;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GamePageController {

    @FXML
    private Pane GameAddToCartButton_Pane;

    @FXML
    private Pane GameBuyNowButton_Pane;

    @FXML
    private Label GamePrice_Label;

    @FXML
    private Label StoreGameDescription_Label;

    @FXML
    private Label StoreGameSubTitle_Label;

    @FXML
    private ImageView StoreGameThumbnail_Image;

    @FXML
    private ImageView StorePageGameMainPicture_Image;

    @FXML
    private Label StorePageGameTitle_Label;

    @FXML
    private Pane StorePage_Pane;
    @FXML
    private Text genreText;

    private User currentUser;
    private Games currentGame;
    private boolean ViewCartWasClicked = false;
    private boolean ViewLibraryWasClicked = false;
    @FXML
    private void initialize() {
        GameAddToCartButton_Pane.setOnMouseClicked(e -> addToCart());
        GameBuyNowButton_Pane.setOnMouseClicked(e -> buyNow());
    }
    @FXML
    void loadMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) StoreGameDescription_Label.getScene().getWindow();
            stage.setScene(new Scene(root));
            MainScreenController mainScreenController = loader.getController();
            mainScreenController.currentUser = currentUser;
            mainScreenController.setUserOnDashboard(currentUser);
            mainScreenController.setViewMyCartClicked(ViewCartWasClicked); // Pass the boolean
            mainScreenController.setViewLibraryClicked(ViewLibraryWasClicked);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load MainScreen.");
        }
    }

    public void displayGameDetails(Games game) {
        this.currentGame = game;
        if (game != null) {
            StorePageGameTitle_Label.setText(game.getGameTitle());
            StoreGameDescription_Label.setText(game.getGameDescription());

            double price = Double.parseDouble(String.valueOf(game.getConvertedGamePrice()));
            GamePrice_Label.setText(String.format("%.2f", price));

            // Handle Thumbnail Image
            if (game.getCardImageURL() != null && !game.getCardImageURL().isEmpty()) {
                StoreGameThumbnail_Image.setImage(new javafx.scene.image.Image(game.getCardImageURL()));
            } else {
                System.err.println("[WARNING] Thumbnail image URL is empty.");
            }

            // Handle Main Showcase Image
            if (game.getShowcaseImagesURL() != null && !game.getShowcaseImagesURL().isEmpty()) {
                StorePageGameMainPicture_Image.setImage(new javafx.scene.image.Image(game.getShowcaseImagesURL().get(0)));
            } else {
                System.err.println("[WARNING] Showcase images list is empty.");
            }

            // Handle Genre Labels
            if (game.getGenreList() != null) {
                List<String> genreList = game.getGenreList();
                Set<String> uniqueGenres = new HashSet<>(genreList);
                String genres = String.join(", ", uniqueGenres);
                genreText.setText(genres);
            } else {
                System.err.println("[WARNING] Genre list is null.");
            }
        } else {
            System.err.println("[ERROR] Game is null!");
        }
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void addToCart() {
        if (currentUser == null || currentGame == null) {
            System.err.println("[ERROR] Current user or game is null!");
            return;
        }
        CartManager cartmngr = new CartManager();
        if (cartmngr.isGameInCart(currentUser, currentGame)) {
            showGameAlreadyOnCart();
        } else if (cartmngr.isGameInLibrary(currentUser,currentGame)) {
            showGameAlreadyOwned();
        } else {
            cartmngr.addToCart(currentUser, currentGame);
            showAddedToCartPopup();
        }
    }

 public void buyNow() {
    CartManager cartManager = new CartManager();
    if (cartManager.isGameInLibrary(currentUser, currentGame)) {
        showGameAlreadyOwned();
    } else {
        if (!cartManager.isGameInCart(currentUser, currentGame)) {
            cartManager.addToCart(currentUser, currentGame);
        }
        loadCheckOutPage();
    }
}

    public void loadCheckOutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CheckOutPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) GameBuyNowButton_Pane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load checkout page.");
        }
    }
    private void showAddedToCartPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddedToCartPopUp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            AddedToCart controller = loader.getController();
            controller.setOnPopupClosed(() -> {
                if(controller.isViewMyCartWasClicked()){
                    ViewCartWasClicked = true;
                    loadMainScreen();
                } else {
                    System.out.println("Popup closed without clicking 'View Cart'.");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load AddedToCart popup.");
        }
    }

    private void showGameAlreadyOnCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameAlreadyOnCartPopUp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            AddedToCart controller = loader.getController();
            controller.setOnPopupClosed(() -> {
                if(controller.isViewMyCartWasClicked()){
                    ViewCartWasClicked = true;
                    loadMainScreen();
                } else {
                    System.out.println("Popup closed without clicking 'View Cart'.");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load AddedToCart popup.");
        }
    }

    public void showGameAlreadyOwned(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameAlreadyOwnedPopUp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            GameAlreadyOwned controller = loader.getController();
            controller.setOnPopupClosed(() -> {
                if(controller.ifViewLibraryWasClicked()){
                    ViewLibraryWasClicked = true;
                    loadMainScreen();
                } else {
                    System.out.println("Popup closed without clicking 'View Cart'.");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load AddedToCart popup.");
        }

    }
}