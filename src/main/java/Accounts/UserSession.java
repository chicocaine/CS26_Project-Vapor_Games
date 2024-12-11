package Accounts;

import Transaction.Transaction; // Assuming Transaction class is in this package

public class UserSession {
    private static UserSession instance;
    private User currentUser;
    private Transaction currentTransaction; // Added field for current transaction

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction transaction) {
        this.currentTransaction = transaction;
    }
    public String getCurrentTransactionDate() {
        if (currentTransaction != null) {
            return currentTransaction.getTransactionDate();
        } else {
            return "No transaction available.";
        }
    }
}
