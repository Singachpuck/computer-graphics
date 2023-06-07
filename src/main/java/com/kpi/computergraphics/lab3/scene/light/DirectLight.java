package com.kpi.computergraphics.lab3.scene.light;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;
import java.util.Optional;

public class DirectLight implements Light {
    private final Vector3D direction;
    private final Color color;
    private final double intensity;

    public DirectLight(Vector3D direction, Color color, double intensity) {
        this.direction = direction.normalize();
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public Color checkColor(IntersectionInfo intersection) {
        var dotProduct = intersection.position().normalize().dotProduct(direction);
        var multiplier = dotProduct < 0 ? 0 : dotProduct;
        return new Color(
                intersection.color().r() * color.r() * multiplier * intensity,
                intersection.color().g() * color.g() * multiplier * intensity,
                intersection.color().b() * color.b() * multiplier * intensity
        );
    }

    @Override
    public boolean checkShadow(IntersectionInfo intersection, List<SceneObject> objects) {
        if (intersection == null) {
            return false;
        }
        var shadowRay = new Ray(intersection.position(), direction);
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
