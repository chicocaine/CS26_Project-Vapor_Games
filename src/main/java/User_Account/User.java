package User_Account;

public class User {

    private int userID;
    private String username;
    private Wallet wallet;
    private String name;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return username;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }
    
}
