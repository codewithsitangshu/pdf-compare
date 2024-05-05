package com.org.codewithsitangshu.pdf.extract.images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScaleImage {
    private static final Logger LOGGER = Logger.getLogger(ScaleImage.class.getName());

    /**
     * Scales the given image to the specified width and height.
     *
     * @param image  The image to scale.
     * @param width  The width to scale the image to.
     * @param height The height to scale the image to.
     * @return The scaled image.
     */
    public BufferedImage scale(BufferedImage image, int width, int height) {
        LOGGER.log(Level.INFO, "Scaling image to width: " + width + ", height: " + height + "...");
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        LOGGER.log(Level.INFO, "Image scaled successfully.");
        return scaledImage;
    }
}