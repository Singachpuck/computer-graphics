package com.kpi.computergraphics.model;

import com.kpi.computergraphics.model.base.Point3D;
import com.kpi.computergraphics.model.base.Vector3D;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Camera {

    private Point3D place;

    private Vector3D direction;

    private Screen screen;

    private float fov;
}
