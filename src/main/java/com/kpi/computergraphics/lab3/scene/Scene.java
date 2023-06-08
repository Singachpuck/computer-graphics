package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Matrix;
import com.kpi.computergraphics.lab3.scene.light.Light;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;

public class Scene<T extends SceneObject> {
    public List<T> objects;
    public Camera camera;
    public List<Light> lights;

    Scene(List<T> objects, Camera camera, List<Light> lights) {
        this.objects = objects;
        this.camera = camera;
        this.lights = lights;
    }

    public void transform(Matrix matrix) {
        for (SceneObject object : objects) {
            object.transform(matrix);
        }
    }
}
