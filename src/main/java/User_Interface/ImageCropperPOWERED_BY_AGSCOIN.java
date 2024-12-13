package User_Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageCropperPOWERED_BY_AGSCOIN {

    private JFrame frame;
    private JLabel imageLabel;
    private BufferedImage inputImage;
    private BufferedImage previewImage;
    private File inputFile;
    private JTextField gameNameField;

    public ImageCropperPOWERED_BY_AGSCOIN() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Image Cropper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel();
        JButton loadButton = new JButton("Load Image");
        JButton cropButton = new JButton("Crop and Save");
        String[] aspectRatios = {"16:9", "2:3"};
        JComboBox<String> aspectRatioBox = new JComboBox<>(aspectRatios);
        String[] outputFormats = {"JPG", "PNG"};
        JComboBox<String> outputFormatBox = new JComboBox<>(outputFormats);

        gameNameField = new JTextField(15);
        controlPanel.add(new JLabel("Game Name:"));
        controlPanel.add(gameNameField);
        controlPanel.add(loadButton);
        controlPanel.add(new JLabel("Aspect Ratio:"));
        controlPanel.add(aspectRatioBox);
        controlPanel.add(new JLabel("Output Format:"));
        controlPanel.add(outputFormatBox);
        controlPanel.add(cropButton);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        loadButton.addActionListener(e -> loadImage());
        cropButton.addActionListener(e -> cropAndSaveImage(aspectRatioBox.getSelectedItem().toString(), outputFormatBox.getSelectedItem().toString()));

        frame.setVisible(true);
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png"));

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                inputFile = fileChooser.getSelectedFile();
                inputImage = ImageIO.read(inputFile);
                previewImage = inputImage;
                updatePreview();
            } catch (Exception e) {
                showError("Failed to load image: " + e.getMessage());
            }
        }
    }

    private void updatePreview() {
        if (previewImage != null) {
            ImageIcon icon = new ImageIcon(previewImage);
            imageLabel.setIcon(icon);
        }
    }

    private void cropAndSaveImage(String aspectRatioChoice, String outputFormatChoice) {
        if (inputImage == null) {
            showError("No image loaded.");
            return;
        }

        if (gameNameField.getText().trim().isEmpty()) {
            showError("Please enter a game name.");
            return;
        }

        try {
            // Calculate new dimensions for cropping
            int cropWidth, cropHeight;
            String suffix;
            if ("16:9".equals(aspectRatioChoice)) {
                cropWidth = inputImage.getWidth();
                cropHeight = (int) (cropWidth / 16.0 * 9);
                if (cropHeight > inputImage.getHeight()) {
                    cropHeight = inputImage.getHeight();
                    cropWidth = (int) (cropHeight / 9.0 * 16);
                }
                suffix = "_Showcase";
            } else { // 2:3
                cropWidth = inputImage.getWidth();
                cropHeight = (int) (cropWidth / 2.0 * 3);
                if (cropHeight > inputImage.getHeight()) {
                    cropHeight = inputImage.getHeight();
                    cropWidth = (int) (cropHeight / 3.0 * 2);
                }
                suffix = "_Card";
            }

            // Center crop the image
            int x = (inputImage.getWidth() - cropWidth) / 2;
            int y = (inputImage.getHeight() - cropHeight) / 2;
            BufferedImage croppedImage = inputImage.getSubimage(x, y, cropWidth, cropHeight);

            // Scale the cropped image to its maximum resolution
            BufferedImage highResImage = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = highResImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(croppedImage, 0, 0, cropWidth, cropHeight, null);
            g2d.dispose();

            // Update preview
            previewImage = highResImage;
            updatePreview();

            // Save to the source directory with the appropriate naming convention
            String outputFormat = outputFormatChoice.toLowerCase();
            String baseName = gameNameField.getText().trim() + suffix;
            File outputFile = new File(inputFile.getParent(), baseName + "." + outputFormat);

            ImageIO.write(highResImage, outputFormat, outputFile);
            JOptionPane.showMessageDialog(frame, "Image cropped and saved successfully in the source directory!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageCropperPOWERED_BY_AGSCOIN::new);
    }
}
