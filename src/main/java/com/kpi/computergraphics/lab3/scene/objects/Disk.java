package com.kpi.computergraphics.lab3.scene.objects;

import com.kpi.computergraphics.lab3.base.Matrix;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.base.MatrixTransformation;

import java.util.Optional;

public class Disk implements SceneObject {
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

        double length = center
                .subtract(ray.start())
                .dotProduct(normal) / denominator;

        if (length <= 0) return Optional.empty();

        Vector3D pHit = ray.start()
                .add(ray.start().multiply(length));
        double distanceToCenter = pHit
                .subtract(center).length();

        if (distanceToCenter > radius) return Optional.empty();

        return Optional.of(new IntersectionInfo(pHit, denominator < 0 ? normal.negate() : normal, length, this));
    }
}
