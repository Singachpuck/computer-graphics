package com.kpi.computergraphics.lab3.model.object;

import com.kpi.computergraphics.lab3.model.IntersectionInfo;
import com.kpi.computergraphics.lab3.model.SceneObject;
import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Ray;
import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.service.MatrixTransformation;

import java.util.Optional;

public class Sphere implements SceneObject {
    public Vector3D center;
    public double radius;

    public Sphere(Vector3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public void transform(Matrix matrix) {
        center = MatrixTransformation.transform(center, matrix);
        radius = MatrixTransformation.transform(radius, matrix);
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        var first = ray.start();
        var second = ray.start().add(ray.vector());

        var a = (second.x() - first.x()) * (second.x() - first.x()) +
                (second.y() - first.y()) * (second.y() - first.y()) +
                (second.z() - first.z()) * (second.z() - first.z());
        var b = 2*((second.x()-first.x())*(first.x()-center.x())+
                (second.y()-first.y())*(first.y()-center.y())+
                (second.z()-first.z())*(first.z()-center.z()));
        var c = (first.x()-center.x())*(first.x()-center.x()) +
                (first.y()-center.y())*(first.y()-center.y()) +
                (first.z()-center.z())*(first.z()-center.z()) - radius*radius;

        double D = b * b - 4 * a * c;

        if (D < 0) {
            return Optional.empty();
        }
        double t1 = (-b + Math.sqrt(D)) / (2 * a);
        double t2 = (-b - Math.sqrt(D)) / (2 * a);
        if (t2 < 0) {
            return Optional.empty();
        }
        double length = Math.min(t1, t2);
        if (length <= 0) {
            return Optional.empty();
        }
        Vector3D pHit = ray.start().add(ray.vector().multiply(length));
        return Optional.of(new IntersectionInfo(pHit, this.center.subtract(pHit).normalize(), length, this));
    }
}
