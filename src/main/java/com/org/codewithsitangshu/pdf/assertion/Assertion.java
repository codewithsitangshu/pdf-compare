package com.org.codewithsitangshu.pdf.assertion;

import com.org.codewithsitangshu.pdf.result.Difference;

import java.util.List;
import java.util.Map;

public interface Assertion {

    default boolean isDifference(Map<Integer, List<Difference<Object>>> allDifference) {
        return false;
    };



}
