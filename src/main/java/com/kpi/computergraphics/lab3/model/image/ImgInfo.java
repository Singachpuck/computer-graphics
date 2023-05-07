package com.kpi.computergraphics.lab3.model.image;

public interface ImgInfo {
    int getWidth();

    int getHeight();

    int getMaxColor();

    default boolean hasAlpha() {
        return false;
    }

    default boolean isGrayscale() {
        return false;
    }
}
