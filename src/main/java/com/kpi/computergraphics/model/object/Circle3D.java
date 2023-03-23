package com.kpi.computergraphics.model.object;

import com.kpi.computergraphics.model.base.Line;
import com.kpi.computergraphics.model.base.Plane;
import com.kpi.computergraphics.model.base.Point3D;
import com.kpi.computergraphics.model.base.Vector3D;
import com.kpi.computergraphics.service.LinearAlgebra;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Circle3D implements Object3D {

    private Plane plane;

    private Point3D center;

    private float radius;

    @Override
    public Point3D[] intersects(Line line) {
        return LinearAlgebra.intersects(line, this);
    }

    @Override
    public Vector3D normal(Point3D point) {
        return plane.getNormal();
    }
}
