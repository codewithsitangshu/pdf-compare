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

public class ComparePDFText {

    private final Config config;
    private final PDDocument expectedPDF;
    private final PDDocument actualPDF;
    private final PDFPages pdfPages;
    private final PDFText pdfText;
    private ResultFormatText resultFormatText;
    private List<Integer> pages;


    public ComparePDFText(Config config, PDDocument expectedPDF, PDDocument actualPDF) {
        this.config = config;
        this.expectedPDF = expectedPDF;
        this.actualPDF = actualPDF;
        this.pdfPages = new PDFPages();
        this.pdfText = new PDFText(this.config);
        this.resultFormatText = new ResultFormatText();
    }

    public ResultFormat compare() {

        int pageCountExpectedPDF = pdfPages.getCount(this.expectedPDF);
        int pageCountActualPDF = pdfPages.getCount(this.actualPDF);

        if(this.config.isCompareAllPages()) {
            int startPage = this.config.getStartPage();
            int endPage = Math.min(pageCountExpectedPDF, pageCountActualPDF);
            this.pages = extractPageNumbers(startPage,endPage);
        } else if (this.config.getSpecificPages() != null) {
            this.pages = this.config.getSpecificPages();
        } else {
            int startPage = this.config.getStartPage();
            int endPage = this.config.getEndPage();
            this.pages = extractPageNumbers(startPage,endPage);
        }

        this.pages.forEach(pageNumber -> {
            String expectedPDFText = this.pdfText.setDocument(this.expectedPDF)
                    .setCurrentPage(pageNumber)
                    .extractText();
            String actualPDFText = this.pdfText.setDocument(this.actualPDF)
                    .setCurrentPage(pageNumber)
                    .extractText();
            boolean compareFlag = compareText(expectedPDFText,actualPDFText);
            if(compareFlag) {
                List<Difference<Object>> difference = new ArrayList<>();
                difference.addAll(this.resultFormatText.getDifference());
                this.resultFormatText.setAllDifference(pageNumber,difference);
            }
        });

        return this.resultFormatText;
    }

    private boolean compareText(String expectedText, String actualText) {
        String[] expected = expectedText.split("\n");
        String[] actual = actualText.split("\n");
        int maxLength = Math.max(expected.length, actual.length);
        boolean compareFlag = false;

        for (int index = 0; index < maxLength; index++) {
            String lineExpected = (index < expected.length) ? expected[index] : "";
            String lineActual = (index < actual.length) ? actual[index] : "";
            if (!lineExpected.equals(lineActual)) {
                this.resultFormatText.setDifference(lineExpected,lineActual,index+1);
                compareFlag = true;
            }
        }
        return compareFlag;
    }

    private List<Integer> extractPageNumbers(int startPage, int endPage) {
        List<Integer> pages = new ArrayList<>();
        for (int page = startPage; page <= endPage; page++) {
            pages.add(page);
        }
        return pages;
    }
}