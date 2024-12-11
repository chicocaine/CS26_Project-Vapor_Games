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
    public Games initLeft4Dead2() {
        Games game = new Games(
                -1,
                "Left 4 Dead 2",
                "17 Nov, 2009",
                "Set in the zombie apocalypse, Left 4 Dead 2 (L4D2) is the highly anticipated sequel to the award-winning Left 4 Dead, the #1 co-op game of 2008. This co-operative action horror FPS takes you and your friends through the cities, swamps and cemeteries of the Deep South, from Savannah to New Orleans.",
                335.00,
                new ArrayList<>(Arrays.asList("Zombie", "Co-op", "Multiplayer", "Shooter")),
                true,
                "Image/uploads/game_images/Left4Dead_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Left4Dead_Showcase.jpg"))
        );
        return game;
    }

    public Games initIfMyHeartHadWings() {
        Games game = new Games(
                -1,
                "If My Heart Had Wings",
                "28 Jun, 2013",
                "An animated visual novel of a lovely and bittersweet tale of youth, you play as Aoi Minase in this romantic-comedy as he meets various young girls in his hometown and works together with them to revive their school’s glider-flying 'Soaring Club' in order to realize their shared dream of flight.",
                419.00,
                new ArrayList<>(Arrays.asList("Visual Novel", "2D")),
                true,
                "Image/uploads/game_images/IF MY HEART HAD WINGS_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/IF MY HEART HAD WINGS_Showcase.jpg"))
        );
        return game;
    }

    public Games initNekoparaVol1() {
        Games game = new Games(
                -1,
                "Nekopara Vol. 1",
                "30 Dec, 2014",
                "What's NEKOPARA? Why, it's a cat paradise! Kashou Minaduki, the son of a long line of Japanese confection makers moved out to open his own shop 'La Soleil' as a patisserie.",
                335.00,
                new ArrayList<>(Arrays.asList("Visual Novel", "2D")),
                true,
                "Image/uploads/game_images/Nekopara_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Nekopara_Showcase.jpg"))
        );
        return game;
    }

    public Games initHonkaiStarRail() {
        Games game = new Games(
                -1,
                "Honkai Star Rail",
                "26 Apr, 2023",
                "Honkai: Star Rail is a new HoYoverse space fantasy RPG. Hop aboard the Astral Express and experience the galaxy's infinite wonders on this journey filled with adventure and thrills.",
                0.00,
                new ArrayList<>(Arrays.asList("Adventure", "Role-Playing (RPG)", "Turn-Based Strategy")),
                true,
                "Image/uploads/game_images/HSR_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/HSR_Showcase.jpg"))
        );
        return game;
    }

    public Games initGenshinImpact() {
        Games game = new Games(
                -1,
                "Genshin Impact",
                "09 July, 2021",
                "Embark on a journey across Teyvat to find your lost sibling and seek answers from The Seven — the gods of each element. Explore this wondrous world, join forces with a diverse range of characters, and unravel the countless mysteries that Teyvat holds...",
                0.00,
                new ArrayList<>(Arrays.asList("Adventure", "Open World", "Role-Playing (RPG)")),
                true,
                "Image/uploads/game_images/GenshinImpact_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/GenshinImpact_Showcase.jpg"))
        );
        return game;
    }

    public Games initTheOuterWorlds() {
        Games game = new Games(
                -1,
                "The Outer Worlds",
                "08 March, 2023",
                "The Outer Worlds: Spacer’s Choice Edition is the ultimate way to play the award-winning RPG from Obsidian Entertainment and Private Division. Including the base game and all DLC, this remastered masterpiece is the absolute best version of The Outer Worlds.",
                2299.00,
                new ArrayList<>(Arrays.asList("Action", "Role-Playing (RPG)", "Shooter")),
                true,
                "Image/uploads/game_images/The Outer Worlds_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/The Outer Worlds_Showcase.jpg"))
        );
        return game;
    }

    public Games initOrwellKeepingAnEyeOnYou() {
        Games game = new Games(
                -1,
                "Orwell: Keeping an Eye on You",
                "18 March, 2022",
                "Big Brother has arrived - and it’s you. Investigate the lives of citizens to find those responsible for a series of terror attacks. But, be warned, the information you supply will have consequences…",
                289.95,
                new ArrayList<>(Arrays.asList("Simulation")),
                true,
                "Image/uploads/game_images/ORWELL_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/ORWELL_Showcase.jpg"))
        );
        return game;
    }

    public Games initGuiltyGearStrive() {
        Games game = new Games(
                -1,
                "Guilty Gear Strive",
                "11 Jun, 2021",
                "The cutting-edge 2D/3D hybrid graphics pioneered in the Guilty Gear series have been raised to the next level in 'GUILTY GEAR -STRIVE-'. The new artistic direction and improved character animations will go beyond anything you’ve seen before in a fighting game!",
                2046.00,
                new ArrayList<>(Arrays.asList("2D", "Fighting")),
                true,
                "Image/uploads/game_images/Guilty Gear_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Guilty Gear_Showcase.jpg"))
        );
        return game;
    }

    public Games initTownscaper() {
        Games game = new Games(
                -1,
                "Townscaper",
                "27 Aug, 2021",
                "Instant town building toy",
                209.95,
                new ArrayList<>(Arrays.asList("Sandbox")),
                true,
                "Image/uploads/game_images/Townscraper_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Townscraper_Showcase.jpg"))
        );
        return game;
    }

    public Games initBallionaire() {
        Games game = new Games(
                -1,
                "Ballionaire",
                "10 Dec, 2024",
                "BALLIONAIRE is a fast-paced, kinetic roguelike where strategy meets physics to create outrageous wealth! Find and exploit game-breaking synergies as you theorycraft your way to victory. Welcome to the autobonker genre!",
                424.00,
                new ArrayList<>(Arrays.asList("Single Player", "Roguelike", "Turn-Based Strategy")),
                true,
                "Image/uploads/game_images/Ballionaire_Card.jpg",
                new ArrayList<>(Arrays.asList("/Image/uploads/game_images/Ballionaire_Showcase.jpg"))
        );
        return game;
    }
    public Games initDokiDokiLiteratureClub() {
        Games game = new Games(
                -1,
                "Doki-Doki Literature Club",
                "22 Sep, 2017",
                "The Literature Club is full of cute girls! Will you write the way into their heart? This game is not suitable for children or those who are easily disturbed.",
                0.00,
                new ArrayList<>(Arrays.asList("Visual Novel")),
                true,
                "Image/uploads/game_images/Doki-Doki_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Doki-Doku_Shocase.jpg"))
        );
        return game;
    }

    public Games initSlayTheSpire() {
        Games game = new Games(
                -1,
                "Slay the Spire",
                "23 Jan, 2019",
                "We fused card games and roguelikes together to make the best single player deckbuilder we could. Craft a unique deck, encounter bizarre creatures, discover relics of immense power, and Slay the Spire!",
                780.00,
                new ArrayList<>(Arrays.asList("Card Game", "Roguelike")),
                true,
                "Image/uploads/game_images/Slay the Spire_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Slay the Spire_Showcase.jpg"))
        );
        return game;
    }


    public Games initEnterTheGungeon() {
        Games game = new Games(
                -1,
                "Enter the Gungeon",
                "5 Apr, 2016",
                "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.",
                970.00,
                new ArrayList<>(Arrays.asList("Roguelike", "Bullet Hell")),
                true,
                "Image/uploads/game_images/Enter the Gungeon_Card.jpg",
                new ArrayList<>(Arrays.asList("Image/uploads/game_images/Enter the Gungeon_Showcase.jpg"))
        );
        return game;
    }




}

