package com.org.codewithsitangshu.pdf.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFLoad {
    private static final Logger LOGGER = Logger.getLogger(PDFLoad.class.getName());

    /**
     * Loads a PDF document from the specified file path.
     *
     * @param pdfPath The file path of the PDF document.
     * @return The loaded PDDocument object.
     * @throws IOException If an I/O error occurs while loading the PDF document.
     */
    public PDDocument load(String pdfPath) throws IOException {
        LOGGER.log(Level.INFO, "Loading PDF document from path: " + pdfPath);
        PDDocument document = Loader.loadPDF(new File(pdfPath));
        LOGGER.log(Level.INFO, "PDF document loaded successfully.");
        return document;
    }
}
