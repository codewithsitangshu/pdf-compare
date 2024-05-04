package com.org.codewithsitangshu.pdf.result;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ResultFormatVisual implements ResultFormat {


    private List<Difference<Object>> difference = new ArrayList<>();

    public void setDifference(BufferedImage expectedImage, BufferedImage actualImage,
                              BufferedImage differenceImage, double mismatchPercentage) {
        this.difference.add(Difference.of(expectedImage,actualImage,differenceImage,mismatchPercentage));
    }


    @Override
    public List<Difference<Object>> getDifference() {
        return this.difference;
    }

}
