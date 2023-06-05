package com.kpi.computergraphics.lab3.service;

import com.kpi.computergraphics.lab3.model.Scene;
import com.kpi.computergraphics.lab3.service.renderer.OutputStreamRenderer;
import com.kpi.computergraphics.lab3.service.renderer.PPMRenderer;
import com.kpi.computergraphics.lab3.service.renderer.Renderer;

import java.io.OutputStream;

public class RendererFactory {

    public Renderer create(Class<? extends Renderer> renderer, Scene scene, OutputStream outputStream) {
        if (renderer == OutputStreamRenderer.class) {
            return new OutputStreamRenderer(scene, outputStream);
        }
        if (renderer == PPMRenderer.class) {
            return new PPMRenderer(scene, outputStream);
        }
        throw new IllegalStateException("No such renderer found");
    }
}
