package com.kpi.computergraphics.lab3.base;

import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.Optional;

public class Plane implements SceneObject {

    private Vector3D normal;
    private Vector3D position;

    public Plane(Vector3D normal, Vector3D center) {
        this.normal = normal;
        this.position = center;
    }

    public Vector3D normal() {
        return normal;
    }

    public Vector3D position() {
        return position;
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        var denominator = normal.dotProduct(ray.vector());

        if (Math.abs(denominator) == 0) {
            return Optional.empty();
        }

        var length = position.subtract(ray.start()).dotProduct(normal) / denominator;

        if (length <= 0) {
            return Optional.empty();
        }

        var intersectPos = ray.start().add(ray.vector().multiply(length));
        var intersectNormal = denominator < 0 ? normal.negate() : normal;

        return Optional.of(new IntersectionInfo(intersectPos, intersectNormal, length, this));
    }

    @Override
    public void transform(Matrix matrix) {
        normal = MatrixTransformation.transform(normal, matrix);
        position = MatrixTransformation.transform(position, matrix);
    }


}
