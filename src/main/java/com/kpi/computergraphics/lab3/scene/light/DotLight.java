package com.kpi.computergraphics.lab3.scene.light;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;
import java.util.Optional;

public class DotLight implements Light {
    private final Vector3D dot;
    private final Color color;
    private final double intensity;

    public DotLight(Vector3D dot, Color color, double intensity) {
        this.dot = dot;
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public Color checkColor(IntersectionInfo intersection) {
        var vector = intersection.position().subtract(dot);
        var length = vector.length();
        var dotProduct = intersection.normal().dotProduct(vector);
        var multiplier = dotProduct < 0 ? 0 : dotProduct;
        return new Color(
                (intersection.color().r() * color.r() * multiplier * this.intensity) / (length * length),
                (intersection.color().g() * color.g() * multiplier * this.intensity) / (length * length),
                (intersection.color().b() * color.b() * multiplier * this.intensity) / (length * length)
        );
    }

    @Override
    public boolean checkShadow(IntersectionInfo intersection, List<SceneObject> objects) {
        if (intersection == null) {
            return false;
        }
        var shadowRay = new Ray(
                intersection.position(),
                dot.subtract(intersection.position()));
        for (SceneObject object : objects) {
            if (object == intersection.object()) {
                continue;
            }
            Optional<IntersectionInfo> shadowIntersection = object.findIntersection(shadowRay);
            if (shadowIntersection.isPresent()) {
                return true;
            }
        }
        return false;
    }
}
