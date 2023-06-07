package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.Camera;
import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;
import java.util.Optional;

public abstract class Renderer {
    public final Scene scene;

    protected Renderer(Scene scene) {
        this.scene = scene;
    }

    public void render() {
        Camera camera = this.scene.camera;
        List<SceneObject> objects = this.scene.objects;

        renderStart();

        for (int y = 0; y < camera.verticalResolution; y++) {
            rowStart();

            for (int x = 0; x < camera.horizontalResolution; x++) {
                Vector3D screenPixelVector = camera.getScreenPixelVector(x, y);
                Ray ray = new Ray(camera.focalPoint, screenPixelVector);
                IntersectionInfo closestIntersection = null;

                for (SceneObject object : objects) {
                    Optional<IntersectionInfo> optionalIntersection = object.findIntersection(ray);

                    if (optionalIntersection.isEmpty()) {
                        continue;
                    }

                    IntersectionInfo intersection = optionalIntersection.get();

                    closestIntersection = closestIntersection != null ?
                            (intersection.length() < closestIntersection.length() ? intersection : closestIntersection)
                            : intersection;
                }

                intersect(closestIntersection);
            }
            rowEnd();
        }
        renderEnd();
    }

    protected abstract void renderStart();

    protected abstract void rowStart();

    protected abstract void intersect(IntersectionInfo intersection);

    protected abstract void rowEnd();

    protected abstract void renderEnd();
}
