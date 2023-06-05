package com.kpi.computergraphics.lab3.service;

import com.kpi.computergraphics.lab3.model.Camera;
import com.kpi.computergraphics.lab3.model.Scene;
import com.kpi.computergraphics.lab3.model.SceneObject;
import com.kpi.computergraphics.lab3.model.SceneObjectsFactory;
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

import static com.kpi.computergraphics.lab3.model.ReaderOBJ.readStream;

public class SceneFactory {
    private final Map<String, Scene> scenes = new HashMap<>();

    private final Camera defaultCamera;
    private final Vector3D defaultLight;

    public SceneFactory(SceneObjectsFactory objectsFactory) {
        defaultCamera = objectsFactory.camera(
                new Vector3D(0, 0, -2000),
                new Vector3D(0, 0, 1),
                Math.PI / 3, 800, 600);
        defaultLight = objectsFactory.vector(-1, -1, 1);
        try {
            InputStream input = new FileInputStream("src/main/resources/cow.obj");
            PolygonMesh cowMesh = readStream(input);
            Sphere sphere = objectsFactory.sphere(0, -500, 200, 700);
            List<SceneObject> cowOnSphereObjects = new ArrayList<>();
            cowOnSphereObjects.add(cowMesh);
            cowOnSphereObjects.add(sphere);
            var cowOnSphereScene = new Scene(cowOnSphereObjects, defaultCamera, defaultLight);
            scenes.put("cow_on_sphere", cowOnSphereScene);

            Triangle triangle = objectsFactory.triangle(
                    objectsFactory.vector(0, 200, 200),
                    objectsFactory.vector(-200, 400, 205),
                    objectsFactory.vector(200, 400, 195)
            );
            List<SceneObject> geometryObjects = new ArrayList<>();
            geometryObjects.add(triangle);
            geometryObjects.add(sphere);
            var geometryScene = new Scene(geometryObjects, defaultCamera, defaultLight);
            scenes.put("geometry", geometryScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns preset scene by name.
     *
     * @param sceneName name of the preset scene
     * @return preset scene
     */
    public Scene get(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            return scenes.get(sceneName);
        }
        throw new IllegalStateException("No such scene found");
    }

    /**
     * Create default scene from provided OBJ file.
     *
     * @param fileName name of OBJ file
     * @return configured scene
     */
    public Scene build(String fileName) {
        try {
            InputStream input = new FileInputStream("src/main/resources/" + fileName);
            PolygonMesh mesh = readStream(input);
            List<SceneObject> objects = List.of(mesh);
            return new Scene(objects, defaultCamera, defaultLight);
        } catch (Exception e) {
            throw new IllegalStateException("No such file found");
        }
    }
}
