package Games;

import java.util.ArrayList;

public class Games_Test {
    public static void main (String[] args) {
        String[] genre =  {"Free to Play", "2D Platformer", "Indie"};
        Games sheepyashortadventure2 = new Games(-1, "Sheepy: A Short Adventure 2", "06-06-2025", "A Short Handcrafted Pixel Art Platformer that follows Sheepy, an abandoned plushy brought to life. Sheepy: A Short Adventure 2 is the sequel of Sheepy: A Short Adventure from MrSuicideSheep.", 0, arrayToArrayList(genre), true, null);

    
        GamesManager gm = new GamesManager();
        
        // Test add games
        gm.addGame(sheepyashortadventure2);
        // Test load games
        gm.GamesLoader();
        
        ArrayList<Games> gamesList = gm.getAllGames();



        for (Games game : gamesList) {
            System.out.println("=================================================");
            System.out.println(game.getGameTitle());
            System.out.println(game.getGameReleaseDate());
            System.out.println(game.getGameDescription());
            System.out.println(game.getGamePrice());
            System.out.println(game.isAvailable());
            System.out.println("=================================================");
        }
        
    }

    public static ArrayList<String> arrayToArrayList (String[] arr) {
        ArrayList<String> arrList = new ArrayList<String>();

        for (String x : arr) {
            arrList.add(x);
        }

        return arrList;
    } 
    

}
