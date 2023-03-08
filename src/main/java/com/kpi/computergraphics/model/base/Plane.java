package com.kpi.computergraphics.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Plane {

    private Vector3D normal;

    private Point3D point;
}
