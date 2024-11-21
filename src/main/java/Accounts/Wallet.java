package Accounts;

public class Wallet { // don't know how this works honestly

    private double balance;

    public Wallet (double initialBalance) { 
        this.balance = initialBalance;
    }

    public void updateBalance (double funds) {
        this.balance += funds;
    }

    public double getBalance () { // this can also be called getBalance()
        return balance;
    }

}
