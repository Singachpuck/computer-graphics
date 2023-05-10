package com.kpi.computergraphics.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.kpi.computergraphics.lab3.model.Scene;
import com.kpi.computergraphics.lab3.model.Camera;
import com.kpi.computergraphics.lab3.model.SceneObject;
import com.kpi.computergraphics.lab3.model.base.*;
import com.kpi.computergraphics.lab3.model.object.*;
import com.kpi.computergraphics.lab3.service.*;
import com.kpi.computergraphics.lab3.service.renderer.OutputStreamRenderer;
import com.kpi.computergraphics.lab3.service.renderer.PPMRenderer;
import com.kpi.computergraphics.lab3.service.renderer.Renderer;


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
            Camera camera = new Camera(cameraPosition, cameraLookAt, Math.PI / 3, 1920, 1080);
            Vector3D directionalLight = new Vector3D(-1, -1, 1);
            List<SceneObject> objects = new ArrayList<>();
            objects.add(new Sphere(new Vector3D(2000, 1000, 8000), 6000));
            objects.add(mesh);
            Scene scene = new Scene(objects, camera, directionalLight);

            // Transformations
//            camera.transform(MatrixTransformation.rotateMatrix(Math.PI / 6, 0, 0));
//            mesh.transform(MatrixTransformation.scaleMatrix(1, 1, 2));

            try (OutputStream output = new BufferedOutputStream(new FileOutputStream(outputPath))) {
//                Renderer renderer = new OutputStreamRenderer(scene, output);
                Renderer renderer = new PPMRenderer(scene, output);
                renderer.render();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
