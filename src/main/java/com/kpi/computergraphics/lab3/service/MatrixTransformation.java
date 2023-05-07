package com.kpi.computergraphics.lab3.service;

import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Vector3D;

import static com.kpi.computergraphics.lab3.model.base.Matrix.transformFromVector;

public class MatrixTransformation {
    public static Matrix translateMatrix(double x, double y, double z) {
        double[][] arr = {
                {1, 0, 0, x},
                {0, 0, 0, y},
                {0, 0, 0, z},
                {0, 0, 0, 1}
        };
        return new Matrix(arr);
    }

    public static Matrix scaleMatrix(double x, double y, double z) {
        double[][] arr = {
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}
        };
        return new Matrix(arr);
    }

    public static Matrix rotateXMatrix(double angle) {
        double[][] arr = {
                {1, 0, 0, 0},
                {0, Math.cos(angle), Math.sin(angle), 0},
                {0, -Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 0, 1}
        };
        return new Matrix(arr);
    }

    public static Matrix rotateYMatrix(double angle) {
        double[][] arr = {
                {Math.cos(angle), 0, -Math.sin(angle), 0},
                {0, 1, 0, 0},
                {Math.sin(angle), 0, Math.cos(angle), 0},
                {0, 0, 0, 1}
        };
        return new Matrix(arr);
    }

    public static Matrix rotateMatrix(double angleX, double angleY, double angleZ) {
        double[][] identityArr = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        var identityMatrix = new Matrix(identityArr);
        var matrix = rotateXMatrix(angleX).multiply(identityMatrix);
        matrix = rotateYMatrix(angleY).multiply(matrix);
        matrix = rotateZMatrix(angleZ).multiply(matrix);
        return matrix;
    }

    public static Matrix rotateZMatrix(double angle) {
        double[][] arr = {
                {Math.cos(angle), Math.sin(angle), 0, 0},
                {-Math.sin(angle), Math.cos(angle), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix(arr);
    }

    public static Vector3D transform(Vector3D vector, Matrix transformMatrix) {
        var vectorMatrix = transformFromVector(vector);
        return transformMatrix.multiply(vectorMatrix).toVector();
    }

    public static double transform(double scalar, Matrix transformMatrix) {
        double[][] scalarMatrix = {{scalar}, {0}, {0}, {0}};
        var vectorMatrix = new Matrix(scalarMatrix);
        return transformMatrix.multiply(vectorMatrix).get(0, 0);
    }
}
