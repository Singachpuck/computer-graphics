package com.kpi.computergraphics.lab3.model.object;

import com.kpi.computergraphics.lab3.model.IntersectionInfo;
import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Ray;
import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.model.traceabletransformable.Traceable;
import com.kpi.computergraphics.lab3.model.traceabletransformable.TraceableTransformable;
import com.kpi.computergraphics.lab3.service.MatrixTransformation;

import java.util.Optional;

public class Sphere implements Traceable, TraceableTransformable<Sphere> {
    public Vector3D center;
    public double radius;

    public Sphere(Vector3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public void transform(Matrix matrix) {
        this.center = MatrixTransformation.transform(this.center, matrix);
        this.radius = MatrixTransformation.transform(this.radius, matrix);
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        double a = ray.vector().dotProduct(ray.vector());
        // o - c vector
        Vector3D ocVector = ray.start().subtract(this.center);
        double b = 2 * ray.vector().dotProduct(ocVector);
        double c = ocVector.dotProduct(ocVector) - this.radius * this.radius;
        double D = b * b - 4 * a * c;

        if (D < 0) {
            return null;
        }
        double t1 = (-b + Math.sqrt(D)) / (2 * a);
        double t2 = (-b - Math.sqrt(D)) / (2 * a);
        // there are two options, t1 and t2 both < 0 which means ray hits the sphere but from behind,
        // or just t2 < 0 which means ray hits the sphere but starts inside it;
        // in both ways let's consider it as no hit
        if (t2 < 0) {
            return null;
        }
        double t = Math.min(t1, t2);
        if (t <= 0) {
            return null;
        }
        Vector3D pHit = ray.start().add(ray.vector().multiply(t));
        return Optional.of(new IntersectionInfo(pHit, this.center.subtract(pHit).normalize(), t));
    }
}