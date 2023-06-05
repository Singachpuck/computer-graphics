package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.scene.Scene;

import java.io.OutputStream;

public class PpmRendererFactory implements RendererFactory {

    public Renderer create(Scene scene, OutputStream outputStream) {
        return new PPMRenderer(scene, outputStream);
    }
}
