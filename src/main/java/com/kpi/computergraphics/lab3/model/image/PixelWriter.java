package com.kpi.computergraphics.lab3.model.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PixelWriter implements ImgWriter {
    public String format = String.valueOf(ImgFormat.PPM);

    @Override
    public String getFormat() {
        return format;
    }

    public InputStream write(ImgBuffer imgBuffer) {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream();
        try {
            out.connect(in);
        } catch (Exception e) {
            // Handle exception
        }
        ImgInfo imgInfo = imgBuffer.getImageInfo();
        int height = imgInfo.getHeight();
        int width = imgInfo.getWidth();
        int maxColor = imgInfo.getMaxColor();
        String header = String.format("P3 %d %d %d\n", width, height, maxColor);
        try {
            out.write(header.getBytes());

            InputStream pixels = imgBuffer.getPixels();
            byte[] buffer = new byte[3];
            int count = 0;
            while (pixels.read(buffer) != -1) {
                String pixel = String.format("%d %d %d\n", buffer[0], buffer[1], buffer[2]);
                out.write(pixel.getBytes());
                count++;
            }
            pixels.close();
            out.close();
            in.close();
        } catch (IOException e) {
            //fix
        }
        return in;
    }
}
