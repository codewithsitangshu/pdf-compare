package com.org.codewithsitangshu.pdf.extract.text;

import com.org.codewithsitangshu.pdf.config.Config;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.util.List;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PDFText {

    private PDFTextStripper stripper;
    private PDDocument document;
    private final Config config;
    private int currentPage = 1;

    public PDFText(Config config) {
        this.config = config;
    }

    private PDFText() {
        this.config = null;
    }

    public PDFText setDocument(PDDocument document) {
        this.document = document;
        return this;
    }

    public PDFText setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public String extractText() {

        setPDFTextStripper();

        String pdfText = getText(this.currentPage,
                this.config.isTrimWhiteSpace());

        if (!config.getExcludeString().isEmpty()) {
            pdfText = pdfText.replaceAll(config.getExcludeString(), "");
        }

        if (config.getExcludeList() != null) {
            for (String text : config.getExcludeList()) {
                pdfText = pdfText.replaceAll(text, "");
            }
        }

        if (!config.getRegexToExclude().isEmpty()) {
            // Compile the regex pattern
            Pattern pattern = Pattern.compile(config.getRegexToExclude());
            // Create a matcher with the input text
            Matcher matcher = pattern.matcher(pdfText);
            // Replace all matches with an empty string to exclude them
            pdfText = matcher.replaceAll("");
        }

        return pdfText;
    }

    private PDFText setPDFTextStripper() {
        try {
            if (!this.document.isEncrypted()) {
                this.stripper = new PDFTextStripper();
                return this;
            } else {
                throw new IOException("PDF document is encrypted, cannot extract text.");
            }
        } catch (IOException e) {
            System.err.println("Error setting PDFTextStripper: " + e.getMessage());
            return null; // Or handle the error as needed
        }
    }

    private PDFText setStartPage(int startPage) {
        this.stripper.setStartPage(startPage);
        return this;
    }

    private PDFText setEndPage(int endPage) {
        this.stripper.setEndPage(endPage);
        return this;
    }

    private String getText(int pageNumber, boolean isTrimWhiteSpace) {
        String pdfText = "";
        setStartPage(pageNumber)
                .setEndPage(pageNumber);

        try {
            pdfText = this.stripper.getText(this.document);
        } catch (IOException e) {
            System.err.println("Error extracting text for page " + pageNumber + ": " + e.getMessage());
            return pdfText; // Return empty string or handle the error as needed
        }
        if (isTrimWhiteSpace) {
            pdfText = pdfText.trim().replaceAll("\\s+", " ").trim();
        }
        return pdfText;

    }

}
