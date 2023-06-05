package com.kpi.computergraphics.lab3.model.object;

import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.Triangle;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    Vector3D v1 = new Vector3D(0, 0, 0);
    Vector3D v2 = new Vector3D(1, 0, 0);
    Vector3D v3 = new Vector3D(0, 1, 0);
    Triangle triangle = new Triangle(v1, v2, v3);

    @Test
    void intersectionTest() {
        Ray ray = new Ray(new Vector3D(0, 0, 1), new Vector3D(0, 0, 1));
        assertTrue(triangle.findIntersection(ray).isEmpty());
    }

    @Test
    void intersectionTest2() {
        Ray ray = new Ray(new Vector3D(0.5, 0.5, 1), new Vector3D(0, 0, -1));
        Optional<IntersectionInfo> hit = triangle.findIntersection(ray);
        assertFalse(hit.isEmpty());
        Vector3D position = hit.get().position();
        assertEquals(1, hit.get().length());
        assertEquals(0.5, position.x());
        assertEquals(0.5, position.y());
        assertEquals(0, position.z());
    }

    @Test
    void intersectionTest3() {
        Ray ray = new Ray(new Vector3D(2, 2, 1), new Vector3D(0, 0, -1));
        assertTrue(triangle.findIntersection(ray).isEmpty());
    }

    @Test
    void intersectionTest4() {
        Ray ray = new Ray(new Vector3D(0.5, 0.5, 1), new Vector3D(0, 0, 1));
        assertTrue(triangle.findIntersection(ray).isEmpty());
    }
}
