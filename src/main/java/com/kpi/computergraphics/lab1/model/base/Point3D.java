package com.kpi.computergraphics.lab1.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point3D {

    private float x;

    private float y;

    private float z;

    public Point3D(float[] coords3d) {
        this.x = coords3d[0];
        this.y = coords3d[1];
        this.z = coords3d[2];
    }
}
