package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.config.Builder;
import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import com.org.codewithsitangshu.pdf.util.PDFLoad;
import lombok.Setter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Compare implements Comparator {

    private final Config config;
    private static final Logger LOGGER = Logger.getLogger(Compare.class.getName());

    @Setter
    private CompareMode compareMode = CompareMode.TEXT;

    // Default constructor
    public Compare() {
        this.config = new Builder().build();
    }

    // Constructor with custom configuration
    public Compare(Config config) {
        this.config = config;
    }

    // Compare PDF documents
    @Override
    public ResultFormat compare(PDDocument expectedPDF, PDDocument actualPDF) throws IOException {
        ResultFormat mismatch = null;

        // Perform comparison based on the selected mode
        if (compareMode == CompareMode.TEXT) {
            ComparePDFText comparePDFText = new ComparePDFText(config, expectedPDF, actualPDF);
            mismatch = comparePDFText.compare();
        } else if (compareMode == CompareMode.VISUAL) {
            ComparePDFVisual comparePDFVisual = new ComparePDFVisual(config, expectedPDF, actualPDF);
            mismatch = comparePDFVisual.compare();
        }

        // Close PDF documents after comparison
        expectedPDF.close();
        actualPDF.close();

        LOGGER.log(Level.INFO, "Comparison completed");
        return mismatch;
    }

    // Compare PDF documents given their file paths
    @Override
    public ResultFormat compare(String expectedPDFPath, String actualPDFPath) throws IOException {
        PDFLoad pdfLoad = new PDFLoad();
        PDDocument expectedPDF = pdfLoad.load(expectedPDFPath);
        PDDocument actualPDF = pdfLoad.load(actualPDFPath);
        return compare(expectedPDF, actualPDF);
    }
}