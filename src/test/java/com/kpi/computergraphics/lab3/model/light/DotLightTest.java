package com.kpi.computergraphics.lab3.model.light;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.light.DotLight;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.Triangle;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DotLightTest {
    Color color = new Color(1, 0.3, 0.2);
    Ray ray = new Ray(
            new Vector3D(0, 250, 100),
            new Vector3D(0, 250, 300)
    );

    DotLight dotLight = new DotLight(ray.vector(), color, 1000);

    Triangle triangle = new Triangle(
            new Vector3D(0, 200, 200),
            new Vector3D(-200, 400, 205),
            new Vector3D(200, 400, 195)
    );

    @Test
    void checkColorTest(){
        Optional<IntersectionInfo> intersection = triangle.findIntersection(ray);
        if(intersection.isPresent()){
            if(!dotLight.checkShadow(intersection.get(), List.of(triangle))) {
                Color resultColor = dotLight.checkColor(intersection.get());
                assertTrue(resultColor.r() > color.r());
                assertTrue(resultColor.g() > color.g());
                assertTrue(resultColor.b() > color.b());
            }
        }
    }

    @Test
    void checkShadowTest(){
        Optional<IntersectionInfo> inter = triangle.findIntersection(ray);
        inter.ifPresent(intersectionInfo -> assertFalse(dotLight.checkShadow(intersectionInfo, List.of(triangle))));
    }
}
