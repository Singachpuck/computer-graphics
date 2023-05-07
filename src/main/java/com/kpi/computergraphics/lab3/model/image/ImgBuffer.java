package com.kpi.computergraphics.lab3.model.image;

import java.io.InputStream;

public class ImgBuffer {
    private final ImgInfo imgInfo;
    final InputStream pixels;

    public ImgBuffer(ImgInfo imgInfo, InputStream pixels) {
        this.imgInfo = imgInfo;
        this.pixels = pixels;
    }

    public ImgInfo getImageInfo() {
        return imgInfo;
    }

    public InputStream getPixels() {
        return pixels;
    }
}
