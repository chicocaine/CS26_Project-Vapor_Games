package GameCredit;

import Utility.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Redeem {

    public void redeemCode(String code) {
        String sqlCheck = "SELECT is_redeemed FROM VaporGames.game_credits WHERE code = ?";
        String sqlUpdate = "UPDATE VaporGames.game_credits SET is_redeemed = TRUE WHERE code = ?";

        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {

            checkStmt.setString(1, code);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                boolean isRedeemed = rs.getBoolean("is_redeemed");

                if (!isRedeemed) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
                        updateStmt.setString(1, code);
                        updateStmt.executeUpdate();
                        System.out.println("Code redeemed successfully! You have received your credit.");
                    }
                } else {
                    System.out.println("Error: This code has already been redeemed.");
                }
            } else {
                System.out.println("Error: Invalid code.");
            }

        } catch (SQLException e) {
            System.err.println("Error processing the redemption: " + e.getMessage());
        }
    }

    /*
    public static void main(String[] args) {
        Redeem redeem = new Redeem();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your game credit code to redeem: ");
        String code = scanner.nextLine();

        redeem.redeemCode(code);
    }*/
}
