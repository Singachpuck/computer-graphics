package com.kpi.computergraphics.model.object;

import com.kpi.computergraphics.model.base.Plane;
import com.kpi.computergraphics.model.base.Point3D;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Circle3D extends Object3D {

    private Plane plane;

    private Point3D center;

    private float radius;
}
