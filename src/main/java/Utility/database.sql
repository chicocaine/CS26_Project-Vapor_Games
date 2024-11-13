-- Create new database

CREATE TABLE users (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) UNIQUE NOT NULL,
    wallet DECIMAL(7, 2) NOT NULL,
    name TEXT,
    password CHAR(64) NOT NULL
);

CREATE TABLE games (
    gameID INT PRIMARY KEY AUTO_INCREMENT,
    gameTitle TEXT NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(7, 2) NOT NULL,
    available BOOLEAN NOT NULL
);

CREATE TABLE genres (
    genreID INT PRIMARY KEY AUTO_INCREMENT,
    genreName TEXT NOT NULL
);

CREATE TABLE genre_games (
    gameID INT NOT NULL,
    genreID INT NOT NULL,
    FOREIGN KEY (gameID) REFERENCES games(gameID),
    FOREIGN KEY (genreID) REFERENCES genres(genreID),
    PRIMARY(gameID, genreID)
);

CREATE TABLE libraries (
    gameID INT NOT NULL,
    userID INT NOT NULL,
    FOREIGN KEY (gameID) REFERENCES games(gameID),
    FOREIGN KEY (userID) REFERENCES genres(userID),
    PRIMARY(gameID, userID)
);

CREATE TABLE carts (
    cartID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES users(userID)
);

CREATE TABLE cart_games (
    cartID INT NOT NULL,
    gameID INT NOT NULL,
    FOREIGN KEY (gameID) REFERENCES games(gameID),
    FOREIGN KEY (cartID) REFERENCES genres(cartID),
    PRIMARY(gameID, cartID)
);

CREATE TABLE transactions (
    transactionID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    cartID INT NOT NULL,
    transaction_amount DECIMAL(7, 2) NOT NULL,
    FOREIGN KEY (gameID) REFERENCES games(gameID),
    FOREIGN KEY (cartID) REFERENCES genres(cartID)
);
