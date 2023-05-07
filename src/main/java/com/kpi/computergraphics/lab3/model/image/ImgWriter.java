package com.kpi.computergraphics.lab3.model.image;

import java.io.InputStream;

public interface ImgWriter {
    String getFormat();
    InputStream write(ImgBuffer imgBuffer);
}
