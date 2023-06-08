package com.kpi.computergraphics.lab3.scene.objects;

import com.kpi.computergraphics.lab3.base.*;
import com.kpi.computergraphics.lab3.optimization.BoundUtil;

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

    @Override
    public Bound getBound() {
        double x2 = normal.x() * normal.x();
        double y2 = normal.y() * normal.y();
        double z2 = normal.z() * normal.z();

        Vector3D half = new Vector3D(
                this.radius * Math.sqrt(y2 + z2),
                this.radius * Math.sqrt(z2 + x2),
                this.radius * Math.sqrt(x2 + y2)
        );

        Vector3D minVector = this.center.subtract(half);
        Vector3D maxVector = this.center.add(half);

        return new Bound(new LimitedAxis(LimitedAxis.Axis.X, new BoundUtil.MinMax(minVector.x(), maxVector.x())),
                new LimitedAxis(LimitedAxis.Axis.Y, new BoundUtil.MinMax(minVector.y(), maxVector.y())),
                new LimitedAxis(LimitedAxis.Axis.Z, new BoundUtil.MinMax(minVector.z(), maxVector.z())));
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

        return Optional.of(new IntersectionInfo(pHit, denominator < 0 ? normal.negate() : normal, length, this, new Color(1, 1, 1)));
    }
}
