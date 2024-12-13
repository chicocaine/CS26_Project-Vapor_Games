package Accounts;

public class Wallet { // don't know how this works honestly

    private double balance;

    public Wallet (double initialBalance) { 
        this.balance = initialBalance;
    }

    public void updateBalance (double funds) {
        this.balance = funds;
    }

    public void updatedBalance (double funds) {
        this.balance += funds;
    }

    public void decrementBalance (double funds) {
        this.balance -= funds;
    }

    public void setBalance (double balance) {
        this.balance = balance;
    }
    public double getBalance () {
        return balance;
    }

}
