package com.kpi.computergraphics.lab3.model;

public class Ray {
    private final Vector3D start;
    private final Vector3D vector;

    public Ray(Vector3D start, Vector3D vector) {
        this.start = start;
        this.vector = vector.normalize();
    }

    public boolean contains(Vector3D vector) {
        return vector.subtract(start).dotProduct(vector) / vector.dotProduct(vector) >= 0;
    }

    public double radsAngleWith(Ray ray) {
        return vector.radsAngleWith(ray.vector);
    }

    public Vector3D start() {
        return start;
    }

    public Vector3D vector() {
        return vector;
    }
}
