package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import Utility.DBConnectionPool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionHistoryPageController {

    @FXML
    private VBox TransactionHistory_VBox;

    public void initialize() {
        loadTransactionHistory();
    }

    private void loadTransactionHistory() {
        User currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser != null) {
            String query = "SELECT * FROM transactions WHERE userID = ? ORDER BY transaction_date DESC";

            try (Connection conn = DBConnectionPool.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, currentUser.getUserID());
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/TransactionHistoryPageTile.fxml"));
                    Pane transactionTile = loader.load();

                    TransactionHistoryPageTileController tileController = loader.getController();
                    tileController.setTransactionDetails(
                            rs.getString("transaction_date"),
                            rs.getInt("transactionID"),
                            rs.getDouble("transaction_amount")
                    );

                    TransactionHistory_VBox.getChildren().add(transactionTile);
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}