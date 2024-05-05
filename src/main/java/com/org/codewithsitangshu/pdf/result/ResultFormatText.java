package com.org.codewithsitangshu.pdf.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultFormatText implements ResultFormat {
    private static final Logger LOGGER = Logger.getLogger(ResultFormatText.class.getName());

    private List<Difference<Object>> difference = new ArrayList<>();
    private Map<Integer, List<Difference<Object>>> allDifference = new LinkedHashMap<>();

    /**
     * Sets the difference between expected and actual text for a specific line number.
     *
     * @param expectedText The expected text.
     * @param actualText   The actual text.
     * @param lineNumber   The line number where the difference occurs.
     */
    public void setDifference(String expectedText, String actualText, int lineNumber) {
        LOGGER.log(Level.INFO, "Setting text difference for line number: " + lineNumber);
        this.difference.add(Difference.of(expectedText, actualText, lineNumber));
    }

    /**
     * Sets all differences for a specific page number.
     *
     * @param pageNumber The page number.
     * @param diff       The list of differences for the page.
     */
    public void setAllDifference(int pageNumber, List<Difference<Object>> diff) {
        LOGGER.log(Level.INFO, "Setting all differences for page number: " + pageNumber);
        this.allDifference.put(pageNumber, diff);
        this.difference.clear(); // Clearing current differences after setting all differences for the page
    }

    @Override
    public List<Difference<Object>> getDifference() {
        return this.difference;
    }

    @Override
    public Map<Integer, List<Difference<Object>>> getAllDifferences() {
        return this.allDifference;
    }
}
