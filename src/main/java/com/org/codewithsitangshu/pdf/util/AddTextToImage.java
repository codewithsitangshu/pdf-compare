package com.org.codewithsitangshu.pdf.util;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

public class AddTextToImage {

    public BufferedImage addText(BufferedImage image, String text) {

        BufferedImage originalImage = image;

        // Create a new BufferedImage with the same dimensions and type as the original image
        BufferedImage modifiedImage = new BufferedImage(
                originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Get the graphics object of the modified image
        Graphics2D g = modifiedImage.createGraphics();

        // Draw the original image onto the modified image
        g.drawImage(originalImage, 0, 0, null);

        // Add the text "Extra" in bold in the middle of the image
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
        return modifiedImage;
    }

}
