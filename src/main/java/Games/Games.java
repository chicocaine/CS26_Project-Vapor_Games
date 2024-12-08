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


    public double getConvertedGamePrice() {
        return gamePrice / 6.9;
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

//    public Games initTerraria() {
//        Games game = new Games(
//                -1,
//                "Terraria",
//                "May 16, 2011",
//                "Terraria is a 2011 action-adventure sandbox game developed by Re-Logic. The game was first released for Windows and has since been ported to other PC and console platforms. The game features exploration, crafting, building, painting, and combat with a variety of creatures in a procedurally generated 2D world",
//                335.00,
//                new ArrayList<>(Arrays.asList("Sandbox", "Survival", "2D")),
//                true,
//                "Image/uploads/game_images/Terraria_Card.jpg",
//                new ArrayList<>(Arrays.asList("Image/uploads/game_imagesTerraria_Showcase.webp"))
//        );
//        return game;
//    }
//
//    public Games initRust() {
//        Games game = new Games(
//                -1,
//                "Rust",
//                "February 9, 2018",
//                "The only aim in Rust is to survive. Everything wants you to die - the island’s wildlife and other inhabitants, the environment, other survivors. Do whatever it takes to last another night.",
//                1049.95,
//                new ArrayList<>(Arrays.asList("Survival", "Crafting", "Multiplayer", "Open World")),
//                true,
//                "Image/uploads/game_images/Rust_Card.jpg",
//                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Rust_Showcase.jpg"))
//        );
//        return game;
//    }
//
//    public Games initDontStarveTogether() {
//        Games game = new Games(
//                -1,
//                "Don't Starve Together",
//                "April 22, 2016",
//                "Fight, Farm, Build and Explore Together in the standalone multiplayer expansion to the uncompromising wilderness survival game, Don't Starve.",
//                389.95,
//                new ArrayList<>(Arrays.asList("Survival", "Open World", "Multiplayer")),
//                true,
//                "Image/uploads/game_images/DontStarveTogether_Card.jpg",
//                new ArrayList<>(Arrays.asList("Image/uploads/game_images/DontStarveTogether_Showcase.jpg"))
//        );
//        return game;
//    }
//
//    public Games initPlantsVsZombies() {
//        Games game = new Games(
//                -1,
//                "Plants Vs Zombies",
//                "May 6, 2009",
//                "Zombies are invading your home, and the only defense is your arsenal of plants! Armed with an alien nursery-worth of zombie-zapping plants like peashooters and cherry bombs, you'll need to think fast and plant faster to stop dozens of types of zombies dead in their tracks.",
//                165.95,
//                new ArrayList<>(Arrays.asList("Tower Defense", "Zombie", "Strategy", "Single player")),
//                true,
//                "Image/uploads/game_images/PVZ_Card.jpg",
//                new ArrayList<>(Arrays.asList("Image/uploads/game_images/PVZ_Showcase.webp"))
//        );
//        return game;
//    }
//
//    public Games initBloonsTD6() {
//        Games game = new Games(
//                -1,
//                "Bloons TD 6",
//                "December 18, 2018",
//                "The Bloons are back and better than ever! Get ready for a massive 3D tower defense game designed to give you hours and hours of the best strategy gaming available.",
//                379.95,
//                new ArrayList<>(Arrays.asList("Tower Defense", "Strategy", "Multiplayer", "Single player")),
//                true,
//                "Image/uploads/game_images/BTD6_Card.jpg",
//                new ArrayList<>(Arrays.asList("Image/uploads/game_images/BTD6_Showcase.jpg"))
//        );
//        return game;
//    }

}

