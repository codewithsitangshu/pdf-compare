package com.org.codewithsitangshu.compare.text;

import com.org.codewithsitangshu.pdf.compare.Comparator;
import com.org.codewithsitangshu.pdf.compare.Compare;
import com.org.codewithsitangshu.pdf.compare.CompareMode;
import com.org.codewithsitangshu.pdf.config.Builder;
import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.result.Difference;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.org.codewithsitangshu.pdf.assertion.Assertion.assertThat;

public class TextCompareTest {

    @Test
    public void compareTextWithDefaultBuilder() throws IOException {
        Comparator comparator = new Compare();
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        //Print Differences
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page, differences) -> {
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });
        if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
            allMismatchText.append("No differences. Good to go.");
        }
        System.out.println(allMismatchText);

        //Assertion for text mismatch
        boolean flag = assertThat(resultFormat).hasTextMismatch();

    }

    @Test
    public void compareTextAllPages() throws IOException {

        Config config = new Builder().setCompareAllPages(true)
                .build();

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        //Print Differences
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page, differences) -> {
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });
        if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
            allMismatchText.append("No differences. Good to go.");
        }
        System.out.println(allMismatchText);

        //Assertion for text mismatch
        boolean flag = assertThat(resultFormat).hasTextMismatch();

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
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        //Print Differences
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page, differences) -> {
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });
        if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
            allMismatchText.append("No differences. Good to go.");
        }
        System.out.println(allMismatchText);

        //Assertion for text mismatch
        boolean flag = assertThat(resultFormat).hasTextMismatch();

    }

    @Test
    public void compareTextWithSpecificPages() throws IOException {

        List<Integer> pages = Arrays.asList(5, 7);

        Config config = new Builder()
                .setCompareAllPages(false)
                .setSpecificPages(pages)
                .build();

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        //Print Differences
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page, differences) -> {
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });
        if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
            allMismatchText.append("No differences. Good to go.");
        }
        System.out.println(allMismatchText);

        //Assertion for text mismatch
        boolean flag = assertThat(resultFormat).hasTextMismatch();

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
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        //Print Differences
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page, differences) -> {
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });
        if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
            allMismatchText.append("No differences. Good to go.");
        }
        System.out.println(allMismatchText);

        //Assertion for text mismatch
        boolean flag = assertThat(resultFormat).hasTextMismatch();

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
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        //Print Differences
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page, differences) -> {
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });
        if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
            allMismatchText.append("No differences. Good to go.");
        }
        System.out.println(allMismatchText);

        //Assertion for text mismatch
        boolean flag = assertThat(resultFormat).hasTextMismatch();

    }

    @Test
    public void compareTextWithExcludeListOfString() throws IOException {

        Config config = new Builder().setCompareAllPages(true)
                .build();

        List<String> excludingList = Arrays.asList("Even being \\d+ to \\d+ minutes","John Daugherty \\(\\d+\\)");
        config.setExcludeList(excludingList);

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.TEXT);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        //Print Differences
        Map<Integer, List<Difference<Object>>> allDifference = resultFormat.getAllDifferences();
        StringBuilder allMismatchText = new StringBuilder();
        allDifference.forEach((page, differences) -> {
            for (Difference<Object> difference : differences) {
                allMismatchText.append("Page ").append(page).append(":").append("\n\n");
                allMismatchText.append("Line ").append(difference.getLineNumber()).append(":").append("\n");
                allMismatchText.append("Expected String : ").append(difference.getExpected()).append("\n");
                allMismatchText.append("Actual String : ").append(difference.getActual()).append("\n");
            }
            allMismatchText.append("\n\n\n");
        });
        if (allMismatchText.toString().replaceAll("\\n", "").isEmpty()) {
            allMismatchText.append("No differences. Good to go.");
        }
        System.out.println(allMismatchText);

        //Assertion for text mismatch
        boolean flag = assertThat(resultFormat).hasTextMismatch();

    }



}
