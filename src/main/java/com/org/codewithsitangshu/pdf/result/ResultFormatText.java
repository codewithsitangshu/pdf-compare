package com.org.codewithsitangshu.pdf.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultFormatText implements ResultFormat {

    private List<Difference<Object>> difference = new ArrayList<>();
    private Map<Integer,List<Difference<Object>>> allDifference = new LinkedHashMap<>();

    public void setDifference(String expectedText, String actualText, int lineNumber) {
        this.difference.add(Difference.of(expectedText,actualText,lineNumber));
    }

    public void setAllDifference(int pageNumber,List<Difference<Object>> diff) {
        this.allDifference.put(pageNumber,diff);
        this.difference.clear();
    }


    @Override
    public List<Difference<Object>> getDifference() {
        return this.difference;
    }

    @Override
    public Map<Integer, List<Difference<Object>>> getAllDifferences() {
        return this.allDifference;
    }

}
