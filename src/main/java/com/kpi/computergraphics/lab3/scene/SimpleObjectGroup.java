package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Bound;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.optimization.BoundUtil;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class SimpleObjectGroup extends ObjectGroup {

    private final List<SceneObject> objects;

    private final Bound bound;

    public SimpleObjectGroup(List<SceneObject> objects) {
        this.objects = objects;
        this.bound = BoundUtil.boundListUnion(objects).union();
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray, List<SceneObject> avoid, boolean closest) {
        IntersectionInfo closestHit = null;
        for (SceneObject object : objects) {
            if (avoid.contains(object)) {
                continue;
            }
            Optional<IntersectionInfo> currentHit = object.findIntersection(ray);
            if (currentHit.isEmpty()) {
                continue;
            }
            if (!closest) {
                return currentHit;
            }
            closestHit = closestHit != null
                    ? IntersectionInfo.getClosest(currentHit.get(), closestHit)
                    : currentHit.get();
        }

        return Optional.ofNullable(closestHit);
    }
}