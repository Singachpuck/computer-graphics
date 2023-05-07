package com.kpi.computergraphics.lab3.model.image;

public enum ImgFormat {
    PPM("ppm"),
    BMP("bmp"),
    PNG("png");

    private final String format;

    ImgFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
