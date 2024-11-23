package Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ProfilePictureUtility {

    /**
     * Copies the given image file to the specified directory with a UUID filename.
     * @param photoFile The photo file to be uploaded.
     * @param directoryPath The directory where the photo will be copied to.
     * @return The UUID that represents the file's name in the directory.
     * @throws IOException If there is an issue reading or writing the file.
     */
    public String saveProfilePicture(File photoFile, String directoryPath) throws IOException {
        // Check if the directory exists, if not, create it
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Create directory if it doesn't exist
        }

        // Generate a unique filename using UUID
        String uniqueFileName = UUID.randomUUID().toString() + getFileExtension(photoFile);

        // Define the path where the file will be copied
        Path targetPath = new File(directory, uniqueFileName).toPath();

        // Copy the file to the target path
        Files.copy(photoFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // Return the UUID (which is the filename) of the saved file
        return uniqueFileName;
    }

    /**
     * Gets the file extension of the provided file.
     * @param file The file to retrieve the extension from.
     * @return The file extension (e.g., ".jpg").
     */
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = fileName.substring(dotIndex);  // Include the dot in the extension
        }
        return extension;
    }

    public static void main(String[] args) {
        //try {
            // Example usage - Ensure that this path points to an actual file on your machine.
            //File photo = new File("path_to_your_photo.jpg"); // Replace with actual file path
            //String directoryPath = "uploads/profile_pics";  // Your target directory

            // Save the photo and get the UUID (filename)
            //String uuid = saveProfilePicture(photo, directoryPath);
            //System.out.println("Photo saved with UUID: " + uuid);

        //} catch (IOException e) {
        //    System.err.println("Error saving photo: " + e.getMessage());
        //}
    }
}