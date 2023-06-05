package com.kpi.computergraphics.lab3.scene.objects;

import com.kpi.computergraphics.lab3.base.Vector3D;

public class IntersectionInfo {

    private final Vector3D position;
    private final Vector3D normal;
    private final double length;
    private final SceneObject object;

    public IntersectionInfo(Vector3D position, Vector3D normal, double length, SceneObject object) {
        this.position = position;
        this.normal = normal.normalize();
        this.length = length;
        this.object = object;
    }

    public Vector3D position() {
        return position;
    }

    public Vector3D normal() {
        return normal;
    }

    public double length() {
        return length;
    }

    public SceneObject object() {
        return object;
    }
}
