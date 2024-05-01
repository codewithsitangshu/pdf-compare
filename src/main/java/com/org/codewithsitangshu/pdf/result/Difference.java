package com.org.codewithsitangshu.pdf.result;

import lombok.Getter;

import java.util.Objects;

public class Difference<T> {

    @Getter
    private final T expected;
    @Getter
    private final T actual;
    @Getter
    private final int lineNumber;

    public static <T> Difference<T> of(T expected, T actual, int lineNumber) {
        return new Difference<>(expected, actual, lineNumber);
    }

    private Difference(T expected, T actual, int lineNumber) {
        this.expected = expected;
        this.actual = actual;
        this.lineNumber = lineNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expected, actual, lineNumber);
    }

}
