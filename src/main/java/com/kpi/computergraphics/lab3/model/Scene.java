package com.kpi.computergraphics.lab3.model;

import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Vector3D;

import java.util.List;

public class Scene {
    public List<SceneObject> objects;
    public Camera camera;
    public Vector3D light;

    public Scene(List<SceneObject> objects, Camera camera, Vector3D light) {
        this.objects = objects;
        this.camera = camera;
        this.light = light.normalize();
    }

    public void transform(Matrix matrix) {
        for (SceneObject object : objects) {
            object.transform(matrix);
        }
    }
}
