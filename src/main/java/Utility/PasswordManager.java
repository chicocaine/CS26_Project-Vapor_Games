package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordManager {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16; // Length of the salt

    // Hashes a password with a randomly generated salt
    public String hashPassword(String password) {
        try {
            byte[] salt = generateSalt();
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(salt);
            byte[] hashedPassword = digest.digest(password.getBytes());

            // Combine salt and hash as a single string
            StringBuilder hexString = new StringBuilder();
            for (byte b : salt) {
                hexString.append(String.format("%02x", b));
            }
            hexString.append(":"); // Delimiter between salt and hash
            for (byte b : hashedPassword) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }


    // Verifies if the given password matches the hashed one
    public boolean verifyPassword(String password, String storedValue) {
        try {
            String[] parts = storedValue.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid stored hash format");
            }

            // Extract salt
            byte[] salt = new byte[SALT_LENGTH];
            for (int i = 0; i < salt.length; i++) {
                salt[i] = (byte) Integer.parseInt(parts[0].substring(i * 2, (i + 1) * 2), 16);
            }

            // Hash the input password with the extracted salt
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(salt);
            byte[] hashedPassword = digest.digest(password.getBytes());

            // Convert hashed password to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedPassword) {
                hexString.append(String.format("%02x", b));
            }

            // Compare with stored hash
            return hexString.toString().equals(parts[1]);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }


    // Hashes a password with the provided salt
    private byte[] hashWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        digest.update(salt);
        return digest.digest(password.getBytes());
    }

    // Generates a random salt
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static void main(String[] args) {
        //PasswordManager pwm = new PasswordManager();

        // Example usage
        //String password = "superSecretPassword123!";
        //String hashedPassword = pwm.hashPassword(password);
        //System.out.println("Hashed Password: " + hashedPassword);

        //boolean isValid = pwm.verifyPassword(password, hashedPassword);
       // System.out.println("Password valid: " + isValid);
    }
}
