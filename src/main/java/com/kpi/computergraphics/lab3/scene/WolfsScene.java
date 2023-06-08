package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.light.AmbientLight;
import com.kpi.computergraphics.lab3.scene.light.DirectLight;
import com.kpi.computergraphics.lab3.scene.light.DotLight;
import com.kpi.computergraphics.lab3.scene.objects.PolygonMesh;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.kpi.computergraphics.lab3.base.MatrixTransformation.rotateMatrix;
import static com.kpi.computergraphics.lab3.base.MatrixTransformation.scaleMatrix;
import static com.kpi.computergraphics.lab3.scene.ReaderOBJ.readStream;

public class WolfsScene implements PresetScene<SceneObject> {
    @Override
    public Scene<SceneObject> getScene() {
        var camera = new Camera(
                new Vector3D(0, 100, -500),
                new Vector3D(0, 0, 1),
                Math.PI / 3, 800, 600);
        var dirLightWhite = new DirectLight(
                new Vector3D(1, 1, -1),
                new Color(1, 1, 1),
                0.7
        );
        var dirLightBlue = new DirectLight(
                new Vector3D(1, -1, -1),
                new Color(0, 0, 1),
                0.5
        );
        var dirLightOrange = new DirectLight(
                new Vector3D(-1, 1, -1),
                new Color(1, 0.5, 0),
                0.5
        );
        var dotLight = new DotLight(
                new Vector3D(0, 0, 0),
                new Color(1, 1, 1),
                2000
        );
        var ambientLight = new AmbientLight(
                new Color(1, 0, 0),
                1000
        );

        try {
            InputStream inputWolf = new FileInputStream("src/main/resources/wolf.obj");
            InputStream inputGrandWolf = new FileInputStream("src/main/resources/wolf2.obj");
            PolygonMesh wolfMesh = readStream(inputWolf);
            PolygonMesh grandWolfMesh = readStream(inputGrandWolf);
            wolfMesh.transform(rotateMatrix(0, 130, 0));
            grandWolfMesh.transform(scaleMatrix(0.5, 0.5, 0.5));
            List<SceneObject> wolfObjects = new ArrayList<>();
            wolfObjects.addAll(wolfMesh.getObjects());
            wolfObjects.addAll(grandWolfMesh.getObjects());
            return new Scene<>(wolfObjects, camera, List.of(dirLightWhite, dirLightBlue, dirLightOrange, dotLight, ambientLight));

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
