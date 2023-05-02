package com.kpi.computergraphics.lab3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Vector3D {

    private float x;
    private float y;
    private float z;

    public Vector3D add(Vector3D vector) {
        return new Vector3D(x + vector.x, y + vector.y, z + vector.z);
    }

    public Vector3D subtract(Vector3D vector) {
        return new Vector3D(x - vector.x, y - vector.y, z - vector.z);
    }

    public Vector3D multiply(float num) {
        return new Vector3D(x * num, y * num, z * num);
    }

    public float dotProduct(Vector3D vector) {
        return x * vector.x + y * vector.x + z * vector.z;
    }

    public Vector3D crossProduct(Vector3D vector) {
        return new Vector3D(
                y * vector.z - z * vector.y,
                z * vector.x - x * vector.z,
                x * vector.y - y * vector.x);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D normalize() {
        return multiply(1 / length());
    }

    public Vector3D negate() {
        return new Vector3D(-x, -y, -z);
    }
}
