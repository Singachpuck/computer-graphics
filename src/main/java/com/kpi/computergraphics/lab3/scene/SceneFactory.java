package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.objects.PolygonMesh;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import com.kpi.computergraphics.lab3.scene.objects.Sphere;
import com.kpi.computergraphics.lab3.scene.objects.Triangle;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kpi.computergraphics.lab3.scene.ReaderOBJ.readStream;

public class SceneFactory {
    private final Map<String, Scene> scenes = new HashMap<>();

    private final Camera defaultCamera;
    private final Vector3D defaultLight;

    public SceneFactory() {
        defaultCamera = new Camera(
                new Vector3D(0, 0, -2000),
                new Vector3D(0, 0, 1),
                Math.PI / 3, 800, 600);
        defaultLight = new Vector3D(-1, -1, 1);
        try {
            InputStream input = new FileInputStream("src/main/resources/cow.obj");
            PolygonMesh cowMesh = readStream(input);
            Sphere sphere = new Sphere(new Vector3D(0, -500, 200), 700);
            List<SceneObject> cowOnSphereObjects = new ArrayList<>();
            cowOnSphereObjects.add(cowMesh);
            cowOnSphereObjects.add(sphere);
            var cowOnSphereScene = new Scene(cowOnSphereObjects, defaultCamera, defaultLight);
            scenes.put("cow_on_sphere", cowOnSphereScene);

            Triangle triangle = new Triangle(
                    new Vector3D(0, 200, 200),
                    new Vector3D(-200, 400, 205),
                    new Vector3D(200, 400, 195)
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
