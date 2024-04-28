package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.extract.text.PDFText;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import com.org.codewithsitangshu.pdf.result.text.ComparisonResultText;
import com.org.codewithsitangshu.pdf.result.text.DifferenceText;
import com.org.codewithsitangshu.pdf.util.PDFPages;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparePDFText {

    private final Config config;
    private final PDDocument expectedPDF;
    private final PDDocument actualPDF;
    private final PDFPages pdfPages;
    private final PDFText pdfText;
    private ResultFormat resultFormat;
    private Map<Integer, List<DifferenceText<Object>>> allMismatch = new HashMap<>();


    public ComparePDFText(Config config, PDDocument expectedPDF, PDDocument actualPDF) {
        this.config = config;
        this.expectedPDF = expectedPDF;
        this.actualPDF = actualPDF;
        this.pdfPages = new PDFPages();
        this.pdfText = new PDFText(this.config);
        this.resultFormat = new ComparisonResultText();
    }

    public String compare() {

        int pageCountExpectedPDF = pdfPages.getCount(this.expectedPDF);
        int pageCountActualPDF = pdfPages.getCount(this.actualPDF);
        int maxPageCount = Math.max(pageCountExpectedPDF, pageCountActualPDF);

        int startPage = this.config.getStartPage();
        int endPage = this.config.isCompareAllPages() ? maxPageCount : this.config.getEndPage();

        int currentPage = startPage;
        StringBuilder allMismatchText = new StringBuilder();

        while (currentPage <= endPage) {
            String expectedPDFText = this.pdfText.setDocument(this.expectedPDF).extractText();
            String actualPDFText = this.pdfText.setDocument(this.actualPDF).extractText();
            List<DifferenceText<Object>> difference = this.resultFormat.compareText(expectedPDFText,actualPDFText);
            allMismatch.put(currentPage, difference);

            allMismatchText.append("Page ").append(currentPage).append(":").append("\n\n");

            for (DifferenceText<Object> mismatch : difference) {
                allMismatchText.append("Line ").append(mismatch.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(mismatch.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(mismatch.getActual()).append("\n");
            }

            currentPage++;
        }
        return allMismatchText.toString();
    }
}