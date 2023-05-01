package com.kpi.computergraphics.lab3.model;

import java.util.Optional;

public class Plane implements Traceable {

    private final Vector3D normal;
    private final Vector3D position;

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

        return Optional.of(new IntersectionInfo(intersectPos, intersectNormal, length));
    }
}
