package com.kpi.computergraphics.lab3.model.traceabletransformable;

import com.kpi.computergraphics.lab3.model.base.Matrix;

import java.util.List;

public class TraceableTransformableGroup<T extends Traceable & Transformable>
        extends TraceableGroup<T>
        implements Traceable, Transformable {

    public TraceableTransformableGroup(List<T> traceableObjects) {
        super(traceableObjects);
    }

    @Override
    public void transform(Matrix matrix) {
        for (T traceableObject : traceableObjects) {
            traceableObject.transform(matrix);
        }
    }
}
