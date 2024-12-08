// src/main/java/User_Interface/GamePageController.java
package User_Interface;

import Accounts.User;
import Transaction.CartManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import Games.Games;

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
    private Text genreLabel0;

    @FXML
    private Text genreLabel1;

    @FXML
    private Text genreLabel2;

    @FXML
    private Text genreLabel3;

    @FXML
    private Pane StorePage_Pane;

    private User currentUser;
    private Games currentGame;
    private MainScreenController mainScreenController;

    @FXML
    private void initialize() {
        GameAddToCartButton_Pane.setOnMouseClicked(e -> addToCart());
        GameBuyNowButton_Pane.setOnMouseClicked(e -> buyNow());
    }

    public void displayGameDetails(Games game) {
        this.currentGame = game;
        StorePageGameTitle_Label.setText(game.getGameTitle());
        StoreGameDescription_Label.setText(game.getGameDescription());
        double price = Double.parseDouble(String.valueOf(game.getConvertedGamePrice()));
        GamePrice_Label.setText(String.format("%.2f", price));
        StoreGameThumbnail_Image.setImage(new javafx.scene.image.Image(game.getCardImageURL()));
        StorePageGameMainPicture_Image.setImage(new javafx.scene.image.Image(game.getShowcaseImagesURL().get(0)));
        genreLabel0.setText(game.getGenreList().get(0));
        genreLabel1.setText(game.getGenreList().get(1));
        genreLabel2.setText(game.getGenreList().get(2));
        genreLabel3.setText(game.getGenreList().get(3));
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void addToCart() {
        if (currentUser != null && currentGame != null) {
            CartManager cartmngr = new CartManager();
            cartmngr.addToCart(currentUser, currentGame);
            System.out.println("Game successfully added to the cart.");
        } else if (currentGame == null) {
            System.err.println("[ERROR] Current game is null!");
        } else {
            System.err.println("[ERROR] Current user is null!");
        }
    }

    public void buyNow() {
        addToCart();
        if (mainScreenController != null) {
            mainScreenController.loadCartPage();
        } else {
            System.err.println("[ERROR] MainScreenController is not set!");
        }
    }
}