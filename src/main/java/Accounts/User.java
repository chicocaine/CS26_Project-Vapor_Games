package Accounts;

import Accounts.UserManager.UserUpdates;

public class User extends Account_Interface{

    private int userID;
    private String username;
    private Wallet wallet;
    private String name;
    private String pfpURL;

    public User (int userID, String username, double wallet, String name, String pfpURL) {
        setUserID(userID);
        setUserName(username);
        setWallet(new Wallet(wallet));
        setName(name);
        setPfpURL(pfpURL);
    }

    public void setUserID (int userID) {
        this.userID = userID;
    }

    public void setUserName (String username) {
        this.username = username;
    }

    public void setWallet (Wallet wallet) {
        this.wallet = wallet;
    }

    public void setName (String name) {
        this.name = name;
    }
    public void setPfpURL (String pfpURL) {
        this.pfpURL = pfpURL;
    }

    public int getUserID () {
        return userID;
    }

    public String getUserName () {
        return username;
    }

    public Wallet getWallet () {
        return wallet;
    }

    public String getName () {
        return name;
    }

    public String getPfpURL () {
        return pfpURL;
    }
    
    public void updateBalance(double changeOfBalance) {
        this.wallet.updatedBalance(changeOfBalance);
    }

}
