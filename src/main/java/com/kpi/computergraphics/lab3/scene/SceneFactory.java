package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.light.DirectLight;
import com.kpi.computergraphics.lab3.scene.light.Light;
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
    private final Map<String, Scene<SceneObject>> scenes = new HashMap<>();

    private final Camera defaultCamera;
    private final Light defaultLight;

    public SceneFactory() {
        defaultCamera = new Camera(
                new Vector3D(0, 0, -2000),
                new Vector3D(0, 0, 1),
                Math.PI / 3, 800, 600);
        defaultLight = new DirectLight(
                new Vector3D(1, 1, -1),
                new Color(1, 1, 1),
                0.2
        );
        try {
            Sphere sphere = new Sphere(new Vector3D(0, -500, 200), 700);
            scenes.put("cows_on_sphere", new CowsOnSphereScene().getScene());
            scenes.put("wolfs", new WolfsScene().getScene());

            Triangle triangle = new Triangle(
                    new Vector3D(0, 200, 200),
                    new Vector3D(-200, 400, 205),
                    new Vector3D(200, 400, 195)
            );
            List<SceneObject> geometryObjects = new ArrayList<>();
            geometryObjects.add(triangle);
            geometryObjects.add(sphere);
            var geometryScene = new Scene<>(geometryObjects, defaultCamera, List.of(defaultLight));
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
    public Scene<SceneObject> get(String sceneName) {
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
    public Scene<SceneObject> build(String fileName) {
        try {
            InputStream input = new FileInputStream(fileName);
            PolygonMesh mesh = readStream(input);
            List<SceneObject> objects = List.of(mesh);
            return new Scene<>(objects, defaultCamera, List.of(defaultLight));
        } catch (Exception e) {
            throw new IllegalStateException("No such file found");
        }
    }
}
