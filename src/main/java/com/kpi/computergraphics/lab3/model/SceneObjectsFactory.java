package com.kpi.computergraphics.lab3.model;

import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.model.object.Sphere;
import com.kpi.computergraphics.lab3.model.object.Triangle;

public class SceneObjectsFactory {
    public Sphere sphere(
            double x,
            double y,
            double z,
            double radius
    ) {
        return new Sphere(new Vector3D(x, y, z), radius);
    }

    public Triangle triangle(
            Vector3D vertex1,
            Vector3D vertex2,
            Vector3D vertex3
    ) {
        return new Triangle(vertex1, vertex2, vertex3);
    }

    public Vector3D vector(double x, double y, double z) {
        return new Vector3D(x, y, z);
    }

    public Camera camera(Vector3D focalPoint,
                         Vector3D viewVector,
                         double fov,
                         int horizontalResolution,
                         int verticalResolution) {
        return new Camera(focalPoint, viewVector, fov, horizontalResolution, verticalResolution);
    }
}
