package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.scene.ObjectGroupFactory;
import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.io.OutputStream;

public class PpmRendererFactory implements RendererFactory {

    public Renderer create(Scene<SceneObject> scene, ObjectGroupFactory ogf, OutputStream os) {
        return new PPMRenderer(scene, ogf, os);
    }
}
