package com.kpi.computergraphics.lab2.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D add(Vector3D vector) {
        return new Vector3D(x + vector.x, y + vector.y, z + vector.z);
    }

    public Vector3D subtract(Vector3D vector) {
        return new Vector3D(x - vector.x, y - vector.y, z - vector.z);
    }

    public Vector3D multiply(double num) {
        return new Vector3D(x * num, y * num, z * num);
    }

    public double dotProduct(Vector3D vector) {
        return x * vector.x + y * vector.x + z * vector.z;
    }

    public Vector3D crossProduct(Vector3D vector) {
        return new Vector3D(
                y * vector.z - z * vector.y,
                z * vector.x - x * vector.z,
                x * vector.y - y * vector.x);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D normalize() {
        return multiply(1 / length());
    }

    public Vector3D negate() {
        return new Vector3D(-x, -y, -z);
    }

    public double radsAngleWith(Vector3D vector) {
        return Math.acos(dotProduct(vector)) / (length() * vector.length());
    }

    public double x() {
        return x;
    }

    public void x(double x) {
        this.x = x;
    }

    public double y() {
        return y;
    }

    public void y(double y) {
        this.y = y;
    }

    public double z() {
        return z;
    }

    public void z(double z) {
        this.z = z;
    }
}
