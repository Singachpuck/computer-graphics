package com.kpi.computergraphics.lab3.service;

import com.kpi.computergraphics.lab3.model.Camera;
import com.kpi.computergraphics.lab3.model.Scene;
import com.kpi.computergraphics.lab3.model.SceneObject;
import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.model.object.PolygonMesh;
import com.kpi.computergraphics.lab3.model.object.Sphere;
import com.kpi.computergraphics.lab3.model.object.Triangle;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kpi.computergraphics.lab3.service.ReaderOBJ.readStream;

public class SceneFactory {
    private final Map<String, Scene> scenes = new HashMap<>();

    public SceneFactory() {
        Vector3D cameraPosition = new Vector3D(0, 0, -2000);
        Vector3D cameraLookAt = new Vector3D(0, 0, 1);
        Camera camera = new Camera(cameraPosition, cameraLookAt, Math.PI / 3, 800, 600);
        Vector3D directionalLight = new Vector3D(-1, -1, 1);
        try {
            InputStream input = new FileInputStream("src/main/resources/cow.obj");
            PolygonMesh cowMesh = readStream(input);
            Sphere sphere = new Sphere(new Vector3D(0, -500, 200), 700);
            List<SceneObject> cowOnSphereObjects = new ArrayList<>();
            cowOnSphereObjects.add(cowMesh);
            cowOnSphereObjects.add(sphere);
            var cowOnSphereScene = new Scene(cowOnSphereObjects, camera, directionalLight);
            scenes.put("cow_on_sphere", cowOnSphereScene);

            Triangle triangle = new Triangle(
                    new Vector3D(0, 200, 200),
                    new Vector3D(-200, 400, 205),
                    new Vector3D(200, 400, 195)
            );
            List<SceneObject> geometryObjects = new ArrayList<>();
            geometryObjects.add(triangle);
            geometryObjects.add(sphere);
            var geometryScene = new Scene(geometryObjects, camera, directionalLight);
            scenes.put("geometry", geometryScene);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene(String sceneName) {
        if (!scenes.containsKey(sceneName)) {
            throw new IllegalStateException("Scene '" + sceneName + "' not found");
        }
        return scenes.get(sceneName);
    }
}
