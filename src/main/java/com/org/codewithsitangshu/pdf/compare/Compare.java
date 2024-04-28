package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.config.Builder;
import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import com.org.codewithsitangshu.pdf.util.PDFLoad;
import lombok.Setter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class Compare implements Comparator {

    private final Config config;
    @Setter
    private CompareMode compareMode;

    public Compare() {
        this.config = new Builder().build();
    }

    public Compare(Builder builder) {
        this.config = builder.build();
    }

    @Override
    public ResultFormat compare(PDDocument expectedPDF, PDDocument actualPDF) throws IOException {

        if(compareMode == CompareMode.TEXT) {
            ComparePDFText comparePDFText = new ComparePDFText(config,expectedPDF,actualPDF);
            String mismatch = comparePDFText.compare();
            System.out.println(mismatch);
        }

        return null;
    }

    @Override
    public ResultFormat compare(String expectedPDFPath, String actualPDFPath) throws IOException {
        PDFLoad pdfLoad = new PDFLoad();
        PDDocument expectedPDF = pdfLoad.load(expectedPDFPath);
        PDDocument actualPDF = pdfLoad.load(actualPDFPath);
        return compare(expectedPDF,actualPDF);
    }
}
