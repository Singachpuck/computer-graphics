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

    public Colour findColour(IntersectionInfo intersection) {
        Vector3D vector = intersection.position().subtract(this.direction);

        double d = vector.length();

        double dotProduct = intersection.normal().dotProduct(vector);

        double multiplier = dotProduct < 0 ? 0 : dotProduct*intensity;

        // need to add Colour's to IntersectionInfo and scene.objects' findIntersection() methods
        return new Colour(
                (intersection.colour().red() * colour.red() * multiplier) / Math.pow(d, 2),
                (intersection.colour().green() * colour.green() * multiplier) / Math.pow(d, 2,
                (intersection.colour().blue() * colour.blue() * multiplier) / Math.pow(d, 2
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
