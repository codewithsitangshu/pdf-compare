package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.extract.images.PDFPageAsImage;
import com.org.codewithsitangshu.pdf.extract.images.ScaleImage;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import com.org.codewithsitangshu.pdf.result.ResultFormatVisual;
import com.org.codewithsitangshu.pdf.util.ConvertImageToBlackAndWhite;
import com.org.codewithsitangshu.pdf.util.PDFPages;
import com.org.codewithsitangshu.pdf.util.StoreImageInPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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

        // Determine pages to compare based on configuration
        if (this.config.isCompareAllPages()) {
            int startPage = this.config.getStartPage();
            int endPage = Math.min(pageCountExpectedPDF, pageCountActualPDF);
            this.pages = extractPageNumbers(startPage, endPage);
        } else if (this.config.getSpecificPages() != null) {
            this.pages = this.config.getSpecificPages();
        } else {
            int startPage = this.config.getStartPage();
            int endPage = this.config.getEndPage();
            this.pages = extractPageNumbers(startPage, endPage);
        }

        // Compare images on each page
        this.pages.forEach(page -> {
            try {
                BufferedImage expectedImage = this.pdfPageAsImage.setPDFRenderer(this.expectedPDFRenderer)
                        .setCurrentPage(page)
                        .renderPageAsImage();
                BufferedImage actualImage = this.pdfPageAsImage.setPDFRenderer(this.actualPDFRenderer)
                        .setCurrentPage(page)
                        .renderPageAsImage();

                // Scale images to the same size
                int width = Math.max(expectedImage.getWidth(), actualImage.getWidth());
                int height = Math.max(expectedImage.getHeight(), actualImage.getHeight());
                BufferedImage scaledExpectedImage = this.scaleImage.scale(expectedImage, width, height);
                BufferedImage scaledActualImage = this.scaleImage.scale(actualImage, width, height);

                // Compare Images
                compareImage(scaledExpectedImage, scaledActualImage, width, height);

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error comparing images on page " + page, e);
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
     */
    private void compareImage(BufferedImage scaledExpectedImage, BufferedImage scaledActualImage, int width, int height) {
        // Compare Images
        int mismatchCount = 0;

        BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = diffImage.createGraphics();
        g.drawImage(scaledExpectedImage, 0, 0, null);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (scaledExpectedImage.getRGB(x, y) != scaledActualImage.getRGB(x, y)) {
                    // If pixel is different, mark it in red on the diffImage
                    diffImage.setRGB(x, y, Color.RED.getRGB());
                    mismatchCount++;
                }
            }
        }

        // Calculate percentage mismatch
        double totalPixels = width * height;
        double mismatchPercentage = (mismatchCount / totalPixels) * 100;

        // Convert image to black and white if mismatch percentage is within threshold
        if (mismatchPercentage <= config.getThreshold()) {
            diffImage = this.convertImageToBlackAndWhite.convert(scaledExpectedImage);
        }

        this.resultFormatVisual.setDifference(scaledExpectedImage, scaledActualImage, diffImage, mismatchPercentage);
    }

    /**
     * Extracts the page numbers to be compared.
     *
     * @param startPage The starting page number.
     * @param endPage   The ending page number.
     * @return The list of page numbers.
     */
    private List<Integer> extractPageNumbers(int startPage, int endPage) {
        List<Integer> pages = new ArrayList<>();
        for (int page = startPage; page <= endPage; page++) {
            pages.add(page);
        }
        return pages;
    }
}
