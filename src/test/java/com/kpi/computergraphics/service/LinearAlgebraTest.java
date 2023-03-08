package com.kpi.computergraphics.service;

import com.kpi.computergraphics.model.base.Line;
import com.kpi.computergraphics.model.base.Point3D;
import com.kpi.computergraphics.model.base.Vector3D;
import com.kpi.computergraphics.model.object.Sphere;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LinearAlgebraTest {


    static Stream<Arguments> lineSphereIntersects() {
        return Stream.of(
                Arguments.of(new Line(new Point3D(4, 5, 6), new Vector3D(3, 3, 3)),
                        new Sphere(new Point3D(0, 0, 0), 4)),
                Arguments.of(new Line(new Point3D(10, 5, 2), new Vector3D(2, 1, 0)),
                        new Sphere(new Point3D(0, 0, 0), 3))
        );
    }

    @ParameterizedTest
    @MethodSource(value = "lineSphereIntersects")
    void intersects(Line line, Sphere sphere) {
        Point3D[] point3DS2 = LinearAlgebra.intersects(line, sphere);

        assertNotNull(point3DS2);
    }
}