package com.org.codewithsitangshu.pdf.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PDFLoad {

    public PDDocument load(String pdfPath) throws IOException {
        return Loader.loadPDF(new File(pdfPath));
    }

}
