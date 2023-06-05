package com.kpi.computergraphics.lab3.scene.objects;

import com.kpi.computergraphics.lab3.base.Matrix;
import com.kpi.computergraphics.lab3.base.Ray;

import java.util.Optional;

public interface SceneObject {
    Optional<IntersectionInfo> findIntersection(Ray ray);

    void transform(Matrix matrix);
}
