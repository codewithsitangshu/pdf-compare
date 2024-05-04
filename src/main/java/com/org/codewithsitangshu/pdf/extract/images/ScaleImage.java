package com.org.codewithsitangshu.pdf.extract.images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScaleImage {

    public BufferedImage scale(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return scaledImage;
    }

}
