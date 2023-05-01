package com.kpi.computergraphics.lab3.model;

public class HitInfo {

    private final Vector3D position;
    private final Vector3D normal;
    private final double length;

    public HitInfo(Vector3D position, Vector3D normal, double t) {
        this.position = position;
        this.normal = normal;
        this.length = t;
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
}
