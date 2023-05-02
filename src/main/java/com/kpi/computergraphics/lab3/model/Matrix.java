package com.kpi.computergraphics.lab3.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Matrix {

    private final float[][] table;

    public float at(int i, int j) {
        return table[i][j];
    }

    public Matrix multiply(Matrix m2) {
        if (this.table.length != m2.table[0].length) {
            throw new IllegalArgumentException("Matrices are not compatible");
        }
        final Matrix result = new Matrix(new float[this.table.length][m2.table[0].length]);
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[0].length; j++) {
                float sum = 0;
                for (int k = 0; k < m2.table.length; k++) {
                    sum += this.table[i][k] * m2.table[k][j];
                }
                result.table[i][j] = sum;
            }
        }
        return result;
    }

    public static Matrix fromVector(Vector3D vector) {
        return new Matrix(new float[][] {
                new float[]{vector.getX()},
                new float[]{vector.getY()},
                new float[]{vector.getZ()}
        });
    }

    public Vector3D toVector() {
        return new Vector3D(this.table[0][0], this.table[1][0], this.table[2][0]);
    }
}
