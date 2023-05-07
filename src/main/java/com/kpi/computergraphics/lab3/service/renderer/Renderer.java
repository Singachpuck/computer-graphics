package com.kpi.computergraphics.lab3.service.renderer;

import com.kpi.computergraphics.lab3.model.Camera;
import com.kpi.computergraphics.lab3.model.IntersectionInfo;
import com.kpi.computergraphics.lab3.model.Scene;
import com.kpi.computergraphics.lab3.model.SceneObject;
import com.kpi.computergraphics.lab3.model.base.Ray;
import com.kpi.computergraphics.lab3.model.base.Vector3D;

import java.util.List;
import java.util.Optional;

public abstract class Renderer {
    public final Scene scene;

    public Renderer(Scene scene) {
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

                if (checkShadow(closestIntersection)) {
                    closestIntersection = null;
                }

                intersect(closestIntersection);
            }
            rowEnd();
        }
        renderEnd();
    }

    public boolean checkShadow(IntersectionInfo closestHit) {
        if (closestHit == null) {
            return false;
        }

        Ray shadowRay = new Ray(closestHit.position(), scene.light.multiply(-1));
        for (final SceneObject object : this.scene.objects) {
            if (object == closestHit.object()) {
                continue;
            }
            Optional<IntersectionInfo> shadowHit = object.findIntersection(shadowRay);
            if (shadowHit.isPresent()) {
                return true;
            }
        }
        return false;
    }

    protected abstract void renderStart();

    protected abstract void rowStart();

    protected abstract void intersect(IntersectionInfo intersection);

    protected abstract void rowEnd();

    protected abstract void renderEnd();
}
