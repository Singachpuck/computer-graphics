package com.kpi.computergraphics.lab3.scene.objects;

import com.kpi.computergraphics.lab3.base.*;
import com.kpi.computergraphics.lab3.optimization.BoundUtil;

import java.util.Optional;
import java.util.stream.DoubleStream;

public class Triangle implements SceneObject {

    private static final double EPS = 0.00000001;
    private Vector3D vertex1;
    private Vector3D vertex2;
    private Vector3D vertex3;

    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    @Override
    public Bound getBound() {
        return new Bound(new LimitedAxis(LimitedAxis.Axis.X, new BoundUtil.MinMax(
                DoubleStream.of(
                            vertex1.x(),
                            vertex2.x(),
                            vertex3.x()
            ).min().getAsDouble(),
                DoubleStream.of(
                            vertex1.x(),
                            vertex2.x(),
                            vertex3.x()
            ).max().getAsDouble())),
            new LimitedAxis(LimitedAxis.Axis.Y, new BoundUtil.MinMax(
                DoubleStream.of(
                            vertex1.y(),
                            vertex2.y(),
                            vertex3.y()
                    ).min().getAsDouble(),
                DoubleStream.of(
                            vertex1.y(),
                            vertex2.y(),
                            vertex3.y()
                    ).max().getAsDouble())),
            new LimitedAxis(LimitedAxis.Axis.Z, new BoundUtil.MinMax(
                DoubleStream.of(
                            vertex1.z(),
                            vertex2.z(),
                            vertex3.z()
                    ).min().getAsDouble(),
                DoubleStream.of(
                            vertex1.z(),
                            vertex2.z(),
                            vertex3.z()
                    ).max().getAsDouble())));
    }

    public void transform(Matrix transformMatrix) {
        vertex1 = MatrixTransformation.transform(vertex1, transformMatrix);
        vertex2 = MatrixTransformation.transform(vertex2, transformMatrix);
        vertex3 = MatrixTransformation.transform(vertex3, transformMatrix);
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        var E1 = vertex2.subtract(vertex1);
        var E2 = vertex3.subtract(vertex1);
        var T = ray.start().subtract(vertex1);

        var P = ray.vector().crossProduct(E2);
        var Q = T.crossProduct(E1);

        var PE1 = P.dotProduct(E1);

        var length = Q.dotProduct(E2) / PE1;
        if (length <= EPS) {
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

        return Optional.of(new IntersectionInfo(intersectPos, intersectNormal, length, this, new Color(1, 1, 1)));
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
