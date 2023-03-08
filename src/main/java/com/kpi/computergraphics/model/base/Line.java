package com.kpi.computergraphics.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Line {

    private Point3D point;

    private Vector3D direction;
}
