package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ObjectGroup {

    public abstract Optional<IntersectionInfo> findIntersection(Ray ray, List<SceneObject> avoid, boolean closest);

    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        return this.findIntersection(ray, new ArrayList<>(), true);
    }
}
