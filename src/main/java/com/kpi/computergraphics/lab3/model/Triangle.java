package com.kpi.computergraphics.lab3.model;

import com.kpi.computergraphics.lab3.service.MatrixTransformation;

import java.util.Optional;

public class Triangle {

    private final double eps = 0.00000001;
    private Vector3D vertex1;
    private Vector3D vertex2;
    private Vector3D vertex3;

    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    public void transform(Matrix transformMatrix) {
        vertex1 = MatrixTransformation.transform(vertex1, transformMatrix);
        vertex2 = MatrixTransformation.transform(vertex2, transformMatrix);
        vertex3 = MatrixTransformation.transform(vertex3, transformMatrix);
    }

    public Optional<HitInfo> findIntersection(Ray ray) {
        var E1 = vertex2.subtract(vertex1);
        var E2 = vertex3.subtract(vertex1);
        var T = ray.start().subtract(vertex1);

        var P = ray.vector().crossProduct(E2);
        var Q = T.crossProduct(E1);

        var PE1 = P.dotProduct(E1);

        var length = Q.dotProduct(E2) / PE1;
        if (length <= eps) {
            return Optional.empty();
        }

        var u = P.dotProduct(T) / PE1;
        if (u < 0) {
            return Optional.empty();
        }

        var v = Q.dotProduct(ray.vector()) / PE1;
        if (v < 0) {
            return Optional.empty();
        }

        if (u + v > 1) {
            return Optional.empty();
        }

        var intersectPos = ray.start().add(ray.vector().multiply(length));
        var rawNormal = E1.crossProduct(E2);
        var intersectNormal = rawNormal.dotProduct(ray.vector()) < 0 ?
                rawNormal.multiply(-1) : rawNormal;

        return Optional.of(new HitInfo(intersectPos, intersectNormal, length));
    }

    public Vector3D vertex1() {
        return vertex1;
    }

    public Vector3D vertex2() {
        return vertex2;
    }

    public Vector3D vertex3() {
        return vertex3;
    }
}
