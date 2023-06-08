package com.kpi.computergraphics.lab3.scene.objects;

import com.kpi.computergraphics.lab3.base.Bounded;
import com.kpi.computergraphics.lab3.base.Matrix;
import com.kpi.computergraphics.lab3.base.Ray;

import java.util.Optional;

public interface SceneObject extends Bounded {
    Optional<IntersectionInfo> findIntersection(Ray ray);

    void transform(Matrix matrix);
}
