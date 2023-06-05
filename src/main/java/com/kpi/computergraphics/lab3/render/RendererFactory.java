package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.scene.Scene;

import java.io.OutputStream;

public interface RendererFactory {
    Renderer create(Scene scene, OutputStream outputStream);
}
