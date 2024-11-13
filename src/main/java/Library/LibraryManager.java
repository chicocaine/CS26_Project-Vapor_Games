package Library;
import java.util.ArrayList;
import java.util.List;

import Games.Games;
import User_Account.User;

public class LibraryManager {

    public List<Games> getGameLibrary (User user) { 
        List<Games> game_list = new ArrayList<Games>();
        return game_list;
    }

    public void addGameToLibrary (User user, Games game) {} // add user and game in library table

    public void removeGameFromLibrary (User user, Games game) {} // remove game from user library -- p.s. idk why this is here since we don't do refunds

    public boolean isGameOwned (User user, Games game) { // what is the point of this one?
        return true;
    } 

    public String checkGameStatus (User user, Games game) { // don't know what this means
        return null;
    }
}
