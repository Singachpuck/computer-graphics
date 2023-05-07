package com.kpi.computergraphics.lab3.service;

import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.model.object.Triangle;
import com.kpi.computergraphics.lab3.model.traceabletransformable.Mesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReaderOBJ {

    public static Mesh readStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        List<Vector3D> vertices = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();

        Pattern vertexPattern = Pattern.compile("^v\\s([0-9.-]+)\\s([0-9.-]+)\\s([0-9.-]+)");
        Pattern facePattern = Pattern.compile("^f(\\s\\d+(\\/\\d*)?(\\/\\d+)?) {1,3}");

        while ((line = reader.readLine()) != null) {
            Matcher vertexMatcher = vertexPattern.matcher(line);
            if (vertexMatcher.find()) {
                double x = Double.parseDouble(vertexMatcher.group(1));
                double y = Double.parseDouble(vertexMatcher.group(2));
                double z = Double.parseDouble(vertexMatcher.group(3));
                vertices.add(new Vector3D(x, y, z));
            }

            Matcher faceMatcher = facePattern.matcher(line);
            if (faceMatcher.find()) {
                String[] parts = faceMatcher.group(1).trim().split("\\s+");
                int v1Index = Integer.parseInt(parts[0].split("/")[0]) - 1;
                int v2Index = Integer.parseInt(parts[1].split("/")[0]) - 1;
                int v3Index = Integer.parseInt(parts[2].split("/")[0]) - 1;
                Vector3D v1 = vertices.get(v1Index);
                Vector3D v2 = vertices.get(v2Index);
                Vector3D v3 = vertices.get(v3Index);
                triangles.add(new Triangle(v1, v2, v3));
            }
        }

        reader.close();

        return new Mesh(triangles);
    }
}
