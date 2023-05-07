package com.kpi.computergraphics.lab3.model.traceabletransformable;

import com.kpi.computergraphics.lab3.model.IntersectionInfo;
import com.kpi.computergraphics.lab3.model.base.Ray;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TraceableGroup<T extends Traceable> implements Traceable {
    public List<T> traceableObjects;

    public TraceableGroup(List<T> traceableObjects) {
        this.traceableObjects = traceableObjects;
    }

    public TraceableGroup() {
        this(new ArrayList<T>());
    }

    public void add(T traceableObject) {
        traceableObjects.add(traceableObject);
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        Optional<IntersectionInfo> closestHit = Optional.empty();
        for (T traceableObject : traceableObjects) {
            Optional<IntersectionInfo> currentHit = traceableObject.findIntersection(ray);

            if (currentHit.isEmpty()) {
                continue;
            }

            closestHit = closestHit.isPresent() ?
                    (closestHit.get().length() < currentHit.get().length() ? closestHit : currentHit)
                    : currentHit;
        }

        return closestHit;
    }
}
