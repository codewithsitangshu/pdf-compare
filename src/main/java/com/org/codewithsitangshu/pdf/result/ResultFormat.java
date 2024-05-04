package com.org.codewithsitangshu.pdf.result;

import java.util.List;
import java.util.Map;

public interface ResultFormat {

    List<Difference<Object>> getDifference();
    default Map<Integer,List<Difference<Object>>> getAllDifferences() {
        return Map.of();
    };

    default boolean isEmpty() {
        return getAllDifferences().isEmpty();
    }

}
