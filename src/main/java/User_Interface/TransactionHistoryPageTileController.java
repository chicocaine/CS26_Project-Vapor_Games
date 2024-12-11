package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import Utility.DBConnectionPool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryPageTileController {

    @FXML
    private Label Date_Label;

    @FXML
    private Label OrderID_Label;

    @FXML
    private Label TotalPrice_Label;

    @FXML
    private Button DownloadReciept_Button;

    public User currentUser = UserSession.getInstance().getCurrentUser();

    public void setTransactionDetails(String date, int orderId, double totalPrice) {
        Date_Label.setText(date);
        OrderID_Label.setText(String.valueOf(orderId));
        TotalPrice_Label.setText(String.format("%.2f", totalPrice));
    }

    @FXML
    private void HandlesButtonClicked(MouseEvent event) {
        downloadReceipt(Integer.parseInt(OrderID_Label.getText()));
    }

    public void downloadReceipt(int transactionID) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Receipt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                String receiptContent = generateReceiptContent(transactionID);
                fileWriter.write(receiptContent);
                System.out.println("Receipt saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[ERROR] Failed to save receipt.");
            }
        }
    }

    private String generateReceiptContent(int transactionID) {
        StringBuilder receipt = new StringBuilder();

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM transactions WHERE transactionID = ?")) {

            preparedStatement.setInt(1, transactionID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String date = resultSet.getString("transaction_date");
                String games = resultSet.getString("transaction_games");
                double totalPrice = resultSet.getDouble("transaction_amount");
                String username = currentUser.getUserName();

                receipt.append("Vapor Games\n\n");
                receipt.append("UM Matina Street\n");
                receipt.append("PS Bldg. Room 301\n");
                receipt.append("Davao City\n\n");
                receipt.append("Online Purchase\n");
                receipt.append("\"Powered by AGS COiN\"\n\n");
                receipt.append(date).append("\n");
                receipt.append("Order ID:\t").append(transactionID).append("\n");
                receipt.append(String.format("User Name:\t%s\n\n", username));
                receipt.append("ITEM\t\t\t  -\t\t-Price\n");
                receipt.append("-----------------------------------------------------\n");

                String[] gameItems = games.split(",");
                for (String game : gameItems) {
                    String gameTitle = game.trim().replaceAll("\\(.*\\)", "");
                    double gamePrice = getGamePrice(gameTitle);
                    receipt.append(String.format("%-25s", gameTitle))
                            .append(" - \t\t- ")                         //
                            .append(String.format("%.2f", gamePrice / 6.9))
                            .append(" ags\n");
                }

                receipt.append("\n  ***\t\t\t\t  Total: ").append(String.format("%.2f", totalPrice)) // Format total price
                        .append(" ags\n\n"); // Add newline after total

                receipt.append("------------------------------------------------------\n");
                receipt.append("Paid By:\t\t\tAGS COiN\n\n");
                receipt.append("THANK YOU FOR YOUR PURCHASE!\n");
                receipt.append("HEIL AGS COIN!\n");
            } else {
                receipt.append("[ERROR] Transaction not found.\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            receipt.append("[ERROR] Database query failed.\n");
        }

        return receipt.toString();
    }

    private double getGamePrice(String gameTitle) {
        double price = 0.0;
        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT price FROM games WHERE gameTitle = TRIM(?)")) {
            preparedStatement.setString(1, gameTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
                System.out.println("Price: " + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
}
