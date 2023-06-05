package com.kpi.computergraphics.lab3.service.renderer;

import com.kpi.computergraphics.lab3.model.Scene;

import java.io.OutputStream;

public class OutputStreamRendererFactory implements RendererFactory{
    @Override
    public Renderer create(Scene scene, OutputStream outputStream) {
        return new OutputStreamRenderer(scene, outputStream);
    }
}
