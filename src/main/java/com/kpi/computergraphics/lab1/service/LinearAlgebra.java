package com.kpi.computergraphics.lab1.service;

import com.kpi.computergraphics.lab1.model.base.Line;
import com.kpi.computergraphics.lab1.model.base.Plane;
import com.kpi.computergraphics.lab1.model.base.Point3D;
import com.kpi.computergraphics.lab1.model.base.Vector3D;
import com.kpi.computergraphics.lab1.model.object.Circle3D;
import com.kpi.computergraphics.lab1.model.object.Sphere;

public class LinearAlgebra {

    public static final double FLOAT_EPSILON = 1e-5;

    public static Point3D[] intersects(Line line, Sphere sphere) {
        final Point3D lp = line.getPoint();
        final Vector3D ldv = line.getDirection();
        final Vector3D sclpVector = new Vector3D(sphere.getCenter(), lp);
        final float radius = sphere.getRadius();

        final double lvSclpScalar = scalarMultiply(sclpVector, ldv);
        final double lvSquared = scalarMultiply(ldv, ldv);
        final double sclpSquared = scalarMultiply(sclpVector, sclpVector);

        final double b = 2 * lvSclpScalar;

        final double D = b * b - 4 * lvSquared * (sclpSquared - Math.pow(radius, 2));

        if (D < 0) {
            return null;
        }

        final double t1 = (-b + Math.sqrt(D)) / (2 * lvSquared);
        final double t2 = (-b - Math.sqrt(D)) / (2 * lvSquared);

        final Point3D p1 = new Point3D();
        p1.setX((float) (ldv.getX() * t1 + lp.getX()));
        p1.setY((float) (ldv.getY() * t1 + lp.getY()));
        p1.setZ((float) (ldv.getZ() * t1 + lp.getZ()));

        final Point3D p2 = new Point3D();
        p2.setX((float) (ldv.getX() * t2 + lp.getX()));
        p2.setY((float) (ldv.getY() * t2 + lp.getY()));
        p2.setZ((float) (ldv.getZ() * t2 + lp.getZ()));

        return new Point3D[]{p1, p2};
    }

    public static Point3D[] intersects(Line line, Circle3D circle3D) {
        final Point3D[] intersectCirclePlane = intersects(line, circle3D.getPlane());

        if (intersectCirclePlane != null && intersectCirclePlane.length == 1) {
            if (distance(intersectCirclePlane[0], circle3D.getCenter()) <= circle3D.getRadius()) {
                return intersectCirclePlane;
            }
        }
        return null;
    }

    public static Point3D[] intersects(Line line, Plane plane) {
        final Point3D linePoint = line.getPoint();
        final Vector3D lineDirection = line.getDirection();
        final Point3D planePoint = plane.getPoint();
        final Vector3D planeNormal = plane.getNormal();

        if (Math.abs(scalarMultiply(lineDirection, planeNormal)) < FLOAT_EPSILON) {
            return null;
        }

        final Vector3D diff = new Vector3D(planePoint, linePoint);
        final float tmp1 = (float) scalarMultiply(diff, planeNormal);
        final float tmp2 = (float) scalarMultiply(lineDirection, planeNormal);
        final float k = tmp1 / tmp2;

        final Point3D intersection = moveByVector(linePoint, invert(constantMultiply(lineDirection, k)));
        return new Point3D[]{intersection};
    }

    public static boolean isDependant(Vector3D v1, Vector3D v2) {
        final float epsilon = (float) 1e-5;
        final float k1 = v1.getX() / v2.getX();
        final float k2 = v1.getY() / v2.getY();
        final float k3 = v1.getZ() / v2.getZ();

        return Math.abs(k1 - k2) < epsilon && Math.abs(k1 - k3) < epsilon;
    }

    public static double scalarMultiply(Vector3D v1, Vector3D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    public static Vector3D vectorMultiply(Vector3D v1, Vector3D v2) {
        float x = v1.getY() * v2.getZ() - v1.getZ() * v2.getY();
        float y = -(v1.getX() * v2.getZ() - v1.getZ() * v2.getX());
        float z = v1.getZ() * v2.getY() - v1.getY() * v2.getX();
        return new Vector3D(x, y, z);
    }

    public static Point3D moveByVector(Point3D p, Vector3D v) {
        return new Point3D(p.getX() + v.getX(), p.getY() + v.getY(), p.getZ() + v.getZ());
    }

    public static Point3D moveByVectorAtDistance(Point3D p, Vector3D v, float distance) {
        final float k = (float) (distance / Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2)));
        return moveByVector(p, constantMultiply(v, k));
    }

    public static double length(Vector3D v1) {
        return Math.sqrt(scalarMultiply(v1, v1));
    }

    public static double distance(Point3D p1, Point3D p2) {
        return length(new Vector3D(p1, p2));
    }

    public static Vector3D constantMultiply(Vector3D v, float constant) {
        final Vector3D resV = new Vector3D();
        resV.setX(v.getX() * constant);
        resV.setY(v.getY() * constant);
        resV.setZ(v.getZ() * constant);
        return resV;
    }

    public static Vector3D invert(Vector3D v) {
        return new Vector3D(-v.getX(), -v.getY(), -v.getZ());
    }
}
