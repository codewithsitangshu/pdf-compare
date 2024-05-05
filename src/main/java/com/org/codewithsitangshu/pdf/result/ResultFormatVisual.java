package com.org.codewithsitangshu.pdf.result;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultFormatVisual implements ResultFormat {
    private static final Logger LOGGER = Logger.getLogger(ResultFormatVisual.class.getName());

    private List<Difference<Object>> difference = new ArrayList<>();

    /**
     * Sets the visual difference between expected and actual images.
     *
     * @param expectedImage     The expected image.
     * @param actualImage       The actual image.
     * @param differenceImage   The image highlighting the differences.
     * @param mismatchPercentage The percentage of mismatch between the images.
     */
    public void setDifference(BufferedImage expectedImage, BufferedImage actualImage,
                              BufferedImage differenceImage, double mismatchPercentage) {
        LOGGER.log(Level.INFO, "Setting visual difference with a mismatch percentage of: " + mismatchPercentage);
        this.difference.add(Difference.of(expectedImage, actualImage, differenceImage, mismatchPercentage));
    }

    @Override
    public List<Difference<Object>> getDifference() {
        return this.difference;
    }
}