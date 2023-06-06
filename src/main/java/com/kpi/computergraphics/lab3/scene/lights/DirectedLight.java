package com.kpi.computergraphics.lab3.scene.lights;

import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

public class DirectedLight {
    private Vector3D direction;
    private Colour colour;
    private double intensity;

    public DirectedLight(Vector3D direction, Colour colour, double intensity) {
        this.direction = direction;
        this.colour = colour;
        this.intensity = intensity;
    }

    public Colour findColour(IntersectionInfo hit) {
        Vector3D vector = hit.position().subtract(this.direction);

        double d = vector.length();

        double dotProduct = hit.normal().dotProduct(vector);

        double colorMultiplier = dotProduct < 0 ? 0 : dotProduct;

        return new Colour(
                (hit.colour().red() * colour.red() * colorMultiplier * intensity) / (d * d),
                (hit.colour().green() * colour.green() * colorMultiplier * intensity) / (d * d),
                (hit.colour().blue() * colour.blue() * colorMultiplier * intensity) / (d * d)
        );
    }

    public boolean isShadow(IntersectionInfo intersection, SceneObject sceneObject) {
        if (intersection == null)
            return false;

        Vector3D baseVector = this.direction.subtract(intersection.position());
        Ray shadow = new Ray(
                intersection.position().add(baseVector.normalize().multiply(0.0000000001)),
                baseVector
        );

        return sceneObject.findIntersection(shadow).isPresent();
    }
}
