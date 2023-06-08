package com.kpi.computergraphics.lab3.optimization.kd;

import com.kpi.computergraphics.lab3.base.Bound;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.optimization.BoundUtil;
import com.kpi.computergraphics.lab3.scene.SimpleObjectGroup;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class KdTreeFactory {

    private final double nPrimitivesMax;
    private final KdAxisSplitter<SceneObject> splitter;

    public KdTreeFactory(double maxPrimitives) {
        this.nPrimitivesMax = maxPrimitives;
        this.splitter = new KdAxisSplitter<>();
    }

    public KdNode getKDTree(List<SceneObject> objects, int depth) {
        BoundUtil.BoundResult boundResult = BoundUtil.boundListUnion(objects);
        return this.buildTreeRecursively(
                objects,
                depth,
                boundResult.union(),
                boundResult.primitiveBounds(),
                0
        );
    }

    private KdNode buildTreeRecursively(List<SceneObject> objects, int depth, Bound bound, List<Bound> bounds, int rCounter) {
        if (objects.size() <= this.nPrimitivesMax || depth <= 0) {
            return new KdLeaf(objects);
        }
        KdAxisSplitter.SplitResult<SceneObject> splitResult = this.splitter.split(bounds, bound, objects, rCounter);

        if (splitResult == null) {
            return new KdLeaf(objects);
        }

        KdNode leftChild = this.buildTreeRecursively(splitResult.leftSplitMembers(),
                depth - 1,
                splitResult.leftBound(),
                splitResult.allLeftBounds(),
                splitResult.newBadRefine());
        KdNode rightChild = this.buildTreeRecursively(splitResult.rightSplitMembers(),
                depth - 1,
                splitResult.rightBound(),
                splitResult.allRightBounds(),
                splitResult.newBadRefine());
        return new KdInternal(
                leftChild,
                rightChild,
                splitResult.splitType(),
                splitResult.splitPosition()
        );
    }

    static abstract class KdNode {

        public abstract Bound getBound();

        public abstract Optional<IntersectionInfo> findIntersection(Ray ray, List<SceneObject> avoid, boolean closest);
    }

    static class KdLeaf extends KdNode {
        private final SimpleObjectGroup group;

        @Getter
        private final Bound bound;

        public KdLeaf(List<SceneObject> objects) {
            this.group = new SimpleObjectGroup(objects);
            this.bound = this.group.getBound();
        }

        public Optional<IntersectionInfo> findIntersection(Ray ray, List<SceneObject> avoid, boolean closest) {
            return this.group.findIntersection(ray, avoid, closest);
        }
    }
}
