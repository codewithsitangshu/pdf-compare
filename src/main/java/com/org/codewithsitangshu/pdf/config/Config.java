package com.org.codewithsitangshu.pdf.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Config {

    @Getter
    private final int startPage;
    @Getter
    private final int endPage;
    @Getter
    private final boolean isCompareAllPages;
    @Getter
    private final List<Integer> specificPages;
    @Getter @Setter
    private boolean isTrimWhiteSpace = false;
    @Getter @Setter
    private String excludeString = "";
    @Getter @Setter
    private List<String> excludeList;
    @Getter @Setter
    private String regexToExclude = "";
    @Getter @Setter
    private int dpi = 300;
    @Getter @Setter
    private double threshold = 0;
    @Getter @Setter
    private String savePDFPath = "Result.pdf";

    public Config(boolean isCompareAllPages, int startPage, int endPage, List<Integer> specificPages) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.isCompareAllPages = isCompareAllPages;
        this.specificPages = specificPages;
    }

}
