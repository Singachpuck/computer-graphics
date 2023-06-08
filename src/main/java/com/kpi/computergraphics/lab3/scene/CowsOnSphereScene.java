package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.light.DirectLight;
import com.kpi.computergraphics.lab3.scene.light.DotLight;
import com.kpi.computergraphics.lab3.scene.objects.PolygonMesh;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import com.kpi.computergraphics.lab3.scene.objects.Sphere;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.kpi.computergraphics.lab3.base.MatrixTransformation.rotateMatrix;
import static com.kpi.computergraphics.lab3.base.MatrixTransformation.translateMatrix;
import static com.kpi.computergraphics.lab3.scene.ReaderOBJ.readStream;

public class CowsOnSphereScene implements PresetScene<SceneObject> {
    @Override
    public Scene<SceneObject> getScene() {
        var camera = new Camera(
                new Vector3D(0, 300, -3000),
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
        var dotLightRed = new DotLight(
                new Vector3D(0, 300, 200),
                new Color(1, 0, 0),
                2000
        );

        try {
            InputStream inputCow = new FileInputStream("src/main/resources/cow.obj");
            InputStream input2Cow = new FileInputStream("src/main/resources/cow.obj");
            PolygonMesh cow1Mesh = readStream(inputCow);
            PolygonMesh cow2Mesh = readStream(input2Cow);
            cow1Mesh.transform(rotateMatrix(0, 90, 0));
            cow2Mesh.transform(rotateMatrix(0, -90, 0));
            cow2Mesh.transform(translateMatrix(0, 500, 0));
            Sphere sphere = new Sphere(new Vector3D(0, -500, 200), 700);
            List<SceneObject> cowOnSphereObjects = new ArrayList<>();
            cowOnSphereObjects.addAll(cow1Mesh.getObjects());
            cowOnSphereObjects.addAll(cow2Mesh.getObjects());
            cowOnSphereObjects.add(sphere);
            return new Scene<>(cowOnSphereObjects, camera, List.of(dirLightWhite, dirLightBlue, dirLightOrange, dotLightRed));

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
