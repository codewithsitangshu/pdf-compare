package com.org.codewithsitangshu.pdf.result;

import com.org.codewithsitangshu.pdf.result.text.DifferenceText;

import java.util.List;
import java.util.Map;

public interface ResultFormat {

    List<DifferenceText<Object>> compareText(String expectedText, String actualText);

}
