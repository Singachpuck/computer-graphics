package com.kpi.computergraphics.lab3.model;

import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.model.traceabletransformable.TraceableTransformable;
import com.kpi.computergraphics.lab3.model.traceabletransformable.Transformable;

import java.util.List;

public class Scene implements Transformable {
    public List<TraceableTransformable> objects;
    public Camera camera;
    public Vector3D light;

    public Scene(List<TraceableTransformable> objects, Camera camera, Vector3D light) {
        this.objects = objects;
        this.camera = camera;
        this.light = light.normalize();
    }

    public void transform(Matrix matrix) {
        for (TraceableTransformable object : this.objects) {
            object.transform(matrix);
        }
    }
}
