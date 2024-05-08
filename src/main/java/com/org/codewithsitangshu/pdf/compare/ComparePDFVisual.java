package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.config.Region;
import com.org.codewithsitangshu.pdf.extract.images.PDFPageAsImage;
import com.org.codewithsitangshu.pdf.extract.images.ScaleImage;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import com.org.codewithsitangshu.pdf.result.ResultFormatVisual;
import com.org.codewithsitangshu.pdf.util.AddTextToImage;
import com.org.codewithsitangshu.pdf.util.ConvertImageToBlackAndWhite;
import com.org.codewithsitangshu.pdf.util.PDFPages;
import com.org.codewithsitangshu.pdf.util.StoreImageInPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComparePDFVisual {

    private final Config config;
    private final PDDocument expectedPDF;
    private final PDDocument actualPDF;
    private final PDFRenderer expectedPDFRenderer;
    private final PDFRenderer actualPDFRenderer;
    private final PDFPages pdfPages;
    private List<Integer> pages;
    private PDFPageAsImage pdfPageAsImage;
    private ScaleImage scaleImage;
    private ResultFormatVisual resultFormatVisual;
    private StoreImageInPDF storeImageInPDF;
    private ConvertImageToBlackAndWhite convertImageToBlackAndWhite;
    private AddTextToImage addTextToImage;
    private static final Logger LOGGER = Logger.getLogger(ComparePDFVisual.class.getName());

    /**
     * Constructs a ComparePDFVisual object with the specified configuration and PDF documents.
     *
     * @param config      The configuration for comparison.
     * @param expectedPDF The expected PDF document.
     * @param actualPDF   The actual PDF document.
     */
    public ComparePDFVisual(Config config, PDDocument expectedPDF, PDDocument actualPDF) {
        this.config = config;
        this.expectedPDF = expectedPDF;
        this.actualPDF = actualPDF;
        this.pdfPages = new PDFPages();
        this.pdfPageAsImage = new PDFPageAsImage(config);
        this.scaleImage = new ScaleImage();
        this.resultFormatVisual = new ResultFormatVisual();
        this.convertImageToBlackAndWhite = new ConvertImageToBlackAndWhite();
        this.storeImageInPDF = new StoreImageInPDF(config.getSavePDFPath());
        this.addTextToImage = new AddTextToImage();
        // Create PDF renderers
        this.expectedPDFRenderer = new PDFRenderer(this.expectedPDF);
        this.actualPDFRenderer = new PDFRenderer(this.actualPDF);
    }

    /**
     * Compares the visual content of the PDF documents.
     *
     * @return The result format containing the differences.
     * @throws IOException If an I/O error occurs.
     */
    public ResultFormat compare() throws IOException {
        LOGGER.log(Level.INFO, "Starting visual comparison...");

        int pageCountExpectedPDF = pdfPages.getCount(this.expectedPDF);
        int pageCountActualPDF = pdfPages.getCount(this.actualPDF);

        this.pages = pdfPages.calculatePages(config, expectedPDF, actualPDF);

        // Compare images on each page
        this.pages.forEach(pageNumber -> {
            try {
                BufferedImage expectedImage = null;
                BufferedImage actualImage = null;

                if (pageNumber > pageCountExpectedPDF) {
                    actualImage = convertImageToBlackAndWhite.convert(this.pdfPageAsImage.setPDFRenderer(this.actualPDFRenderer)
                            .setCurrentPage(pageNumber).renderPageAsImage());
                    BufferedImage diffImage = this.addTextToImage.addText(actualImage,"Extra Page in actual pdf...");
                    this.resultFormatVisual
                            .setDifference(null, actualImage, diffImage, 100);
                } else if (pageNumber > pageCountActualPDF) {
                    expectedImage = convertImageToBlackAndWhite.convert(this.pdfPageAsImage.setPDFRenderer(this.expectedPDFRenderer)
                            .setCurrentPage(pageNumber).renderPageAsImage());
                    BufferedImage diffImage = this.addTextToImage.addText(expectedImage,"Extra Page in expected pdf...");
                    this.resultFormatVisual
                            .setDifference(expectedImage, null, diffImage, 100);
                } else {
                    expectedImage = this.pdfPageAsImage.setPDFRenderer(this.expectedPDFRenderer)
                            .setCurrentPage(pageNumber).renderPageAsImage();
                    actualImage = this.pdfPageAsImage.setPDFRenderer(this.actualPDFRenderer)
                            .setCurrentPage(pageNumber).renderPageAsImage();
                    // Scale images to the same size
                    int width = Math.max(expectedImage.getWidth(), actualImage.getWidth());
                    int height = Math.max(expectedImage.getHeight(), actualImage.getHeight());
                    BufferedImage scaledExpectedImage = this.scaleImage.scale(expectedImage, width, height);
                    BufferedImage scaledActualImage = this.scaleImage.scale(actualImage, width, height);

                    // Compare Images
                    compareImage(scaledExpectedImage, scaledActualImage, width, height, pageNumber);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error comparing images on page " + pageNumber, e);
                throw new RuntimeException(e);
            }
        });

        // Save the result image with differences marked
        this.resultFormatVisual.getDifference()
                .forEach(diff -> {
                    try {
                        this.storeImageInPDF.store((BufferedImage) diff.getDifference());
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Error storing result image in PDF", e);
                        throw new RuntimeException(e);
                    }
                });

        this.storeImageInPDF.closeResultPDF();
        LOGGER.log(Level.INFO, "Visual comparison completed.");
        return this.resultFormatVisual;
    }

    /**
     * Compares two images and stores the differences in the result format.
     *
     * @param scaledExpectedImage The scaled expected image.
     * @param scaledActualImage   The scaled actual image.
     * @param width               The width of the images.
     * @param height              The height of the images.
     * @param page                The page number
     */
    private void compareImage(BufferedImage scaledExpectedImage, BufferedImage scaledActualImage, int width, int height , int page) {
        // Compare Images
        int mismatchCount = 0;

        BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = diffImage.createGraphics();
        g.drawImage(scaledExpectedImage, 0, 0, null);

        List<Region> regionToExclude = config.getRegionsToExcludeOnSpecficPage().getOrDefault(page, 
                    config.getRegionsToExclude());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isInsideAnyRegions(x, y, regionToExclude)) {
                    // mark region on the diffImage
                    diffImage.setRGB(x, y, Color.DARK_GRAY.getRGB());
                } else {
                    int expectedRGB = scaledExpectedImage.getRGB(x, y);
                    int actualRGB = scaledActualImage.getRGB(x, y);

                    // If pixel is different, mark it in red on the diffImage
                    if (expectedRGB != actualRGB) {
                        diffImage.setRGB(x, y, Color.RED.getRGB());
                        mismatchCount++;
                    } else {
                        // If pixel is the same, set it to black or white based on the original image
                        int grayscaleValue = (expectedRGB >> 16) & 0xff; // Extract red channel
                        int newRGB = (grayscaleValue > 128) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                        diffImage.setRGB(x, y, newRGB);
                    }
                }
            }
        }

        // Calculate percentage mismatch
        double totalPixels = width * height;
        double mismatchPercentage = (mismatchCount / totalPixels) * 100;

        this.resultFormatVisual.setDifference(scaledExpectedImage, scaledActualImage, diffImage, mismatchPercentage);
    }

    private static boolean isInsideAnyRegions(int x, int y, List<Region> regions) {
        return regions.stream()
                .anyMatch(region -> region.isInsideRegion(x, y));
    }

}
