package com.kpi.computergraphics.lab3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.kpi.computergraphics.lab3.model.Scene;
import com.kpi.computergraphics.lab3.model.Camera;
import com.kpi.computergraphics.lab3.model.SceneObject;
import com.kpi.computergraphics.lab3.model.base.*;
import com.kpi.computergraphics.lab3.model.object.*;
import com.kpi.computergraphics.lab3.service.*;


public class App {
    public static void main(String[] args) {
        String objFilePath = "";
        String outputPath = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--source")) {
                objFilePath = args[i + 1];
            }
            if (args[i].equals("--output")) {
                outputPath = args[i + 1];
            }
        }

        if (objFilePath.isEmpty()) {
            throw new RuntimeException("Invalid input: no obj path");
        }
        if (outputPath.isEmpty()) {
            throw new RuntimeException("Invalid input: no output path");
        }

        try {
            InputStream input = new FileInputStream(objFilePath);
            PolygonMesh mesh = ReaderOBJ.readStream(input);
            System.out.println("Mesh loaded");
            Vector3D cameraPosition = new Vector3D(0, 0, -2000);
            Vector3D cameraLookAt = new Vector3D(0, 0, 1);
            Camera camera = new Camera(cameraPosition, cameraLookAt, Math.PI / 3, 50, 50);
            Vector3D directionalLight = new Vector3D(-1, -1, 1);
            List<SceneObject> objects = new ArrayList<>();
            objects.add(new Sphere(new Vector3D(0, 1100, 8000), 3500));
            objects.add(mesh);
            objects.add(new Disk(new Vector3D(-400, -1800, 8000), new Vector3D(0, 1, 0), 8000));
            Scene scene = new Scene(objects, camera, directionalLight);
            scene.transform(MatrixTransformation.translateMatrix(-400, -500, 2000));
            mesh.transform(MatrixTransformation.translateMatrix(900, 100, 700));
            mesh.transform(MatrixTransformation.scaleMatrix(2, 2, 2));
            // mesh.translate(0, 0, 1000);

            // transforms relative to (0, 0, 0)
            // look from below
            // camera.translate(0, -1000, 0);
            // camera.rotate(Math.PI / 6, 0, 0);

            // look from the side
            // camera.translate(-1000, 0, 0);
            // camera.rotate(0, -Math.PI / 6, 0);

            // look upside down
            // camera.rotate(0, 0, Math.PI);

            // look behind
            // camera.rotate(0, Math.PI, 0);

            OutputStream output = new FileOutputStream(outputPath);
            PixelRenderer renderer = new PixelRenderer(scene, output);
            // transforms relative to the camera
            // await renderer.render();
            // look behind
            renderer.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
