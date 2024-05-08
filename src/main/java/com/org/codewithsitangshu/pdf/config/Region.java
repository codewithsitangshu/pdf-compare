package com.org.codewithsitangshu.pdf.config;

public class Region {
    private int x1, y1, x2, y2;

    public Region(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isInsideRegion(int x, int y) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2;
    }
}
