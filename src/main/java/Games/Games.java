package Games;

import java.util.List;

public class Games extends Games_Interface {

    private int gameID;
    private String gameTitle;
    private String gameDescription;
    private double gamePrice;
    private boolean isAvailable;
    private List<String> genre;

    public Games () {
        // empty constructor
    }

    public Games (int gameID, String gameTitle, String gameDescription, double gamePrice, List<String> genre, boolean isAvailable) {
        this.gameID = gameID;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.genre = genre;
        this.isAvailable = isAvailable;
    }

    public void setGameID (int gameID) {
        this.gameID = gameID;
    }

    public void setGameTitle (String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void setGameDescription (String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public void setGamePrice (double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public void setGameGenres (List<String> genre) {
       this.genre = genre;
    }

    public void setAvailability (boolean available) {
        this.isAvailable = available; 
    }

    public int getGameID () {
        return gameID;
    }

    public String getGameTitle () {
        return gameTitle;
    }

    public String getameDescription () {
        return gameDescription;
    }

    public double getGamePrice () {
        return gamePrice;
    }

    public List<String> getGenreList () {
        return genre;
    } 

    public boolean isAvailable () {
        return isAvailable;
    }    

}