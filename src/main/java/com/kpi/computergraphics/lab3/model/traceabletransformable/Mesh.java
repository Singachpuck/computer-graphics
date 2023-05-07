package com.kpi.computergraphics.lab3.model.traceabletransformable;

import com.kpi.computergraphics.lab3.model.object.Triangle;

import java.util.List;

public class Mesh extends TraceableTransformableGroup<Triangle> {
    public final List<Triangle> triangles = this.traceableObjects;

    public Mesh(List<Triangle> traceableObjects) {
        super(traceableObjects);
    }
}
