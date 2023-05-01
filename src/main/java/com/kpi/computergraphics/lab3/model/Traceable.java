package com.kpi.computergraphics.lab3.model;

import java.util.Optional;

public interface Traceable {
    Optional<IntersectionInfo> findIntersection(Ray ray);
}
