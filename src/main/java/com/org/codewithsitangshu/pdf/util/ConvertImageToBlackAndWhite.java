package com.org.codewithsitangshu.pdf.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConvertImageToBlackAndWhite {
    private static final Logger LOGGER = Logger.getLogger(ConvertImageToBlackAndWhite.class.getName());

    /**
     * Converts the given image to black and white.
     *
     * @param image The image to convert.
     * @return The black and white version of the image.
     */
    public BufferedImage convert(BufferedImage image) {
        LOGGER.log(Level.INFO, "Converting image to black and white...");
        BufferedImage bwImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = bwImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        LOGGER.log(Level.INFO, "Image converted to black and white successfully.");
        return bwImage;
    }
}
