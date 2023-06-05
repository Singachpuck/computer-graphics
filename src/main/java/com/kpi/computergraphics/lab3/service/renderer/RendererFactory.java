package com.kpi.computergraphics.lab3.service.renderer;

import com.kpi.computergraphics.lab3.model.Scene;

import java.io.OutputStream;

public interface RendererFactory {
    Renderer create(Scene scene, OutputStream outputStream);
}
