package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.scene.ObjectGroupFactory;
import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.io.OutputStream;

public interface RendererFactory {
    Renderer create(Scene<SceneObject> scene, ObjectGroupFactory ogf, OutputStream os);
}
