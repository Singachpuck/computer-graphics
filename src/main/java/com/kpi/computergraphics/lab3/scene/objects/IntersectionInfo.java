package com.kpi.computergraphics.lab3.scene.objects;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Vector3D;

public class IntersectionInfo {

    private final Vector3D position;
    private final Vector3D normal;
    private final double length;
    private final SceneObject object;
    private Color color;

    public IntersectionInfo(Vector3D position, Vector3D normal, double length, SceneObject object, Color color) {
        this.position = position;
        this.normal = normal.normalize();
        this.length = length;
        this.object = object;
        this.color = color;
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

    public Color color() {
        return color;
    }

    public void color(Color color) {
        this.color = color;
    }

    public static IntersectionInfo getClosest(IntersectionInfo... intersectionInfos) {
        double minT = intersectionInfos[0].length;
        IntersectionInfo closestIntersection = intersectionInfos[0];
        for (int i = 1; i < intersectionInfos.length; i++) {
            if (intersectionInfos[i].length < minT) {
                closestIntersection = intersectionInfos[i];
                minT = intersectionInfos[i].length;
            }
        }
        return closestIntersection;
    }
}
