package com.org.codewithsitangshu.compare.visual;

import com.org.codewithsitangshu.pdf.compare.Comparator;
import com.org.codewithsitangshu.pdf.compare.Compare;
import com.org.codewithsitangshu.pdf.compare.CompareMode;
import com.org.codewithsitangshu.pdf.config.Builder;
import com.org.codewithsitangshu.pdf.config.Config;
import com.org.codewithsitangshu.pdf.config.Region;
import com.org.codewithsitangshu.pdf.result.ResultFormat;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.org.codewithsitangshu.pdf.assertion.Assertion.assertThat;

public class VisualCompareText {

    @Test
    public void comparePDFWithDefaultBuilder() throws IOException {

        Config config = new Builder().build();
        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/image-compare/sample1.pdf";
        String actualPdf = "src/test/resources/image-compare/sample2.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        assertThat(resultFormat).hasVisualMismatch();

    }

    @Test
    public void comparePDFWithSpecificStartAndEndPage() throws IOException {

        Config config = new Builder()
                .setCompareAllPages(false)
                .setStartPage(2)
                .setEndPage(4)
                .build();
        config.setSavePDFPath("src/test/resources/text-compare/result_start_end.pdf");
        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        assertThat(resultFormat).hasVisualMismatch();

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

        assertThat(resultFormat).hasVisualMismatch();

    }

    @Test
    public void comparePDFWithThreshold() throws IOException {

        double threshold = 0.1;
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

        assertThat(resultFormat).hasVisualMismatch(threshold);

    }

    @Test
    public void comparePDFWithRegionsToExclude() throws IOException {

        List<Region> regionsToExclude = Arrays.asList(
                new Region(200, 200, 2500, 500),
                new Region(1500, 1800, 3000, 3299)
            );

        Config config = new Builder()
                .setCompareAllPages(true)
                .build();
        config.setSavePDFPath("src/test/resources/text-compare/result_regions_excluded.pdf");
        config.setRegionsToExclude(regionsToExclude);
        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf, actualPdf);

        assertThat(resultFormat).hasVisualMismatch();

    }

    @Test
    public void comparePDFWithRegionsToExcludeOnSpecificPage() throws IOException {

        List<Region> regionsToExclude = Arrays.asList(
                new Region(200,200,2500,500),
                new Region(1500,1800,3000,3299));

        Map<Integer,List<Region>> regionsToExcludeOnSpecficPage = Map.of(
                3,Arrays.asList(new Region(100,100,2500,2500)),
                6,Arrays.asList(new Region(200,200,600,600)));

        Config config = new Builder()
                .setCompareAllPages(true)
                .build();
        config.setSavePDFPath("src/test/resources/text-compare/result_regions_excluded_on_specific_pages.pdf");

        /* If regionsToExclude and regionsToExcludeOnSpecficPage both are set
        then regionsToExcludeOnSpecficPage will take priority */
        config.setRegionsToExclude(regionsToExclude);
        config.setRegionsToExcludeOnSpecificPage(regionsToExcludeOnSpecficPage);

        Comparator comparator = new Compare(config);
        comparator.setCompareMode(CompareMode.VISUAL);

        String expectedPdf = "src/test/resources/text-compare/expected.pdf";
        String actualPdf = "src/test/resources/text-compare/actual.pdf";
        ResultFormat resultFormat = comparator.compare(expectedPdf,actualPdf);

        assertThat(resultFormat).hasVisualMismatch();

    }

}