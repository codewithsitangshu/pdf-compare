package com.org.codewithsitangshu.pdf.extract.images;

import com.org.codewithsitangshu.pdf.config.Config;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class PDFPageAsImage {

    private final Config config;
    private int currentPage = 1;
    private PDFRenderer renderer;

    private PDFPageAsImage() {
        this.config = null;
    }

    public PDFPageAsImage(Config config) {
        this.config = config;
    }

    public PDFPageAsImage setPDFRenderer(PDFRenderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public PDFPageAsImage setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }


    public BufferedImage renderPageAsImage() throws IOException {
        return renderer.renderImageWithDPI(currentPage-1, config.getDpi());
    }


}
