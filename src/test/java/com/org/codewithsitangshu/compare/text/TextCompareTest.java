package com.org.codewithsitangshu.compare.text;

import com.org.codewithsitangshu.pdf.assertion.Assertion;
import com.org.codewithsitangshu.pdf.assertion.TextAssert;
import com.org.codewithsitangshu.pdf.compare.Comparator;
import com.org.codewithsitangshu.pdf.compare.Compare;
import com.org.codewithsitangshu.pdf.compare.CompareMode;
import com.org.codewithsitangshu.pdf.config.Builder;
import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.result.Difference;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextCompareTest {

    Assertion assertion = new TextAssert();

    @Test
    public void compareTextWithDefaultBuilder() throws IOException {
        Comparator comparator = new Compare();
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        boolean flag = assertion.isDifference(allDifference);
        //Print Differences
        if(flag) {
            StringBuilder allMismatchText = new StringBuilder();
            allDifference.forEach((page, differences) -> {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                for (Difference<Object> difference : differences) {
                    allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                    allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                    allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
                }
                allMismatchText.append("\n\n\n");
            });
            System.out.println(allMismatchText);
        }
        else {
            System.out.println("No differences. Good to go.");
        }

        Assert.assertFalse(flag, "PDFs have differences. Please check");

    }

    @Test
    public void compareTextAllPages() throws IOException {

        Config config = new Builder().setCompareAllPages(true)
                .build();

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page,differences) -> {
            allMismatchText.append("Page ").append(page).append(":").append("\n\n");
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });

        System.out.println(allMismatchText);

    }

    @Test
    public void compareTextWithSpecificStartAndEndPage() throws IOException {

        Config config = new Builder()
                .setCompareAllPages(false)
                .setStartPage(2)
                .setEndPage(6)
                .build();

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page,differences) -> {
            allMismatchText.append("Page ").append(page).append(":").append("\n\n");
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });

        System.out.println(allMismatchText);

    }

    @Test
    public void compareTextWithSpecificPages() throws IOException {

        List<Integer> pages = Arrays.asList(2,4);

        Config config = new Builder()
                .setCompareAllPages(false)
                .setSpecificPages(pages)
                .build();

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page,differences) -> {
            allMismatchText.append("Page ").append(page).append(":").append("\n\n");
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });

        System.out.println(allMismatchText);

    }


    @Test
    public void compareTextWithTrimWhiteSpace() throws IOException {

        Config config = new Builder().setCompareAllPages(true)
                .build();
        config.setTrimWhiteSpace(true);

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page,differences) -> {
            allMismatchText.append("Page ").append(page).append(":").append("\n\n");
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });

        System.out.println(allMismatchText);

    }

    @Test
    public void compareTextWithExcludeRegExp() throws IOException {

        Config config = new Builder().setCompareAllPages(true)
                .build();
        config.setRegexToExclude("John Daugherty \\(\\d+\\)");

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page,differences) -> {
            allMismatchText.append("Page ").append(page).append(":").append("\n\n");
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });

        System.out.println(allMismatchText);

    }

}
