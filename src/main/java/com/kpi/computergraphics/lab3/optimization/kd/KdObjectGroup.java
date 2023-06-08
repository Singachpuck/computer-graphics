package com.kpi.computergraphics.lab3.optimization.kd;

import com.kpi.computergraphics.lab3.base.Bound;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.optimization.kd.KdTreeFactory.KdNode;
import com.kpi.computergraphics.lab3.scene.ObjectGroup;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class KdObjectGroup extends ObjectGroup {

    private final List<SceneObject> objects;

    private final Bound bound;

    private final KdTreeFactory treeFactory;

    private final KdNode root;

    public KdObjectGroup(List<SceneObject> objects, KdTreeFactory treeFactory) {
        this.objects = objects;
        this.treeFactory = treeFactory;
        double depth = 1 + 8 + 1.3 * (Math.log(objects.size()) / Math.log(2));
        this.root = treeFactory.getKDTree(objects, (int) depth);
        this.bound = this.root.getBound();
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray, List<SceneObject> avoid, boolean closest) {
        return this.root.findIntersection(ray, avoid, closest);
    }
}
