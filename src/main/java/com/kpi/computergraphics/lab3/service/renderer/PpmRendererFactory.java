package com.kpi.computergraphics.lab3.service.renderer;

import com.kpi.computergraphics.lab3.model.Scene;

import java.io.OutputStream;

public class PpmRendererFactory implements RendererFactory {

    public Renderer create(Scene scene, OutputStream outputStream) {
        return new PPMRenderer(scene, outputStream);
    }
}
