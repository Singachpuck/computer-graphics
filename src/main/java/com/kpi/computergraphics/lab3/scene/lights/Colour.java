package com.kpi.computergraphics.lab3.scene.lights;

public class Colour {
    private double red;
    private double green;
    private double blue;

    public Colour(double red, double green, double blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double red() {
        return red;
    }

    public double green() {
        return green;
    }

    public double blue() {
        return blue;
    }
}
