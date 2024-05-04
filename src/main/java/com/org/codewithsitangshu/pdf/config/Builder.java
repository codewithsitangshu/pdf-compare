package com.org.codewithsitangshu.pdf.config;

import java.util.List;

public class Builder {

    private int startPage = 1;
    private int endPage = -1;
    private boolean isCompareAllPages = true;
    private List<Integer> specificPages = null;


    public Builder setStartPage(int startPage) {
        this.startPage = startPage;
        return this;
    }

    public Builder setEndPage(int endPage) {
        this.endPage = endPage;
        return this;
    }

    public Builder setSpecificPages(List<Integer> pages) {
        this.specificPages = pages;
        return this;
    }

    public Builder setCompareAllPages(boolean compareAllPages) {
        isCompareAllPages = compareAllPages;
        return this;
    }

    public Config build() {
        return new Config(isCompareAllPages, startPage, endPage, specificPages);
    }

}
