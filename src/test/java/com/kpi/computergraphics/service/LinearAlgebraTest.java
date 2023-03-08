package com.kpi.computergraphics.service;

import com.kpi.computergraphics.model.base.Line;
import com.kpi.computergraphics.model.base.Plane;
import com.kpi.computergraphics.model.base.Point3D;
import com.kpi.computergraphics.model.base.Vector3D;
import com.kpi.computergraphics.model.object.Circle3D;
import com.kpi.computergraphics.model.object.Sphere;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LinearAlgebraTest {

    private final Random random = new Random();

    static Stream<Arguments> lineSphereIntersects() {
        return Stream.of(
                Arguments.of(new Line(new Point3D(4, 5, 6), new Vector3D(3, 3, 3)),
                        new Sphere(new Point3D(0, 0, 0), 4)),
                Arguments.of(new Line(new Point3D(10, 5, 2), new Vector3D(2, 1, 0)),
                        new Sphere(new Point3D(0, 0, 0), 3))
        );
    }

    static Stream<Arguments> lineSphereNotIntersects() {
        return Stream.of(
                Arguments.of(new Line(new Point3D(4, 5, 6), new Vector3D(0, 3, 3)),
                        new Sphere(new Point3D(10, 0, 0), 4)),
                Arguments.of(new Line(new Point3D(10, 5, 2), new Vector3D(2, 1, 0)),
                        new Sphere(new Point3D(0, 0, 0), 0.1F))
        );
    }

    static Stream<Arguments> linePlaneIntersects() {
        return Stream.of(
                Arguments.of(new Line(new Point3D(0, 1, 0), new Vector3D(1, 2, 0)),
                        new Plane(new Vector3D(2, 3, -1), new Point3D(0, 0, -6)))
        );
    }

    static Stream<Arguments> linePlaneNotIntersects() {
        return Stream.of(
                Arguments.of(new Line(new Point3D(0, 1, 0), new Vector3D(0, 1, 0)),
                        new Plane(new Vector3D(1, 0, 0), new Point3D(0, 0, -6)))
        );
    }

    static Stream<Arguments> lineCircleIntersect() {
        return Stream.of(
                Arguments.of(new Line(new Point3D(0, 1, 0), new Vector3D(1, 2, 0)),
                        new Circle3D(new Plane(new Vector3D(2, 3, -1), new Point3D(0, 0, -6)),
                                new Point3D(0, 0, -6), 10))
        );
    }

    static Stream<Arguments> lineCircleNotIntersect() {
        return Stream.of(
                Arguments.of(new Line(new Point3D(0, 1, 0), new Vector3D(1, 2, 0)),
                        new Circle3D(new Plane(new Vector3D(2, 3, -1), new Point3D(0, 0, -6)),
                                new Point3D(0, 0, -6), 5))
        );
    }

    @ParameterizedTest
    @MethodSource(value = "lineSphereIntersects")
    void intersectsLineSphere(Line line, Sphere sphere) {
        Point3D[] point3DS = LinearAlgebra.intersects(line, sphere);
        assertNotNull(point3DS);
        assertEquals(2, point3DS.length);
    }

    @ParameterizedTest
    @MethodSource(value = "lineSphereNotIntersects")
    void notIntersectsLineSphere(Line line, Sphere sphere) {
        Point3D[] point3DS = LinearAlgebra.intersects(line, sphere);
        assertNull(point3DS);
    }

    @ParameterizedTest
    @MethodSource(value = "linePlaneIntersects")
    void intersectsLinePlane(Line line, Plane plane) {
        Point3D[] point3DS = LinearAlgebra.intersects(line, plane);
        assertNotNull(point3DS);
        assertEquals(1, point3DS.length);
    }

    @ParameterizedTest
    @MethodSource(value = "linePlaneNotIntersects")
    void notIntersectsLinePlane(Line line, Plane plane) {
        Point3D[] point3DS = LinearAlgebra.intersects(line, plane);
        assertNull(point3DS);
    }

    @ParameterizedTest
    @MethodSource(value = "lineCircleIntersect")
    void intersectLineCircle3D(Line line, Circle3D circle3D) {
        Point3D[] point3DS = LinearAlgebra.intersects(line, circle3D);
        assertNotNull(point3DS);
        assertEquals(1, point3DS.length);
    }

    @ParameterizedTest
    @MethodSource(value = "lineCircleNotIntersect")
    void notIntersectLineCircle3D(Line line, Circle3D circle3D) {
        Point3D[] point3DS = LinearAlgebra.intersects(line, circle3D);
        assertNull(point3DS);
    }

    @RepeatedTest(value = 10)
    void pointMoving() {
        Point3D toMove = new Point3D(1, 1, 1);
        Vector3D direction = new Vector3D(random.nextFloat(), random.nextFloat(), random.nextFloat());
        float distanceToMove = 10.12345F;

        Point3D moved = LinearAlgebra.moveByVectorAtDistance(toMove, direction, distanceToMove);
        float newDistance = (float) LinearAlgebra.distance(toMove, moved);

        assertTrue(Math.abs(distanceToMove - newDistance) <= LinearAlgebra.FLOAT_EPSILON);
    }
}