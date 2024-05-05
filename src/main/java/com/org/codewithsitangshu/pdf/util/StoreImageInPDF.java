package com.org.codewithsitangshu.pdf.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreImageInPDF {
    private static final Logger LOGGER = Logger.getLogger(StoreImageInPDF.class.getName());

    private final String path;
    private final float PDF_MARGIN = 50f;
    private PDDocument document;

    /**
     * Constructs a StoreImageInPDF object with the specified file path.
     *
     * @param path The file path to save the PDF.
     */
    public StoreImageInPDF(String path) {
        this.path = path;
        // Delete the existing PDF file if it exists
        File existingPdfFile = new File(path);
        if (existingPdfFile.exists()) {
            existingPdfFile.delete();
        }
        // Create a new PDF document
        try {
            document = new PDDocument();
            document.save(path);
            document = Loader.loadPDF(new File(this.path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stores the given image in the PDF.
     *
     * @param image The image to store.
     * @throws IOException If an I/O error occurs while storing the image.
     */
    public void store(BufferedImage image) throws IOException {
        LOGGER.log(Level.INFO, "Storing image in PDF...");
        PDPage page = new PDPage();
        document.addPage(page);

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, toByteArray(image), "image");

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            float pageWidth = page.getMediaBox().getWidth() - PDF_MARGIN * 2;
            float pageHeight = page.getMediaBox().getHeight() - PDF_MARGIN * 2;
            float imageWidth = image.getWidth();
            float imageHeight = image.getHeight();

            float scalingFactor = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);

            float scaledWidth = imageWidth * scalingFactor;
            float scaledHeight = imageHeight * scalingFactor;

            float xPosition = (pageWidth - scaledWidth) / 2 + PDF_MARGIN;
            float yPosition = (pageHeight - scaledHeight) / 2 + PDF_MARGIN;

            contentStream.drawImage(pdImage, xPosition, yPosition, scaledWidth, scaledHeight);
        }

        document.save(path);
        LOGGER.log(Level.INFO, "Image stored in PDF successfully.");
    }

    /**
     * Closes the PDF document.
     */
    public void closeResultPDF() {
        // Close PDF documents after each test
        if (document != null) {
            try {
                document.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
