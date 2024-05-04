package com.org.codewithsitangshu.compare.visual;

import com.org.codewithsitangshu.pdf.compare.Comparator;
import com.org.codewithsitangshu.pdf.compare.Compare;
import com.org.codewithsitangshu.pdf.compare.CompareMode;
import com.org.codewithsitangshu.pdf.config.Builder;
import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VisualCompareText {

    @Test
    public void comparePDFWithDefaultBuilder() throws IOException {

        Config config = new Builder().build();
        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/image-compare/sample1.pdf";
        String actualPdf = "src/test/resources/image-compare/sample2.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Assert.assertTrue(
                resultFormat.getDifference()
                        .stream()
                        .filter(diff -> diff.getMismatchPercentage() > 0)
                        .collect(Collectors.toList()).size() == 0, "Mismatch is there. Please see the result pdf"
        );

    }

    @Test
    public void comparePDFWithSpecificStartAndEndPage() throws IOException {

        Config config = new Builder()
                .setCompareAllPages(false)
                .setStartPage(2)
                .setEndPage(3)
                .build();
        config.setSavePDFPath("src/test/resources/text-compare/result_start_end.pdf");
        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Assert.assertTrue(
                resultFormat.getDifference()
                        .stream()
                        .filter(diff -> diff.getMismatchPercentage() > 0)
                        .collect(Collectors.toList()).size() == 0, "Mismatch is there. Please see the result pdf"
        );

    }

    @Test
    public void comparePDFWithSpecificPages() throws IOException {

        List<Integer> pages = Arrays.asList(2,7);
        Config config = new Builder()
                .setCompareAllPages(false)
                .setSpecificPages(pages)
                .build();
        config.setSavePDFPath("src/test/resources/text-compare/result_specific.pdf");
        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Assert.assertTrue(
                resultFormat.getDifference()
                        .stream()
                        .filter(diff -> diff.getMismatchPercentage() > 0)
                        .collect(Collectors.toList()).size() == 0, "Mismatch is there. Please see the result pdf"
        );

    }

    @Test
    public void comparePDFWithThreshold() throws IOException {

        int threshold = 2;
        Config config = new Builder()
                .setCompareAllPages(true)
                .build();
        config.setSavePDFPath("src/test/resources/text-compare/result_threshold.pdf");
        config.setThreshold(threshold);
        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        Assert.assertTrue(
                resultFormat.getDifference()
                        .stream()
                        .filter(diff -> diff.getMismatchPercentage() > threshold)
                        .collect(Collectors.toList()).size() == 0, "Mismatch is there. Please see the result pdf"
        );

    }

}
