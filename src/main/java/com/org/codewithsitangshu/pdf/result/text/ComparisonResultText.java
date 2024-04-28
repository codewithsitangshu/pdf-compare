package com.org.codewithsitangshu.pdf.result.text;

import com.org.codewithsitangshu.pdf.result.ResultFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparisonResultText implements ResultFormat {

    private List<DifferenceText<Object>> mismatch = new ArrayList<>();

    @Override
    public List<DifferenceText<Object>> compareText(String expectedText, String actualText) {

        String[] expected = expectedText.split("\n");
        String[] actual = actualText.split("\n");
        int maxLength = Math.max(expected.length, actual.length);
        mismatch.clear();

        for (int index = 0; index < maxLength; index++) {
            String lineExpected = (index < expected.length) ? expected[index] : "";
            String lineActual = (index < actual.length) ? actual[index] : "";
            if (!lineExpected.equals(lineActual)) {
                mismatch.add(DifferenceText.of(lineExpected,lineActual,index+1));

            }
        }
        return mismatch;
    }
}
