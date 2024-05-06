package com.org.codewithsitangshu.pdf.assertion;

import com.org.codewithsitangshu.pdf.result.Difference;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import org.assertj.core.api.AbstractAssert;

import java.util.List;
import java.util.Map;

public class Assertion extends AbstractAssert<Assertion, ResultFormat> {

    private Assertion(ResultFormat resultFormat) {
        super(resultFormat, Assertion.class);
    }

    public static Assertion assertThat(ResultFormat resultFormat){
        return new Assertion(resultFormat);
    }

    public boolean hasTextMismatch(){
        isNotNull();
        Map<Integer, List<Difference<Object>>> allDifference = actual.getAllDifferences();
        for (List<Difference<Object>> differences : allDifference.values()) {
            if (!differences.isEmpty()) {
                failWithMessage("Expected text is not matched with actual text");
                return true;
            }
        }
        return false;
    }

    public boolean hasVisualMismatch() {
        isNotNull();
        List<Difference<Object>> differences = actual.getDifference();
        if(!differences.stream()
                .noneMatch(mismatch -> mismatch.getMismatchPercentage() > 0)) {
            failWithMessage("PDF visual compare is failed. Please see the result pdf");
            return true;
        }
        return false;
    }

    public boolean hasVisualMismatch(double threshold) {
        isNotNull();
        List<Difference<Object>> differences = actual.getDifference();
        if(!differences.stream()
                .noneMatch(mismatch -> mismatch.getMismatchPercentage() > threshold)) {
            failWithMessage("PDF visual compare mismatch is more than threshold value <%s>. Please see the result pdf", threshold);
            return true;
        }
        return false;
    }



}
