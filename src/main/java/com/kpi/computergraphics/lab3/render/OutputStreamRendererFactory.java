package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.scene.Scene;

import java.io.OutputStream;

public class OutputStreamRendererFactory implements RendererFactory{
    @Override
    public Renderer create(Scene scene, OutputStream outputStream) {
        return new OutputStreamRenderer(scene, outputStream);
    }
}
