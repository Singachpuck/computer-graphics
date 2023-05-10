package com.kpi.computergraphics.lab3.model.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    Matrix matrix1 = new Matrix(new double[][] {
            {1, 2},
            {3, 4},
    });

    Matrix matrix2 = new Matrix(new double[][] {
        {5, 6},
        {7, 8},
    });

    Matrix matrix3 = new Matrix(new double[][]{
            {1}
    });

    Matrix expected = new Matrix(new double[][]{
            {19, 22},
            {43, 50},
    });

    @Test
    void multiplyTest() {
        Matrix result = matrix1.multiply(matrix2);

        for (int i = 0; i < 2; i++) {
            assertArrayEquals(expected.getArray()[i], result.getArray()[i]);
        }
    }

    @Test
    void fromVectorTest() {
        Vector3D vector = new Vector3D(1, 2, 3);
        Matrix matrix = Matrix.fromVector(vector);
        double[][] array = matrix.getArray();
        assertEquals(1, array[0][0]);
        assertEquals(2, array[1][0]);
        assertEquals(3, array[2][0]);
    }

    @Test
    void errorTest() {
        assertThrows(RuntimeException.class, () -> matrix1.multiply(matrix3));
    }
}