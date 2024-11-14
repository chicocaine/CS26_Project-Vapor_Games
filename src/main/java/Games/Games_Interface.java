package Games;

import java.util.List;

abstract class Games_Interface {

    protected int gameID;
    protected String gameTitle;
    protected String gameDescription;
    protected double gamePrice;
    protected boolean isAvailable;
    protected List<String> genre;

    public void Games(int gameID, String gameTitle, String gameDescription, double gamePrice, List<String> genre, boolean isAvailable) {};
    public abstract void setGameID (int gameID);
    public abstract void setGameTitle (String gameTitle);
    public abstract void setGameDescription (String gameDescription);
    public abstract void setGamePrice (double gamePrice);
    public abstract void setGameGenres (List<String> genre);
    public abstract void setAvailability (boolean available);
    public abstract int getGameID ();
    public abstract String getGameTitle ();
    public abstract String getGameDescription ();
    public abstract double getGamePrice ();
    public abstract List<String> getGenreList ();
    public abstract boolean isAvailable ();
    

}
