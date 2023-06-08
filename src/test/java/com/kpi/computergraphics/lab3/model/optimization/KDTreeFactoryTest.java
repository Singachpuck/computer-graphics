package com.kpi.computergraphics.lab3.model.optimization;

import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.optimization.kd.KdTreeFactory;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import com.kpi.computergraphics.lab3.scene.objects.Sphere;
import com.kpi.computergraphics.lab3.scene.objects.Triangle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class KDTreeFactoryTest {

    @Test
    public void getKDTree_BoundsTest() {
        KdTreeFactory kdTreeFactory = new KdTreeFactory(4);
        List<SceneObject> objects = new ArrayList<>();
        Triangle object = new Triangle(
                new Vector3D(0, 200, 200),
                new Vector3D(-200, 400, 205),
                new Vector3D(200, 400, 195)
        );

        objects.add(object);

        KdTreeFactory.KdNode kdTree = kdTreeFactory.getKDTree(objects, 5);

        assertNotNull(kdTree);
        assertTrue(kdTree instanceof KdTreeFactory.KdLeaf);

        assertEquals(object.getBound().getX().getStart(), kdTree.getBound().getX().getStart());
        assertEquals(object.getBound().getY().getStart(), kdTree.getBound().getY().getStart());
        assertEquals(object.getBound().getZ().getStart(), kdTree.getBound().getZ().getStart());

        assertEquals(object.getBound().getX().getEnd(), kdTree.getBound().getX().getEnd());
        assertEquals(object.getBound().getY().getEnd(), kdTree.getBound().getY().getEnd());
        assertEquals(object.getBound().getZ().getEnd(), kdTree.getBound().getZ().getEnd());
    }

    @Test
    void findIntersectionTest() {
        List<SceneObject> objects = new ArrayList<>();
        objects.add(new Triangle(
                new Vector3D(0, 200, 200),
                new Vector3D(-200, 400, 205),
                new Vector3D(200, 400, 195)));
        KdTreeFactory kdTreeFactory = new KdTreeFactory(1);
        KdTreeFactory.KdNode kdTree = kdTreeFactory.getKDTree(objects, 3);

        Ray ray = new Ray(
                new Vector3D(0, 250, 100),
                new Vector3D(0, 250, 300));

        Optional<IntersectionInfo> intersection = kdTree.findIntersection(ray, new ArrayList<>(), true);

        assertTrue(intersection.isPresent());
        assertEquals(130, intersection.get().length(), 1);
        assertEquals(objects.get(0), intersection.get().object());
    }

    @Test
    public void getKDTree_LargeObjectListTest() {
        List<SceneObject> objects = new ArrayList<>();
        for(int i = 0; i<100; i++){
            objects.add(new Sphere(
                    new Vector3D(i,i,i),i)
            );
        }

        double maxPrimitives = 5;
        KdTreeFactory kdTreeFactory = new KdTreeFactory(maxPrimitives);

        KdTreeFactory.KdNode kdTree = kdTreeFactory.getKDTree(objects, 10);

        assertNotNull(kdTree);
    }
}

