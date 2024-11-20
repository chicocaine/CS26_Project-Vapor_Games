package Accounts;

public class Wallet { // don't know how this works honestly

    private double balance;

    public Wallet (double initialBalance) { 
        this.balance = initialBalance;
    }

    public void addFunds (double funds) {
        this.balance += funds;
    }

    public double checkBalance () { // this can also be called getBalance()
        return balance;
    }

    public void reduceFunds (double funds) {
        this.balance -= funds;
    }
}
