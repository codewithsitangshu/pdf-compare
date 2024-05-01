package com.org.codewithsitangshu.pdf.config;


import java.util.List;

public class Builder {

    private int startPage = 1;
    private int endPage = -1;
    private boolean isCompareAllPages = true;
    private Config config;


    public Builder setStartPage(int startPage) {
        this.startPage = startPage;
        return this;
    }

    public Builder setEndPage(int endPage) {
        this.endPage = endPage;
        return this;
    }

    public Builder setCompareAllPages(boolean compareAllPages) {
        isCompareAllPages = compareAllPages;
        return this;
    }

    public Config build() {
        this.config = new Config(isCompareAllPages, startPage, endPage);
        return this.config;
    }

}
