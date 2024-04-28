package com.org.codewithsitangshu.pdf.util;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFPages {

    public int getCount(PDDocument pdf) {
        return pdf.getNumberOfPages();
    }

}
