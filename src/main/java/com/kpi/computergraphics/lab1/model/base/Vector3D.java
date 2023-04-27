package com.kpi.computergraphics.lab1.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vector3D {

    private float x;

    private float y;

    private float z;

    public Vector3D(float[] coords3d) {
        this.x = coords3d[0];
        this.y = coords3d[1];
        this.z = coords3d[2];
    }

    public Vector3D(Point3D p1, Point3D p2) {
        this.x = p2.getX() - p1.getX();
        this.y = p2.getY() - p1.getY();
        this.z = p2.getZ() - p1.getZ();
    }
}
