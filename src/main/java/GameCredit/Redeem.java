package GameCredit;

import Accounts.User;
import Accounts.UserSession;
import Transaction.Transaction;
import User_Interface.PageController;
import User_Interface.WalletPageController;
import Utility.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Redeem {
    private PageController pageController;


    public User currentUser = UserSession.getInstance().getCurrentUser();

    public Redeem(PageController pageController) {
        this.pageController = pageController;
    }

    public boolean redeemCode(String code, User user) {
        String sqlCheck = "SELECT is_redeemed, credit_amount FROM VaporGames.game_credits WHERE code = TRIM(?)";
        String sqlUpdate = "UPDATE VaporGames.game_credits SET is_redeemed = TRUE WHERE code = TRIM(?)";
        String sqlUpdateWallet = "UPDATE VaporGames.users SET wallet = wallet + ? WHERE userID = ?";

        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {

            checkStmt.setString(1, code);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                boolean isRedeemed = rs.getBoolean("is_redeemed");
                double creditAmount = rs.getDouble("credit_amount");

                if (!isRedeemed) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate);
                         PreparedStatement updateWalletStmt = conn.prepareStatement(sqlUpdateWallet)) {

                        conn.setAutoCommit(false); // Start transaction

                        updateStmt.setString(1, code);
                        updateStmt.executeUpdate();

                        updateWalletStmt.setDouble(1, creditAmount);
                        updateWalletStmt.setInt(2, user.getUserID());
                        updateWalletStmt.executeUpdate();

                        conn.commit(); // Commit transaction

                        Transaction transaction = new Transaction(currentUser);
                        transaction.confirmRedemption(true);
                        transaction.recordRedeemTransaction(code, creditAmount);

                        System.out.println("Code redeemed successfully! You have received your credit.");
                        pageController.showNotification("Code redeemed successfully!", "success");
                        pageController.refreshWallet();
                        return true;
                    } catch (SQLException e) {
                        conn.rollback(); // Rollback transaction on error
                        System.err.println("Error processing the redemption: " + e.getMessage());
                    }
                } else {
                    System.out.println("Error: This code has already been redeemed.");
                    pageController.showNotification("This code has already been redeemed.", "alreadyRedeemed");
                }
            } else {
                System.out.println("Error: Invalid code.");
                pageController.showNotification("Invalid code.", "error");
            }

        } catch (SQLException e) {
            System.err.println("Error processing the redemption: " + e.getMessage());
        }
        return false;
    }
}