package com.kpi.computergraphics.lab3.scene.light;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;

public class AmbientLight implements Light {
    private final Color color;
    private final double intensity;

    public AmbientLight(Color color, double intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public Color checkColor(IntersectionInfo intersection) {
        int count = 100;
        var colorSum = new Color(0, 0, 0);
        var position = intersection.position();

        for (int i = 0; i < count; i++) {
            var theta = Math.random() * 2 * Math.PI;
            var phi = Math.random() * Math.PI;
            var x = Math.sin(phi) * Math.cos(theta);
            var y = Math.sin(phi) * Math.sin(theta);
            var z = Math.cos(phi);
            var vector = position.subtract(new Vector3D(
                    position.x() + x,
                    position.y() + y,
                    position.z() + z
            ));
            var dotProduct = position.normalize().dotProduct(vector);
            var multiplier = dotProduct < 0 ? 0 : dotProduct;
            var addColor = new Color(
                    intersection.color().r() * color.r() * multiplier * intensity,
                    intersection.color().g() * color.g() * multiplier * intensity,
                    intersection.color().b() * color.b() * multiplier * intensity
            );
            colorSum = new Color(
                    colorSum.r() + addColor.r(),
                    colorSum.g() + addColor.g(),
                    colorSum.b() + addColor.b()
            );
        }
        return new Color(
                colorSum.r() / count,
                colorSum.g() / count,
                colorSum.b() / count
        );
    }

    @Override
    public boolean checkShadow(IntersectionInfo intersectionInfo, List<SceneObject> objects) {
        return false;
    }
}
