package com.kpi.computergraphics.lab3.scene.lights;

import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

public class EnvironmentalLight {
    private Colour colour;
    private double intensity;
    private int samplesCount;

    public EnvironmentalLight(Colour colour, double intensity) {
        this.colour = colour;
        this.intensity = intensity;
        this.samplesCount = 1;
    }

    public EnvironmentalLight(Colour colour, double intensity, int samplesCount) {
        this.colour = colour;
        this.intensity = intensity;
        this.samplesCount = samplesCount;
    }

    private Vector3D createVectorToIntersect(Vector3D vector) {
        double theta = Math.random() * 2 * Math.PI;
        double phi = Math.random() * Math.PI;

        double x = Math.sin(phi) * Math.cos(theta);
        double y = Math.sin(phi) * Math.sin(theta);
        double z = Math.cos(phi);

        return vector.subtract(new Vector3D(vector.x() + x, vector.y() + y, vector.z() + z));
    }

    public Colour findColour(IntersectionInfo intersection) {
        Colour[] samples = new Colour[samplesCount];

        for (int i = 0; i < samplesCount; i++) {
            Vector3D vector = createVectorToIntersect(intersection.position());

            double dotProduct = intersection.normal().dotProduct(vector);

            double multiplier = (dotProduct < 0 ? 0 : dotProduct*intensity);

            samples[i] = new Colour(
                    multiplier * intersection.colour().red() * colour.red(),
                    multiplier * intersection.colour().green() * colour.green(),
                    multiplier * intersection.colour().blue() * colour.blue()
            );
        }

        double reds = 0;
        double greens = 0;
        double blues = 0;

        for (Colour sample : samples) {
            reds += sample.red();
            greens += sample.green();
            blues += sample.blue();
        }

        double averageRed = reds / samplesCount;
        double averageGreen = greens / samplesCount;
        double averageBlue = blues / samplesCount;

        return new Colour(averageRed, averageGreen, averageBlue);
    }

    public boolean isShadow(IntersectionInfo intersection, SceneObject sceneObject) {
        return false;
    }
}

