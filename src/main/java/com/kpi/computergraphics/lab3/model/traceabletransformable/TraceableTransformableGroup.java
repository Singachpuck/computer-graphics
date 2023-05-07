package com.kpi.computergraphics.lab3.model.traceabletransformable;

import com.kpi.computergraphics.lab3.model.base.Matrix;

import java.util.List;

public class TraceableTransformableGroup<T extends TraceableTransformable<T>>
        extends TraceableGroup<T>
        implements TraceableTransformable<T> {

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
