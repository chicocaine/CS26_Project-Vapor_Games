package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import Games.GamesManager;
import Transaction.CartManager;
import Utility.DBConnectionPool;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainScreenController implements  PageController{

    private Stage stage;

    private GamesManager gamesManager;

    private User currentUser1 = UserSession.getInstance().getCurrentUser();

    // === UI COMPONENTS ===

    @FXML
    private MenuItem AccountDropMenu, WalletDropMenu, TransactionHistoryDropMenu;

    @FXML
    private ImageView AccountPicture_Image, SearchButton_Button;

    @FXML
    private Label AccountUser_Label, AccountUserLabelDropMenu, CartNumber_Label, UserBalance_Label;

    @FXML
    private Pane BrowsePane_Button, MainContent_Pane, LogoutButton, logoPane, CartButton_Pane, BalanceButton_Pane;

    @FXML
    private HBox LibraryButton, StoreButton;

    @FXML
    private TextField SearchField_TextField;

    @FXML
    private ImageView logo;

    @FXML
    private MenuButton menuButton;


    private boolean isLibraryButtonClicked = false;
    private boolean isStoreButtonClicked = true;
    private boolean isDiscoverPage = false;

    public User currentUser;

    private boolean TopUpWasClicked = false;
    private boolean viewMyCartClicked = false;
    private boolean continueShoppingClicked = false;
    private boolean viewLibraryClicked = false;
    private boolean needsRefresh = false;

    // === SET USER METHOD ===
    public void setUser(User user) {
        this.currentUser = user;
    }

    // === INITIALIZATION ===
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        LoadHomePage();
        highlightSelectedButton(LibraryButton, StoreButton, false, true);
        updateAccountInfo();
    }

    @FXML
    private void handleAccountUserLabelClick(MouseEvent event) {
        menuButton.show();
    }

    // === BUTTON HANDLERS ===
    @FXML
    void HandlesClickedButton(MouseEvent event) {
        Object source = event.getSource();

        if (source == LogoutButton) {
            handleLogout();
        } else if (source == BrowsePane_Button) {
            handleBrowseButton();
        } else if (source == LibraryButton) {
            handleLibraryButton();
        } else if (source == StoreButton) {
            handleStoreOrDiscoverButton();
        } else if (source == SearchButton_Button) {
            handleSearchButton();
        } else if (source == CartButton_Pane) {
            handleCartPage();
        } else if (source == logoPane) {
            handleStoreOrDiscoverButton();
        } else if (source == BalanceButton_Pane) {
            handleWalletDropDown();
        }
    }

    @FXML
    void HandlesDropMenuAction(ActionEvent event) {
        if (event.getSource() == AccountDropMenu) {
            handleAccountDropdown();
        } else if (event.getSource() == WalletDropMenu) {
            handleWalletDropDown();
        } else if (event.getSource() == TransactionHistoryDropMenu) {
            handleTransactionHistoryDropMenu();
        }
    }

    // === HANDLE SPECIFIC BUTTON ACTIONS ===
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogoutPopUp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to load logout confirmation popup.");
        }
    }

    private void handleLibraryButton() {
        if (!isLibraryButtonClicked) {
            toggleButtonState(true, false);
            highlightSelectedButton(LibraryButton, StoreButton, true, false);
            LoadLibraryPage();
        }
    }

    private void handleBrowseButton() {
        LoadBrowsePage(SearchField_TextField.getText());
        SearchField_TextField.setText("");
    }

    private void handleSearchButton() {
        String query = SearchField_TextField.getText();
        if (!query.isEmpty()) {
            performSearch(query);
        }
    }

    private void handleAccountDropdown() {
        loadAccountPage();
    }

    private void handleCartPage() {
        loadCartPage();
    }

    private void handleWalletDropDown() {
        loadWalletPage();
    }

    private void handleTransactionHistoryDropMenu() {
        loadTransactionHistoryDropMenu();
    }

    // === HELPER METHODS ===
    private void performSearch(String query) {
        handleBrowseButton();
    }

    private void toggleButtonState(boolean libraryState, boolean storeState) {
        isLibraryButtonClicked = libraryState;
        isStoreButtonClicked = storeState;
    }

    private void highlightSelectedButton(HBox library, HBox store, boolean isLibrary, boolean isStore) {
        library.getStyleClass().remove("LNB_SelectionHBoxHighlighted");
        store.getStyleClass().remove("LNB_SelectionHBoxHighlighted");

        if (isLibrary) {
            library.getStyleClass().add("LNB_SelectionHBoxHighlighted");
        } else if (isStore) {
            store.getStyleClass().add("LNB_SelectionHBoxHighlighted");
        }
    }

    private void handleStoreOrDiscoverButton() {
        if (isDiscoverPage) {
            toggleButtonState(false, true);
            highlightSelectedButton(LibraryButton, StoreButton, false, true);
            LoadHomePage();
        } else {
            toggleButtonState(false, true);
            highlightSelectedButton(LibraryButton, StoreButton, false, true);
            LoadHomePage();
        }
        isDiscoverPage = !isDiscoverPage;
    }

    // === PAGE LOADING METHODS ===
    private void LoadHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StorePage.fxml"));
            Pane storePagePane = loader.load();
            setMainContent_Pane(storePagePane);

            StorePageController storePageController = loader.getController();
            for (MediumGameTileController tileController : storePageController.getGameTileControllers()) {
                tileController.setMainController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadBrowsePage(String query) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrowsePage.fxml"));
            Pane browsePagePane = loader.load();
            setMainContent_Pane(browsePagePane);

            BrowsePageController browsePageController = loader.getController();
            if (query != null && !query.isEmpty()) {
                browsePageController.setSearchQuery(query);
            }

            for (MediumGameTileController tileController : browsePageController.getMediumGameTileControllers()) {
                tileController.setMainController(this);
            }

            browsePageController.setMainScreenController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadLibraryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryPage.fxml"));
            Pane libraryPagePane = loader.load();
            setMainContent_Pane(libraryPagePane);

            LibraryPageController libraryPageController = loader.getController();

            for (MediumGameTileController tileController : libraryPageController.getMediumGameTileControllers()) {
                tileController.setMainController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAccountPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccountPage.fxml"));
            Pane accountPagePane = loader.load();
            setMainContent_Pane(accountPagePane);

            AccountPageController accountPageController = loader.getController();
            accountPageController.setUserOnProfile(currentUser);
            accountPageController.setUser(currentUser);
            System.out.println("sets user: " + currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTopUpWasClicked(boolean topUpWasClicked) {
        this.TopUpWasClicked = topUpWasClicked;
        if (TopUpWasClicked) {
            loadWalletPage();
        }
    }

    public void setContinueShoppingClicked(boolean continueShoppingClicked) {
        this.continueShoppingClicked = continueShoppingClicked;
        if (continueShoppingClicked) {
            LoadBrowsePage("");
        }
    }

    public void setViewMyCartClicked(boolean viewMyCartClicked) {
        this.viewMyCartClicked = viewMyCartClicked;
        if (viewMyCartClicked) {
            loadCartPage();
        }
    }

    public void setViewLibraryClicked(boolean viewLibraryClicked) {
        this.viewLibraryClicked = viewLibraryClicked;
        if (viewLibraryClicked) {
            LoadLibraryPage();
        }
    }

    public void setWalletPageRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
        if (needsRefresh) {
            loadWalletPage();
        }
    }

    private void loadWalletPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WalletPage.fxml"));
            Pane walletPagePane = loader.load();
            setMainContent_Pane(walletPagePane);

            WalletPageController walletPageController = loader.getController();
            walletPageController.setUserOnWallet(currentUser);
            System.out.println("Wallet page loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR]: Unable to load WalletPage.fxml");
        }
    }

    private void loadTransactionHistoryDropMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TransactionHistoryPage.fxml"));
            Pane transactionHistory = loader.load();
            setMainContent_Pane(transactionHistory);

            TransactionHistoryPageController transactionHistoryPageController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCartPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CartPage.fxml"));
            Pane cartPagePane = loader.load();
            setMainContent_Pane(cartPagePane);

            CartPageController cartPageController = loader.getController();
            User userSession = UserSession.getInstance().getCurrentUser();
            System.out.println("User on cart: " + userSession);
            CartManager cartManager = new CartManager();
            cartPageController.setUser(userSession, cartManager);
            cartPageController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // === ACCOUNT INFO UPDATE ===
    private void updateAccountInfo() {
        String username = "Loading...";
        AccountUser_Label.setText(username);
        AccountUserLabelDropMenu.setText(username);

        String profileImagePath = "/Image/ProfileTestPicture.png";
        AccountPicture_Image.setImage(new Image(profileImagePath));
    }

    public void setMainContent_Pane(Pane newPane) {
        Pane currentPane = (MainContent_Pane.getChildren().isEmpty()) ? null : (Pane) MainContent_Pane.getChildren().get(0);

        if (currentPane != null) {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), currentPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(event -> {
                MainContent_Pane.getChildren().setAll(newPane);
                newPane.setOpacity(0.0);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                fadeIn.play();
            });

            fadeOut.play();
        } else {
            MainContent_Pane.getChildren().setAll(newPane);
            newPane.setOpacity(0.0);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            fadeIn.play();
        }
    }

    public void setUserOnDashboard(User user) {
        AccountUser_Label.setText(user.getUserName());
        AccountUserLabelDropMenu.setText(user.getUserName());
        String profileImagePath = user.getPfpURL();

        CartManager cartManager = new CartManager();
        CartNumber_Label.setText(("Cart" + " (" +cartManager.getTotalGamesInCart(currentUser1)+")"));
        UserBalance_Label.setText(String.format("AGS: %.2f", user.getWallet().getBalance()));
        if (profileImagePath != null && !profileImagePath.isEmpty()) {
            AccountPicture_Image.setImage(new Image("/Image/ProfileTestPicture.png"));
        } else {
            AccountPicture_Image.setImage(new Image("/Image/ProfileTestPicture.png"));
        }
    }

    @Override
    public void refreshWallet() {
        User currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser != null) {
            double newBalance = getUpdatedBalanceFromDatabase(currentUser.getUserID());
            Platform.runLater(() -> UserBalance_Label.setText(String.format("Balance: %.2f AGS", newBalance)));
        }
    }
    private double getUpdatedBalanceFromDatabase(int userId) {
        String sql = "SELECT wallet FROM VaporGames.users WHERE userID = ?";
        try (Connection conn = DBConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("wallet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void showNotification(String message, String type) {
    }
}
