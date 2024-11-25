package Model;

public class Game {
    private String title;
    private String price;
    private String description;
    private String imagePath;

    // Constructor
    public Game(String title, String price, String imagePath, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }
}
