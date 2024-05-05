package com.org.codewithsitangshu.pdf.assertion;

import com.org.codewithsitangshu.pdf.result.Difference;

import java.util.List;
import java.util.Map;

public class TextAssert implements Assertion {

    @Override
    public boolean isDifference(Map<Integer, List<Difference<Object>>> allDifference) {
        for (List<Difference<Object>> differences : allDifference.values()) {
            if (!differences.isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
