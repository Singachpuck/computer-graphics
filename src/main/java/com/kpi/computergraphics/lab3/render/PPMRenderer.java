package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;

import java.io.IOException;
import java.io.OutputStream;

class PPMRenderer extends Renderer {

    private final OutputStream os;

    PPMRenderer(Scene scene, OutputStream os) {
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
            var color = new Color(0, 0, 0);

            for (var light : scene.lights) {
                if (light.checkShadow(intersection, scene.objects)) continue;
                var additionalColor = light.checkColor(intersection);
                color = new Color(
                        color.r() + additionalColor.r(),
                        color.g() + additionalColor.g(),
                        color.b() + additionalColor.b());
            }
            intersection.color(color);
            toPrint = colorIntensity(color.r()) + " " + colorIntensity(color.g()) + " " + colorIntensity(color.b()) + "\n";
        }

        try {
            os.write(toPrint.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int colorIntensity(double colorRate) {
        return colorRate > 1 ? 255 : (int) Math.round(colorRate * 255);
    }

    @Override
    protected void rowEnd() {
    }

    @Override
    protected void renderEnd() {

    }
}
