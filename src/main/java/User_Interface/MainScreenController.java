package User_Interface;

import Accounts.User;
import Accounts.UserSession;
import Transaction.CartManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;

public class MainScreenController {

    private Stage stage;

    // === UI COMPONENTS ===

    @FXML
    private MenuItem AccountDropMenu, WalletDropMenu, TransactionHistoryDropMenu;

    @FXML
    private ImageView AccountPicture_Image, SearchButton_Button;

    @FXML
    private Label AccountUser_Label, AccountUserLabelDropMenu;

    @FXML
    private Pane BrowsePane_Button, CartButton_Pane, MainContent_Pane, ReturnButton_Pane, LogoutButton;

    @FXML
    private HBox LibraryButton, StoreButton;

    @FXML
    private TextField SearchField_TextField;

    private boolean isLibraryButtonClicked = false;
    private boolean isStoreButtonClicked = true;

    private boolean isDiscoverPage = false;  // Flag to track the current page state

    @FXML
    private AnchorPane walletPageContainer; // Placeholder for wallet page content

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;

    }

    private CartManager cartManager;  // Assuming CartManager is a class that you have

    // Setter to initialize CartManager
    public void setCartManager(CartManager cartManager) {
        this.cartManager = cartManager;
    }



    // === INITIALIZATION & SETUP ===
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // This is the first function called when the controller is loaded
    @FXML
    public void initialize() {
        LoadHomePage();  // Load the initial home page (store page)
        highlightSelectedButton(LibraryButton, StoreButton, false, true);  // Highlight the StoreButton
        updateAccountInfo();  // Set the user account info (e.g., username, profile image)
    }

    // === BUTTON HANDLERS ===
    @FXML
    void HandlesClickedButton(MouseEvent event) {
        Object source = event.getSource();

        if (source == LogoutButton) {
            handleLogout();  // Handle user logout
        } else if (source == BrowsePane_Button) {
            handleBrowseButton();  // Handle browsing
        } else if (source == LibraryButton) {
            handleLibraryButton();  // Handle library button click
        } else if (source == StoreButton) {
            handleStoreOrDiscoverButton();  // Handle the store button click
        } else if (source == SearchButton_Button) {
            handleSearchButton();  // Handle search button click
        } else if (source == CartButton_Pane){
            handleCartPage();
        }
    }

    @FXML
    void HandlesDropMenuAction(ActionEvent event) {
        if (event.getSource() == AccountDropMenu){
            handleAccountDropdown();
        } else if (event.getSource() == WalletDropMenu){
            handleWalletDropDown();
        } else if (event.getSource() == TransactionHistoryDropMenu){
            handleTransactionHistoryDropMenu();
        }
    }


    // === HANDLE SPECIFIC BUTTON ACTIONS ===
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);  // Exit the program
        }
    }

    private void handleLibraryButton() {
        if (!isLibraryButtonClicked) {
            toggleButtonState(true, false);  // Set library button clicked state
            highlightSelectedButton(LibraryButton, StoreButton, true, false);  // Highlight the LibraryButton
            LoadLibraryPage();  // Load the library page
        }
    }

    private void handleStoreButton() {
        if (!isStoreButtonClicked) {
            toggleButtonState(false, true);  // Set store button clicked state
            highlightSelectedButton(LibraryButton, StoreButton, false, true);  // Highlight the StoreButton
            LoadHomePage();  // Load the home page (store page)
        }
    }

    private void handleBrowseButton() {
        LoadBrowsePage(SearchField_TextField.getText());  // Perform search and load browse page
        SearchField_TextField.setText("");  // Clear the search field
    }


    private void handleSearchButton() {
        String query = SearchField_TextField.getText();
        if (!query.isEmpty()) {
            performSearch(query);  // Perform search if query is not empty
        }
    }

    private void handleAccountDropdown() {
        loadAccountPage();
    }

    private void handleCartPage(){
        loadCartPage();
    }

    private void handleWalletDropDown(){
        loadWalletPage();
    }

    private void handleTransactionHistoryDropMenu(){
        loadTransactionHistoryDropMenu();
    };

    // === HELPER METHODS ===
    private void performSearch(String query) {
        handleBrowseButton();  // Handle search by calling browse button handler
    }

    private void toggleButtonState(boolean libraryState, boolean storeState) {
        isLibraryButtonClicked = libraryState;  // Set the library button state
        isStoreButtonClicked = storeState;  // Set the store button state
    }

    private void highlightSelectedButton(HBox library, HBox store, boolean isLibrary, boolean isStore) {
        // Remove the highlight class from both buttons first
        library.getStyleClass().remove("LNB_SelectionHBoxHighlighted");
        store.getStyleClass().remove("LNB_SelectionHBoxHighlighted");

        // Now add the highlight class to the selected button
        if (isLibrary) {
            library.getStyleClass().add("LNB_SelectionHBoxHighlighted");
        } else if (isStore) {
            store.getStyleClass().add("LNB_SelectionHBoxHighlighted");
        }
    }


    private void handleStoreOrDiscoverButton() {
        if (isDiscoverPage) {
            // If currently on the discover page, switch to store
            toggleButtonState(false, true);  // Update button states
            highlightSelectedButton(LibraryButton, StoreButton, false, true);  // Highlight the StoreButton
            LoadHomePage();  // Load store page
        } else {
            // If currently on the store page, switch to discover
            toggleButtonState(false, true);  // Set Store to true and Discover to false
            highlightSelectedButton(LibraryButton, StoreButton, false, true);  // Highlight the Store button
            LoadHomePage();  // Or load the discover page
        }

        // Toggle the state to indicate which page is active
        isDiscoverPage = !isDiscoverPage;  // Toggle the page flag
    }

    // === PAGE LOADING METHODS ===
    private void LoadHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StorePage.fxml"));
            Pane storePagePane = loader.load();
            setMainContent_Pane(storePagePane);  // Set the main content to store page

            // Access the controller for the loaded page
            StorePageController storePageController = loader.getController();
            for (GameTileController tileController : storePageController.getGameTileControllers()) {
                tileController.setMainController(this);  // Set the main controller for each tile
                //tileController.setCurrentUser(currentUser);
            }

        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    private void LoadBrowsePage(String query) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrowsePage.fxml"));
            Pane browsePagePane = loader.load();
            setMainContent_Pane(browsePagePane);  // Set the main content to browse page

            BrowsePageController browsePageController = loader.getController();
            if (query != null && !query.isEmpty()) {
                browsePageController.setSearchQuery(query);  // Pass search query to the browse page
            }

            // Set the main controller for each MediumGameTileController, if applicable
            for (MediumGameTileController tileController : browsePageController.getMediumGameTileControllers()) {
                tileController.setMainController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    private void LoadLibraryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryPage.fxml"));
            Pane libraryPagePane = loader.load();
            setMainContent_Pane(libraryPagePane);  // Set the main content to library page

            LibraryPageController libraryPageController = loader.getController();

            // Set the main controller for each tile in the library page
            for (MediumGameTileController tileController : libraryPageController.getMediumGameTileControllers()) {
                tileController.setMainController(this);  // Set the main controller for each tile
            }

        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    private void loadAccountPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccountPage.fxml"));
            Pane accountPagePane = loader.load();
            setMainContent_Pane(accountPagePane);  // Set the main content to library page

            AccountPageController accountPageController = loader.getController();
            accountPageController.setUserOnProfile(currentUser);
            accountPageController.setUser(currentUser);
            System.out.println("sets user: " + currentUser);
        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }

    private void loadWalletPage() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WalletPage.fxml"));
        Pane walletPagePane = loader.load();
        setMainContent_Pane(walletPagePane);

        WalletPageController walletPageController = loader.getController();
        System.out.println("Setting user on wallet page" + currentUser.getWallet().getBalance());
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
            Pane TransactionHistory = loader.load();
            setMainContent_Pane(TransactionHistory);

            TransactionHistoryPageController transactionHistoryPageController = loader.getController();
            //transactionHistoryPageController.setUserOnWallet(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCartPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CartPage.fxml"));
            Pane cartPagePane = loader.load();
            setMainContent_Pane(cartPagePane);  // Set the main content to cart page

            // Access the controller for the loaded cart page
            CartPageController cartPageController = loader.getController();
            User userSession = UserSession.getInstance().getCurrentUser();
            System.out.println("User on cart: " + userSession);
            CartManager cartManager = new CartManager();
            cartPageController.setUser(userSession, cartManager);  // Pass currentUser and cartManager
            cartPageController.initialize();
            //cartPageController.displayCart();
        } catch (IOException e) {
            e.printStackTrace();  // Handle potential loading errors
        }
    }


    // === ACCOUNT INFO UPDATE ===
    private void updateAccountInfo() {
        String username = "march";  // Replace with actual username retrieval logic
        AccountUser_Label.setText(username);
        AccountUserLabelDropMenu.setText(username);

        String profileImagePath = "/Image/ProfileTestPicture.png";  // Replace with actual image path
        AccountPicture_Image.setImage(new Image(profileImagePath));  // Set profile image
    }

    // === FADE IN/OUT TRANSITIONS ===
    public void setMainContent_Pane(Pane newPane) {
        Pane currentPane = (MainContent_Pane.getChildren().isEmpty()) ? null : (Pane) MainContent_Pane.getChildren().get(0);

        if (currentPane != null) {
            // Fade out the current pane
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), currentPane);
            fadeOut.setFromValue(1.0);  // Fully visible
            fadeOut.setToValue(0.0);    // Fully transparent

            fadeOut.setOnFinished(event -> {
                // Remove the current pane and add the new one with opacity set to 0 initially
                MainContent_Pane.getChildren().setAll(newPane);
                newPane.setOpacity(0.0);  // Set the new pane as invisible initially

                // Fade in the new pane
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newPane);
                fadeIn.setFromValue(0.0);  // Start fully transparent
                fadeIn.setToValue(1.0);    // End fully visible

                fadeIn.play();
            });

            fadeOut.play();
        } else {
            // No current pane, directly show the new one with fade-in (initially invisible)
            MainContent_Pane.getChildren().setAll(newPane)  ;
            newPane.setOpacity(0.0);  // Set the new pane as invisible initially

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newPane);
            fadeIn.setFromValue(0.0);  // Start fully transparent
            fadeIn.setToValue(1.0);    // End fully visible

            fadeIn.play();
        }
    }


    public void setUserOnDashboard(User user) {
        AccountUser_Label.setText(user.getUserName());
        AccountUserLabelDropMenu.setText(user.getUserName());
        String profileImagePath = user.getPfpURL(); // Assuming User class has a method to get profile image path
        if (profileImagePath != null && !profileImagePath.isEmpty()) {
            AccountPicture_Image.setImage(new Image("/Image/ProfileTestPicture.png"));//test image
        } else {
            // Set a default profile image if the user does not have one
            AccountPicture_Image.setImage(new Image("/Image/ProfileTestPicture.png"));
        }
    }
}
