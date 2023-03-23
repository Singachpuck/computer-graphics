package com.kpi.computergraphics.model.object;

import com.kpi.computergraphics.model.base.Line;
import com.kpi.computergraphics.model.base.Point3D;
import com.kpi.computergraphics.model.base.Vector3D;

public interface Object3D {

    Point3D[] intersects(Line line);

    Vector3D normal(Point3D point);
}
