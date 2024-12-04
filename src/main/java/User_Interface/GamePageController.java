package User_Interface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import Games.Games;
import javafx.scene.text.Text;

public class GamePageController {

    @FXML
    private Pane GameAddToCartButton_Pane;

    @FXML
    private Pane GameBuyNowButton_Pane;

    @FXML
    private Pane GameGenreTags_Pane;

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

    public void displayGameDetails(Games game) {
        StorePageGameTitle_Label.setText(game.getGameTitle());
        //StoreGameSubTitle_Label.setText(game.());
        StoreGameDescription_Label.setText(game.getGameDescription());
        GamePrice_Label.setText(""+game.getGamePrice());
        StoreGameThumbnail_Image.setImage(new javafx.scene.image.Image(game.getCardImageURL()));
        StorePageGameMainPicture_Image.setImage(new javafx.scene.image.Image(game.getShowcaseImagesURL().get(0)));
        genreLabel0.setText(game.getGenreList().get(0));
        genreLabel1.setText(game.getGenreList().get(1));
        genreLabel2.setText(game.getGenreList().get(2));
        genreLabel3.setText(game.getGenreList().get(3));
    }
}
