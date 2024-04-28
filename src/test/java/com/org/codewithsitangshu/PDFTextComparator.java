package com.org.codewithsitangshu;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFTextComparator {

    public static void main(String[] args) {

        String pdf1Path = "src/test/resources/text-compare/sample1.pdf";
        String pdf2Path = "src/test/resources/text-compare/sample2.pdf";

        try {
            String text1 = extractTextFromPDF(pdf1Path);
            String text2 = extractTextFromPDF(pdf2Path);

            String diff = compareText(text1, text2);
            System.out.println("Text differences between the two PDFs:");
            System.out.println(diff);
        } catch (IOException e) {
            System.err.println("An error occurred while comparing PDFs: " + e.getMessage());
        }
    }

    private static String extractTextFromPDF(String pdfPath) throws IOException {
        try (PDDocument document = Loader.loadPDF(new File(pdfPath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private static String compareText(String text1, String text2) {
        StringBuilder diff = new StringBuilder();

        String[] lines1 = text1.split("\n");
        String[] lines2 = text2.split("\n");

        int maxLength = Math.max(lines1.length, lines2.length);

        for (int i = 0; i < maxLength; i++) {
            String line1 = (i < lines1.length) ? lines1[i] : "";
            String line2 = (i < lines2.length) ? lines2[i] : "";

            if (!line1.equals(line2)) {
                diff.append("Line ").append(i + 1).append(":").append("\n");
                diff.append("Expected: ").append(line1).append("\n");
                diff.append("Actual  : ").append(line2).append("\n");
                diff.append("\n");
            }
        }

        return diff.toString();
    }
}

