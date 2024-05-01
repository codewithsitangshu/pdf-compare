package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.extract.text.PDFText;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import com.org.codewithsitangshu.pdf.result.ResultFormatText;
import com.org.codewithsitangshu.pdf.result.Difference;
import com.org.codewithsitangshu.pdf.util.PDFPages;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparePDFText {

    private final Config config;
    private final PDDocument expectedPDF;
    private final PDDocument actualPDF;
    private final PDFPages pdfPages;
    private final PDFText pdfText;
    private ResultFormatText resultFormatText;
    private Map<Integer, List<Difference<Object>>> allMismatch = new HashMap<>();


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
        int maxPageCount = Math.max(pageCountExpectedPDF, pageCountActualPDF);

        int startPage = this.config.getStartPage();
        int endPage = this.config.isCompareAllPages() ? maxPageCount : this.config.getEndPage();

        int currentPage = startPage;
        StringBuilder allMismatchText = new StringBuilder();

        while (currentPage <= endPage) {
            String expectedPDFText = this.pdfText.setDocument(this.expectedPDF)
                    .setCurrentPage(currentPage)
                    .extractText();
            String actualPDFText = this.pdfText.setDocument(this.actualPDF)
                    .setCurrentPage(currentPage)
                    .extractText();
            boolean compareFlag = compareText(expectedPDFText,actualPDFText);
            if(compareFlag) {
                List<Difference<Object>> difference = new ArrayList<>();
                difference.addAll(this.resultFormatText.getDifference());
                this.resultFormatText.setAllDifference(currentPage,difference);
            }

            currentPage++;
        }
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
}