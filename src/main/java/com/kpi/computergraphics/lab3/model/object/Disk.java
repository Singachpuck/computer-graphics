package com.kpi.computergraphics.lab3.model.object;

import com.kpi.computergraphics.lab3.model.IntersectionInfo;
import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Ray;
import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.model.traceabletransformable.Traceable;
import com.kpi.computergraphics.lab3.model.traceabletransformable.TraceableTransformable;
import com.kpi.computergraphics.lab3.service.MatrixTransformation;

import java.util.Optional;

public class Disk implements Traceable, TraceableTransformable<Disk> {
    public Vector3D center;
    public Vector3D normal;
    public double radius;

    public Disk(Vector3D center, Vector3D normalVector, double radius) {
        this.center = center;
        this.normal = normalVector.normalize();
        this.radius = radius;
    }

    public void transform(Matrix matrix) {
        center = MatrixTransformation.transform(center, matrix);
        normal = MatrixTransformation.transform(normal, matrix);
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        double denominator = normal.dotProduct(ray.vector());
        if (Math.abs(denominator) == 0) return Optional.empty();

        double t = center
                .subtract(ray.start())
                .dotProduct(normal) / denominator;

        if (t <= 0) return Optional.empty();

        Vector3D pHit = ray.start()
                .add(ray.start().multiply(t));
        double distanceToCenter = pHit
                .subtract(center).length();

        if (distanceToCenter > radius) return Optional.empty();

        return Optional.of(new IntersectionInfo(pHit, denominator < 0 ? normal.negate() : normal, t));
    }
}
