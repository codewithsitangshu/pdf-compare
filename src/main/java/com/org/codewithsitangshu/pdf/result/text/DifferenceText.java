package com.org.codewithsitangshu.pdf.result.text;

import lombok.Getter;

import java.util.Objects;

public class DifferenceText<T> {

    @Getter
    private final T expected;
    @Getter
    private final T actual;
    @Getter
    private final int lineNumber;

    public static <T> DifferenceText<T> of(T expected, T actual, int lineNumber) {
        return new DifferenceText<>(expected, actual, lineNumber);
    }

    private DifferenceText(T expected, T actual, int lineNumber) {
        this.expected = expected;
        this.actual = actual;
        this.lineNumber = lineNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expected, actual, lineNumber);
    }

}
