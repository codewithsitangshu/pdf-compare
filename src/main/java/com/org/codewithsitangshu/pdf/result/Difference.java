package com.org.codewithsitangshu.pdf.result;

import lombok.Getter;

import java.util.Objects;

public class Difference<T> {

    @Getter
    private final T expected;
    @Getter
    private final T actual;
    @Getter
    private T difference;
    @Getter
    private double mismatchPercentage;
    @Getter
    private int lineNumber;

    public static <T> Difference<T> of(T expected, T actual, int lineNumber) {
        return new Difference<>(expected, actual, lineNumber);
    }

    public static <T> Difference<T> of(T expected, T actual, T difference, double mismatchPercentage) {
        return new Difference<>(expected, actual, difference, mismatchPercentage);
    }

    private Difference(T expected, T actual, int lineNumber) {
        this.expected = expected;
        this.actual = actual;
        this.lineNumber = lineNumber;
    }

    private Difference(T expected, T actual, T difference, double mismatchPercentage) {
        this.expected = expected;
        this.actual = actual;
        this.difference = difference;
        this.mismatchPercentage = mismatchPercentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expected, actual, lineNumber);
    }

}
