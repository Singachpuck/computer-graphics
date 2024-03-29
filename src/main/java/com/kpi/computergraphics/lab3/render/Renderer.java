package com.kpi.computergraphics.lab3.render;

import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.scene.Camera;
import com.kpi.computergraphics.lab3.scene.ObjectGroup;
import com.kpi.computergraphics.lab3.scene.ObjectGroupFactory;
import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;
import java.util.Optional;

public abstract class Renderer {

    protected final Scene<SceneObject> scene;

    protected final ObjectGroupFactory groupFactory;

    public Renderer(Scene<SceneObject> scene, ObjectGroupFactory groupFactory) {
        this.scene = scene;
        this.groupFactory = groupFactory;
    }

    public void render() {
        Camera camera = this.scene.camera;
        List<SceneObject> objects = this.scene.objects;

        ObjectGroup objectGroup = groupFactory.getObjectGroup(objects);

        renderStart();

        for (int y = 0; y < camera.verticalResolution; y++) {
            rowStart();

            for (int x = 0; x < camera.horizontalResolution; x++) {
                Vector3D screenPixelVector = camera.getScreenPixelVector(x, y);
                Ray ray = new Ray(camera.focalPoint, screenPixelVector);

//                IntersectionInfo closestIntersection = null;

//                for (SceneObject object : objects) {
//                    Optional<IntersectionInfo> optionalIntersection = object.findIntersection(ray);
//
//                    if (optionalIntersection.isEmpty()) {
//                        continue;
//                    }
//
//                    IntersectionInfo intersection = optionalIntersection.get();
//
//                    closestIntersection = closestIntersection != null ?
//                            (intersection.length() < closestIntersection.length() ? intersection : closestIntersection)
//                            : intersection;
//                }
//
//                if (checkShadow(closestIntersection)) {
//                    closestIntersection = null;
//                }

//                    closestIntersection = closestIntersection != null ?
//                            (intersection.length() < closestIntersection.length() ? intersection : closestIntersection)
//                            : intersection;
//                }

                Optional<IntersectionInfo> intersection = objectGroup.findIntersection(ray);
                intersect(intersection.orElse(null));
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
