package com.kpi.computergraphics.lab3.model.base;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Matrix {

    private double[][] array;

    public static Matrix fromVector(Vector3D vector) {
        double[][] vectorArray = {{vector.x()}, {vector.y()}, {vector.z()}};
        return new Matrix(vectorArray);
    }

    public static Matrix transformFromVector(Vector3D vector) {
        double[][] vectorArray = {{vector.x()}, {vector.y()}, {vector.z()}, {0}};
        return new Matrix(vectorArray);
    }

    public double get(int row, int col) {
        return array[row][col];
    }

    public Matrix multiply(Matrix secondMatrix) {
        if (array[0].length != secondMatrix.array.length) {
            throw new IllegalArgumentException("Matrix can't be multiplied, wrong dimensions");
        }

        var result = new Matrix(new double[array.length][secondMatrix.array[0].length]);

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < secondMatrix.array[0].length; j++) {
                double sum = 0;

                for (int k = 0; k < array[0].length; k++) {
                    sum += array[i][k] * secondMatrix.array[k][j];
                }

                result.array[i][j] = sum;
            }
        }

        return result;
    }

    public Vector3D toVector() {
        return new Vector3D(array[0][0], array[1][0], array[2][0]);
    }
}
