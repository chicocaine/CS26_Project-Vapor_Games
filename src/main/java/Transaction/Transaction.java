package Transaction;

import Games.Games;
import Accounts.User;
import Accounts.UserManager;
import Utility.DBConnectionPool;
import Utility.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
public class Transaction {
    
    CartManager cm = new CartManager();
    private ArrayList<Games> game_list;
    private User user;
    private boolean isConfirmed = false;
    private String transaction_date_time;

    public void loadTransaction (User user) {
        this.game_list = cm.getCart(user);
        this.user = user;
    }

    public ArrayList<Games> getGamesTransactionList () {
        return this.game_list;
    }

    public double getTransactionTotalPrice () {
        return cm.getTotalPrice(this.game_list);
    }

    public void recordTransaction () {
        if (this.isConfirmed) {
            String query = "INSERT INTO transactions (userID, transaction_date, transaction_games, transaction_amount) VALUES (?, ?, ?, ?)";

            StringBuilder formattedGames = new StringBuilder();
            for (Games game : this.game_list) {
                formattedGames.append(game.getGameTitle())
                            .append(" (")
                            .append(String.format("%.2f", game.getGamePrice()))
                            .append("), ");
            }

            if (formattedGames.length() > 0) {
                formattedGames.setLength(formattedGames.length() - 2);
            }

            try (Connection conn = DBConnectionPool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, user.getUserID());
                stmt.setString(2, this.transaction_date_time);
                stmt.setString(3, formattedGames.toString());
                stmt.setDouble(4, getTransactionTotalPrice());

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Transaction recorded successfully.");
                } else {
                    System.out.println("Failed to record the transaction.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Transaction is not confirmed. Cannot record.");
        }
    }


    public boolean confirmTransaction (boolean isConfirmed) {
        DateTime dt = new DateTime();
        UserManager um = new UserManager();
        UserManager.UserUpdates up = um.new UserUpdates();
        
        user.updateBalance(0 - getTransactionTotalPrice());
        up.updateWalletBalance(this.user.getUserID(), user.getWallet().getBalance());
        this.isConfirmed = isConfirmed;
        this.transaction_date_time = dt.getDateTime();

        return isConfirmed;
    }
}
