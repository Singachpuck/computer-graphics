package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.scene.ObjectGroupFactory;
import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.io.IOException;
import java.io.OutputStream;

class OutputStreamRenderer extends Renderer {

    private final OutputStream os;

    OutputStreamRenderer(Scene<SceneObject> scene, ObjectGroupFactory ogf, OutputStream os) {
        super(scene, ogf);
        this.os = os;
    }

    @Override
    protected void renderStart() {

    }

    @Override
    protected void rowStart() {

    }

    @Override
    protected void intersect(IntersectionInfo intersection) {
        char toPrint;
        if (intersection == null) {
            toPrint = ' ';
        } else {
            final double lightRate = intersection.normal().dotProduct(scene.light);
            if (lightRate < 0) {
                toPrint = ' ';
            } else if (lightRate < 0.2) {
                toPrint = '.';
            } else if (lightRate < 0.5) {
                toPrint = '*';
            } else if (lightRate < 0.8) {
                toPrint = 'O';
            } else {
                toPrint = '#';
            }
        }

        try {
            os.write(toPrint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void rowEnd() {
        try {
            os.write('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void renderEnd() {

    }
}
