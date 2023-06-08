package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Matrix;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;

public class Scene<T extends SceneObject> {
    public List<T> objects;
    public Camera camera;
    public Vector3D light;

    Scene(List<T> objects, Camera camera, Vector3D light) {
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
