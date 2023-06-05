package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.Scene;

import java.io.IOException;
import java.io.OutputStream;

public class PPMRenderer extends Renderer {

    private final OutputStream os;

    public PPMRenderer(Scene scene, OutputStream os) {
        super(scene);
        this.os = os;
    }

    @Override
    protected void renderStart() {
        final StringBuilder sb = new StringBuilder();
        sb.append("P3")
                .append('\n')
                .append(scene.camera.horizontalResolution)
                .append(' ')
                .append(scene.camera.verticalResolution)
                .append('\n')
                .append(255)
                .append('\n');
        try {
            os.write(sb.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void rowStart() {

    }

    @Override
    protected void intersect(IntersectionInfo intersection) {
        final String toPrint;
        if (intersection == null) {
            toPrint = "0 0 0\n";
        } else {
            final double lightRate = intersection.normal().dotProduct(scene.light);
            final double grayRate = lightRate < 0 ? 0 : Math.round(lightRate * 255);
            toPrint = grayRate + " " + grayRate + " " + grayRate + "\n";
        }

        try {
            os.write(toPrint.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void rowEnd() {
    }

    @Override
    protected void renderEnd() {

    }
}
