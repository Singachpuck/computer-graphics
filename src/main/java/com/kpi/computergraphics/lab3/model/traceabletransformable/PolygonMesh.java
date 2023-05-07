package com.kpi.computergraphics.lab3.model.traceabletransformable;

import com.kpi.computergraphics.lab3.model.IntersectionInfo;
import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Ray;
import com.kpi.computergraphics.lab3.model.object.Triangle;

import java.util.List;
import java.util.Optional;

public class PolygonMesh implements Traceable, Transformable {

    private final List<Triangle> triangles;

    public PolygonMesh(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    @Override
    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        double minLength = Double.MAX_VALUE;
        Optional<IntersectionInfo> closestIntersection = Optional.empty();
        for (int i = 0; i < triangles.size() - 1; i++) {
            Optional<IntersectionInfo> intersection = triangles.get(i).findIntersection(ray);
            if (intersection.isPresent() && intersection.get().length() < minLength) {
                minLength = intersection.get().length();
                closestIntersection = intersection;
            }
        }
        return closestIntersection;
    }

    public void transform(Matrix transformMatrix) {
        triangles.forEach(triangle -> triangle.transform(transformMatrix));
    }
}
