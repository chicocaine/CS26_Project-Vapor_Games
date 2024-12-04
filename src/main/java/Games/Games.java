package Games;

import java.util.ArrayList;
import java.util.Arrays;

public class Games {

    private int gameID;
    private String gameTitle;
    private String gameReleaseDate;
    private String gameDescription;
    private double gamePrice;
    private boolean isAvailable;
    private ArrayList<String> genre;
    private String cardImageURL;
    private ArrayList<String> showcaseImagesURL;

    public Games () {
        // empty constructor
    }

    public Games (int gameID, String gameTitle, String gameReleaseDate, String gameDescription, double gamePrice, ArrayList<String> genre, boolean isAvailable, String cardImageURL, ArrayList<String> showcaseImagesURL) {
        this.gameID = gameID;
        this.gameTitle = gameTitle;
        this.gameReleaseDate = gameReleaseDate;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.genre = genre;
        this.isAvailable = isAvailable;
        this.cardImageURL = cardImageURL;
        this.showcaseImagesURL = showcaseImagesURL;
    }

    public void setGameID (int gameID) {
        this.gameID = gameID;
    }

    public void setGameTitle (String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void setGameReleaseDate (String gameReleaseDate) {
        this.gameReleaseDate = gameReleaseDate;
    }

    public void setGameDescription (String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public void setGamePrice (double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public void setGameGenres (ArrayList<String> genre) {
       this.genre = genre;
    }

    public void setAvailability (boolean available) {
        this.isAvailable = available; 
    }

    public void setCardImageURL (String cardImageURL) {
        this.cardImageURL = cardImageURL;
    }
    
    public void setShowcaseImagesURL (ArrayList<String> showcaseImagesURL) {
        this.showcaseImagesURL = showcaseImagesURL;
    }

    public int getGameID () {
        return gameID;
    }

    public String getGameTitle () {
        return gameTitle;
    }

    public String getGameReleaseDate () {
        return gameReleaseDate;
    }

    public String getGameDescription () {
        return gameDescription;
    }

    public double getGamePrice () {
        return gamePrice;
    }

    public ArrayList<String> getGenreList () {
        return genre;
    } 

    public boolean isAvailable () {
        return isAvailable;
    }
    
    public String getCardImageURL () {
        return cardImageURL;
    }

    public ArrayList<String> getShowcaseImagesURL () {
        return showcaseImagesURL;
    }

    @Override
    public String toString() {
        return "Games{" +
                "gameID=" + gameID +
                ", gameTitle='" + gameTitle + '\'' +
                ", gameReleaseDate='" + gameReleaseDate + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gamePrice=" + gamePrice +
                ", genres=" + genre +
                ", isAvailable=" + isAvailable +
                ", cardImageURL='" + cardImageURL +
                ", showcaseImagesURL='" + showcaseImagesURL + '\'' +
                '}';
    }

    public void InitGame(){
        Games game = new Games(
                -1,
                "Terraria",
                "May 16, 2011",
                "Terraria is a 2011 action-adventure sandbox game developed by Re-Logic. The game was first released for Windows and has since been ported to other PC and console platforms. The game features exploration, crafting, building, painting, and combat with a variety of creatures in a procedurally generated 2D world",
                48.55,
                new ArrayList<>(Arrays.asList("Action", "Survival", "Sandbox")),
                true,
                "Image/uploads/game_images/Terraria_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_imagesTerraria_Showcase.webp",""))
        );

    }
}

