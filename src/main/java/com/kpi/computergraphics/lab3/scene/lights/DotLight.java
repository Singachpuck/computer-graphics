package com.kpi.computergraphics.lab3.scene.lights;

import com.kpi.computergraphics.lab3.base.Vector3D;

public class DotLight {
    // change
    private Vector3D position;
    private double intensity;

    private Colour colour;

    public DotLight(Vector3D position, double intensity, Colour colour) {
        this.position = position;
        this.intensity = intensity;
        this.colour = colour;
    }

    public Vector3D position() {
        return position;
    }
    public double intensity() {
        return intensity;
    }
    public Colour colour() {
        return colour;
    }

    public void changeIntensity(double newIntensity){
        intensity=intensity;
    }
    public void changePosition(Vector3D position) {
        this.position = position;
    }
    public void changeColour(Colour colour) {
        this.colour = colour;
    }
}
