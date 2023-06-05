package com.kpi.computergraphics.lab3.service;

import com.kpi.computergraphics.lab3.base.Matrix;
import com.kpi.computergraphics.lab3.base.MatrixTransformation;
import com.kpi.computergraphics.lab3.base.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTransformationTest {

    Vector3D vector3D = new Vector3D(1, 2, 3);

    @Test
    void translateTest() {
        Matrix translatedM = MatrixTransformation.translateMatrix(1, 2, 3);
        Vector3D translated = MatrixTransformation.transform(vector3D, translatedM);
        assertEquals(2, translated.x(), 0.001);
        assertEquals(4, translated.y(), 0.001);
        assertEquals(6, translated.z(), 0.001);
    }

    @Test
    void scaleTest() {
        Matrix translatedM = MatrixTransformation.scaleMatrix(1, 2, 3);
        Vector3D translated = MatrixTransformation.transform(vector3D, translatedM);
        assertEquals(1, translated.x(), 0.001);
        assertEquals(4, translated.y(), 0.001);
        assertEquals(9, translated.z(), 0.001);
    }

    @Test
    void rotationTest() {
        Matrix rotationMatrix = MatrixTransformation.rotateMatrix(
                Math.PI / 2,
                Math.PI / 2,
                Math.PI / 2
        );
        Vector3D translated = MatrixTransformation.transform(vector3D, rotationMatrix);
        assertEquals(3, translated.x(), 0.001);
        assertEquals(-2, translated.y(), 0.001);
        assertEquals(1, translated.z(), 0.001);
    }
}
