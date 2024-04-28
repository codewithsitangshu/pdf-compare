package com.org.codewithsitangshu.pdf.compare;

import com.org.codewithsitangshu.pdf.result.ResultFormat;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public interface Comparator {

    ResultFormat compare(PDDocument expectedPDF, PDDocument actualPDF) throws IOException;

    ResultFormat compare(String expectedPDFPath, String actualPDFPath) throws IOException;

}
