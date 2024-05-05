package com.org.codewithsitangshu.pdf.util;

import org.apache.pdfbox.pdmodel.PDDocument;
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
}
