package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.extract.text.PDFText;
import com.org.codewithsitangshu.pdf.result.Difference;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import com.org.codewithsitangshu.pdf.result.ResultFormatText;
import com.org.codewithsitangshu.pdf.util.PDFPages;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComparePDFText {

    private final Config config;
    private final PDDocument expectedPDF;
    private final PDDocument actualPDF;
    private final PDFPages pdfPages;
    private final PDFText pdfText;
    private ResultFormatText resultFormatText;
    private List<Integer> pages;
    private static final Logger LOGGER = Logger.getLogger(ComparePDFText.class.getName());

    /**
     * Constructs a ComparePDFText object with the specified configuration and PDF documents.
     *
     * @param config      The configuration for comparison.
     * @param expectedPDF The expected PDF document.
     * @param actualPDF   The actual PDF document.
     */
    public ComparePDFText(Config config, PDDocument expectedPDF, PDDocument actualPDF) {
        this.config = config;
        this.expectedPDF = expectedPDF;
        this.actualPDF = actualPDF;
        this.pdfPages = new PDFPages();
        this.pdfText = new PDFText(this.config);
        this.resultFormatText = new ResultFormatText();
    }

    /**
     * Compares the text content of the PDF documents.
     *
     * @return The result format containing the differences.
     */
    public ResultFormat compare() {
        LOGGER.log(Level.INFO, "Starting text comparison...");

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

        // Compare text on each page
        this.pages.forEach(pageNumber -> {
            try {
                String expectedPDFText = this.pdfText.setDocument(this.expectedPDF)
                        .setCurrentPage(pageNumber)
                        .extractText();
                String actualPDFText = this.pdfText.setDocument(this.actualPDF)
                        .setCurrentPage(pageNumber)
                        .extractText();
                boolean compareFlag = compareText(expectedPDFText, actualPDFText);
                if (compareFlag) {
                    List<Difference<Object>> difference = new ArrayList<>();
                    difference.addAll(this.resultFormatText.getDifference());
                    this.resultFormatText.setAllDifference(pageNumber, difference);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error comparing text on page " + pageNumber, e);
            }
        });

        LOGGER.log(Level.INFO, "Text comparison completed.");
        return this.resultFormatText;
    }

    /**
     * Compares the text content of two strings.
     *
     * @param expectedText The expected text.
     * @param actualText   The actual text.
     * @return True if there are differences, otherwise false.
     */
    private boolean compareText(String expectedText, String actualText) {
        // Split text into lines
        String[] expected = expectedText.split("\n");
        String[] actual = actualText.split("\n");
        int maxLength = Math.max(expected.length, actual.length);
        boolean compareFlag = false;

        // Compare each line of text
        for (int index = 0; index < maxLength; index++) {
            String lineExpected = (index < expected.length) ? expected[index] : "";
            String lineActual = (index < actual.length) ? actual[index] : "";
            if (!lineExpected.equals(lineActual)) {
                this.resultFormatText.setDifference(lineExpected, lineActual, index + 1);
                compareFlag = true;
            }
        }
        return compareFlag;
    }

    /**
     * Extracts page numbers between startPage and endPage inclusively.
     *
     * @param startPage The starting page number.
     * @param endPage   The ending page number.
     * @return The list of extracted page numbers.
     */
    private List<Integer> extractPageNumbers(int startPage, int endPage) {
        List<Integer> pages = new ArrayList<>();
        for (int page = startPage; page <= endPage; page++) {
            pages.add(page);
        }
        return pages;
    }
}
