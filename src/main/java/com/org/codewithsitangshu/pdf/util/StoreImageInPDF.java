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

public class StoreImageInPDF {

    private final String path;
    private final float PDF_MARGIN = 50f;
    private final PDDocument document;

    public StoreImageInPDF(String path) {
        this.path = path;
        // Delete the existing PDF file if it exists
        File existingPdfFile = new File(path);
        if (existingPdfFile.exists()) {
            existingPdfFile.delete();
        }
        // Create a new PDF document
        try (PDDocument document = new PDDocument()) {
            document.save(path);
            this.document = Loader.loadPDF(new File(this.path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void store(BufferedImage image) throws IOException {

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
    }

    private byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}

