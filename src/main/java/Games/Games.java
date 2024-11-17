package Games;

import java.util.ArrayList;

public class Games {

    private int gameID;
    private String gameTitle;
    private String gameReleaseDate;
    private String gameDescription;
    private double gamePrice;
    private boolean isAvailable;
    private ArrayList<String> genre;
    private String gamePictureURL;

    public Games () {
        // empty constructor
    }

    public Games (int gameID, String gameTitle, String gameReleaseDate, String gameDescription, double gamePrice, ArrayList<String> genre, boolean isAvailable, String gamePictureURL) {
        this.gameID = gameID;
        this.gameTitle = gameTitle;
        this.gameReleaseDate = gameReleaseDate;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.genre = genre;
        this.isAvailable = isAvailable;
        this.gamePictureURL = gamePictureURL;
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

    public void setGamePictureURL (String gamePictureURL) {
        this.gamePictureURL = gamePictureURL;
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
    
    public String getGamePictureURL () {
        return gamePictureURL;
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
                ", gamePictureURL='" + gamePictureURL + '\'' +
                '}';
    }

}