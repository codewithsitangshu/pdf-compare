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
    @Getter @Setter
    private boolean isTrimWhiteSpace = false;
    @Getter @Setter
    private String excludeString = "";
    @Getter @Setter
    private List<String> excludeList;
    @Getter @Setter
    private String regexToExclude = "";

    public Config(boolean isCompareAllPages, int startPage, int endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.isCompareAllPages = isCompareAllPages;
    }

}
