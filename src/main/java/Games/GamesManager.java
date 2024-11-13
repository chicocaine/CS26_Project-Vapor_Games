package Games;

import java.util.ArrayList;
import java.util.List;

public class GamesManager {

    public List<Games> getAllGames () { 
        List<Games> game_list = new ArrayList<Games>();
        return game_list;
    }

    public Games searchGames () {
        Games queried_game = new Games();
        return queried_game;
    }

    public List<Games> filterByGenre (List<String> genre) {
        List<Games> game_list = new ArrayList<Games>();
        return game_list;
    }

    public List<Games> filterByPriceRange (double minPrice, double maxPrice) {
        List<Games> game_list = new ArrayList<Games>();
        return game_list;
    }

    // checkAvailability() no need prolly
    // update catalog ???

    public void addGame (Games game) {} // add a new game record into the database
    
    public void removeGame (Games game) {} // remove an existing game record in the database

    public void updateGame (Games game) {} // update game record in database
}