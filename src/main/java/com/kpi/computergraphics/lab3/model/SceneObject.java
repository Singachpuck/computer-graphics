package com.kpi.computergraphics.lab3.model;

import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Ray;

import java.util.Optional;

public interface SceneObject {
    Optional<IntersectionInfo> findIntersection(Ray ray);

    void transform(Matrix matrix);
}
