package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordManager {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16; // Length of the salt

    // Hashes a password with a randomly generated salt
    public String hashPassword(String password) {
        try {
            // Generate a salt
            byte[] salt = generateSalt();

            // Get an instance of the MessageDigest (SHA-256)
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);

            // Combine the password and the salt
            digest.update(salt);

            // Hash the password
            byte[] hashedPassword = digest.digest(password.getBytes());

            // Convert the hash to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedPassword) {
                hexString.append(String.format("%02x", b));
            }

            // Return the hex string as the hashed password
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    // Verifies if the given password matches the hashed one
    public boolean verifyPassword(String password, String hashedPassword) {
        // Just a simple implementation; in real-world use, store the salt separately
        String rehashedPassword = hashPassword(password);

        // Compare the hashed password with the stored one
        return rehashedPassword.equals(hashedPassword);
    }

    // Generates a random salt
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static void main(String[] args) {
        // Example usage
        // String password = "superSecretPassword123!";

        // Hash the password
        // String hashedPassword = hashPassword(password);
        // System.out.println("Hashed Password: " + hashedPassword);

        // Verify the password
        // boolean isValid = verifyPassword(password, hashedPassword);
        // System.out.println("Password valid: " + isValid);
    }
}