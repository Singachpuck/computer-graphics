package com.kpi.computergraphics.lab3.model.traceabletransformable;

import com.kpi.computergraphics.lab3.model.IntersectionInfo;
import com.kpi.computergraphics.lab3.model.base.Ray;

import java.util.Optional;

public interface Traceable {
    Optional<IntersectionInfo> findIntersection(Ray ray);
}
