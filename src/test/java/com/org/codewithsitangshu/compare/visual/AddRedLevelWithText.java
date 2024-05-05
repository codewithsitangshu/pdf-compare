package com.org.codewithsitangshu.compare.visual;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddRedLevelWithText {

    public static void main(String[] args) {
        // Load the original image
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File("src/test/resources/image-compare/pexels-jhawley-57905.jpg"));
        } catch (IOException e) {
            System.err.println("Error loading the image: " + e.getMessage());
            return;
        }

        // Create a new BufferedImage with the same dimensions and type as the original image
        BufferedImage modifiedImage = new BufferedImage(
                originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Get the graphics object of the modified image
        Graphics2D g = modifiedImage.createGraphics();

        // Draw the original image onto the modified image
        g.drawImage(originalImage, 0, 0, null);

        // Add the text "Extra" in bold in the middle of the image
        String text = "Extra";
        g.setColor(Color.RED);
        Font font = new Font("Arial", Font.BOLD, 100); // Adjust the font as needed
        g.setFont(font);

        // Get the size of the text
        FontRenderContext frc = g.getFontRenderContext();
        TextLayout layout = new TextLayout(text, font, frc);
        Rectangle bounds = layout.getBounds().getBounds();

        // Draw the text in the middle of the image
        int x = (modifiedImage.getWidth() - bounds.width) / 2;
        int y = (modifiedImage.getHeight() + bounds.height) / 2;
        g.drawString(text, x, y);

        // Dispose of the graphics object
        g.dispose();

        // Save the modified image
        try {
            ImageIO.write(modifiedImage, "jpg", new File("modified_image.jpg"));
            System.out.println("Image saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }
}

