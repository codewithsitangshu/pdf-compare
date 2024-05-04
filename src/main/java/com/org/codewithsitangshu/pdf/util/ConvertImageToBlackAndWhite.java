package com.org.codewithsitangshu.pdf.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ConvertImageToBlackAndWhite {

    public BufferedImage convert(BufferedImage image) {
        BufferedImage bwImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = bwImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bwImage;
    }

}
