package com.kpi.computergraphics.lab3.model.optimization;

import com.kpi.computergraphics.lab3.base.LimitedAxis;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.optimization.kd.KdInternal;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import com.kpi.computergraphics.lab3.optimization.kd.KdTreeFactory;

import com.kpi.computergraphics.lab3.scene.objects.Triangle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KdInternalTest {
    @Test
    void findIntersection_findIntersectionEmptyTest() {
        KdTreeFactory.KdNode left = new KdTreeFactory.KdLeaf(new ArrayList<>());
        KdTreeFactory.KdNode right = new KdTreeFactory.KdLeaf(new ArrayList<>());
        KdInternal kdInternal = new KdInternal(left, right, LimitedAxis.Axis.X, 0.0);

        Ray ray = new Ray(new Vector3D(0, 0, 0), new Vector3D(1, 0, 0));
        List<SceneObject> avoid = new ArrayList<>();
        boolean closest = false;

        Optional<IntersectionInfo> intersection = kdInternal.findIntersection(ray, avoid, closest);

        assertTrue(intersection.isEmpty());
    }

    @Test
    void findIntersection_findIntersectionTest() {
        Triangle triangle = new Triangle(
                new Vector3D(0, 200, 200),
                new Vector3D(-200, 400, 205),
                new Vector3D(200, 400, 195)
        );
        List<SceneObject> objects = new ArrayList<>();
        objects.add(triangle);

        KdTreeFactory.KdNode left = new KdTreeFactory.KdLeaf(objects);
        KdTreeFactory.KdNode right = new KdTreeFactory.KdLeaf(new ArrayList<>());
        KdInternal kdInternal = new KdInternal(left, right, LimitedAxis.Axis.X, 0.0);

        Ray ray = new Ray(
                new Vector3D(0, 250, 100),
                new Vector3D(0, 250, 300));
        List<SceneObject> avoid = new ArrayList<>();
        boolean closest = false;

        Optional<IntersectionInfo> intersection = kdInternal.findIntersection(ray, avoid, closest);

        assertTrue(intersection.isPresent());
        assertEquals(triangle, intersection.get().object());
    }
}
