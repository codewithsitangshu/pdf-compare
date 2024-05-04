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
    private CompareMode compareMode = CompareMode.TEXT;

    public Compare() {
        this.config = new Builder().build();
    }

    public Compare(Config config) {
        this.config = config;
    }

    @Override
    public ResultFormat compare(PDDocument expectedPDF, PDDocument actualPDF) throws IOException {
        ResultFormat mismatch = null;
        if(compareMode == CompareMode.TEXT) {
            ComparePDFText comparePDFText = new ComparePDFText(config,expectedPDF,actualPDF);
            mismatch = comparePDFText.compare();
        } else if (compareMode == CompareMode.VISUAL) {
            ComparePDFVisual comparePDFVisual = new ComparePDFVisual(config,expectedPDF,actualPDF);
            mismatch = comparePDFVisual.compare();
        }

        return mismatch;
    }

    @Override
    public ResultFormat compare(String expectedPDFPath, String actualPDFPath) throws IOException {
        PDFLoad pdfLoad = new PDFLoad();
        PDDocument expectedPDF = pdfLoad.load(expectedPDFPath);
        PDDocument actualPDF = pdfLoad.load(actualPDFPath);
        return compare(expectedPDF,actualPDF);
    }
}
