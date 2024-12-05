package GameCredit;

import Accounts.User;
import User_Interface.WalletPageController;
import Utility.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Redeem {
    private WalletPageController walletPageController;

    public Redeem(WalletPageController walletPageController) {
        this.walletPageController = walletPageController;
    }

    public void redeemCode(String code, User user) {
        String sqlCheck = "SELECT is_redeemed, credit_amount FROM VaporGames.game_credits WHERE code = ?";
        String sqlUpdate = "UPDATE VaporGames.game_credits SET is_redeemed = TRUE WHERE code = ?";
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

                        System.out.println("Code redeemed successfully! You have received your credit.");
                        walletPageController.showNotification("Code redeemed successfully!", "success");
                    } catch (SQLException e) {
                        conn.rollback(); // Rollback transaction on error
                        System.err.println("Error processing the redemption: " + e.getMessage());
                    }
                } else {
                    System.out.println("Error: This code has already been redeemed.");
                    walletPageController.showNotification("This code has already been redeemed.", "error");
                }
            } else {
                System.out.println("Error: Invalid code.");
                walletPageController.showNotification("Invalid code.", "error");
            }

        } catch (SQLException e) {
            System.err.println("Error processing the redemption: " + e.getMessage());
        }
    }
}