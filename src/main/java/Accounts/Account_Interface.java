package Accounts;

abstract class Account_Interface { // can add more user parameters 
    
    protected int userID;
    protected String username;
    protected Wallet wallet;
    protected String name;
    protected String pfpURL;

    public void User (int userID, String username, Wallet wallet, String name, String pfpURL) {};
    public abstract void setUserID (int userID);
    public abstract void setUserName (String username);
    public abstract void setWallet (Wallet wallet);
    public abstract void setName (String name);
    public abstract void setPfpURL (String pfpURL);

    public abstract int getUserID ();
    public abstract String getUserName ();
    public abstract Wallet getWallet ();
    public abstract String getName ();
    public abstract String getPfpURL ();

}
