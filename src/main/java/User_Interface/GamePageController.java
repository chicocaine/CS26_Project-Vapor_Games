package User_Interface;

import Accounts.User;
import Transaction.CartManager;
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

import java.io.IOException;

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

    @FXML
    private void initialize() {
        GameAddToCartButton_Pane.setOnMouseClicked(e -> addToCart());
        GameBuyNowButton_Pane.setOnMouseClicked(e -> buyNow());
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
                if (game.getGenreList().size() > 0) {
                    genreLabel0.setText(game.getGenreList().get(0));
                } else {
                    genreLabel0.setText("");
                }
                if (game.getGenreList().size() > 1) {
                    genreLabel1.setText(game.getGenreList().get(1));
                } else {
                    genreLabel1.setText("");
                }
                if (game.getGenreList().size() > 2) {
                    genreLabel2.setText(game.getGenreList().get(2));
                } else {
                    genreLabel2.setText("");
                }
                if (game.getGenreList().size() > 3) {
                    genreLabel3.setText(game.getGenreList().get(3));
                } else {
                    genreLabel3.setText("");
                }
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
        cartmngr.addToCart(currentUser, currentGame);
    }

    public void buyNow() {
        addToCart();
        loadCheckOutPage();
    }

    private void loadCheckOutPage() {
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
}