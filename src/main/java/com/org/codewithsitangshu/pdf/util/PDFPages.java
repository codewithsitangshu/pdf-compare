package com.org.codewithsitangshu.pdf.util;

import com.org.codewithsitangshu.pdf.config.Config;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFPages {
    private static final Logger LOGGER = Logger.getLogger(PDFPages.class.getName());

    /**
     * Gets the total number of pages in the given PDF document.
     *
     * @param pdf The PDF document.
     * @return The total number of pages in the document.
     */
    public int getCount(PDDocument pdf) {
        int pageCount = pdf.getNumberOfPages();
        LOGGER.log(Level.INFO, "Total number of pages in PDF document: " + pageCount);
        return pageCount;
    }

    /**
     * Calculate pages
     *
     * @param config
     * @param expectedPDF
     * @param actualPDF
     * @return
     */
    public List<Integer> calculatePages(Config config, PDDocument expectedPDF, PDDocument actualPDF) {
        int pageCountExpectedPDF = getCount(expectedPDF);
        int pageCountActualPDF = getCount(actualPDF);
        int maxPageCount = Math.max(pageCountExpectedPDF, pageCountActualPDF);
        List<Integer> pages = new ArrayList<>();

        // Determine pages to compare based on configuration

        if (config.isCompareAllPages()) {
            int startPage = config.getStartPage();
            int endPage = maxPageCount;
            for (int page = startPage; page <= endPage; page++) {
                pages.add(page);
            }
        } else if (config.getSpecificPages() != null) {
            pages = config.getSpecificPages();
            for (Integer specificPage : pages) {
                if (specificPage > maxPageCount) {
                    throw new IllegalArgumentException(specificPage + " page exceeds maximum page count");
                }
            }
        } else {
            int startPage = config.getStartPage();
            int endPage = config.getEndPage();
            if (endPage > maxPageCount) {
                throw new IllegalArgumentException("End page exceeds maximum page count");
            }
            for (int page = startPage; page <= endPage; page++) {
                pages.add(page);
            }
        }
        return pages;
    }


}
