package com.kpi.computergraphics.lab3.optimization.kd;

import com.kpi.computergraphics.lab3.base.Bound;
import com.kpi.computergraphics.lab3.base.LimitedAxis;
import com.kpi.computergraphics.lab3.base.Ray;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.optimization.BoundUtil;
import com.kpi.computergraphics.lab3.optimization.kd.KdTreeFactory.KdNode;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Getter
public class KdInternal extends KdNode {

    private final KdNode left;

    private final KdNode right;

    private final Bound bound;

    private final LimitedAxis.Axis splitAxis;

    private final double splitPosition;

    public KdInternal(KdNode left, KdNode right, LimitedAxis.Axis splitAxis, double splitPosition) {
        this.left = left;
        this.right = right;
        this.splitAxis = splitAxis;
        this.splitPosition = splitPosition;
        this.bound = BoundUtil.boundUnion(left.getBound(), right.getBound());
    }

    public Optional<IntersectionInfo> findIntersection(Ray ray, List<SceneObject> avoid, boolean closest) {
        BoundUtil.MinMax limit = this.bound.shoot(ray);
        if (limit == null) {
            return Optional.empty();
        }

        Vector3D invDir = new Vector3D(
                1 / ray.vector().x(),
                1 / ray.vector().y(),
                1 / ray.vector().z()
        );

        Queue<KDComb> comb = new LinkedList<>();
        KdNode node = this;
        double rayTMax = Double.MAX_VALUE;
        IntersectionInfo result = null;
        while (node != null) {
            if (rayTMax < limit.getMin()) {
                break;
            }

            if (node instanceof KdTreeFactory.KdLeaf) {
                Optional<IntersectionInfo> intersection = node.findIntersection(ray, avoid, closest);
                if (intersection.isPresent() && !closest) {
                    return intersection;
                }
                if (intersection.isPresent() && intersection.get().length() < rayTMax) {
                    rayTMax = intersection.get().length();
                    result = intersection.get();
                }

                if (comb.size() > 0) {
                    KDComb nextTodo = comb.poll();
                    node = nextTodo.node;
                    limit.setMin(nextTodo.limit.getMin());
                    limit.setMax(nextTodo.limit.getMax());
                } else {
                    node = null;
                }
            } else if (node instanceof KdInternal internal) {
                LimitedAxis.Axis axis = internal.splitAxis;
                double[] rayAxes = new double[]{ray.start().x(), ray.start().y(), ray.start().z()};
                double[] rayVectorAxes = new double[]{ray.vector().x(), ray.vector().y(), ray.vector().z()};

                double[] invDirAxes = new double[]{invDir.x(), invDir.y(), invDir.z()};

                double plane = (internal.splitPosition - rayAxes[axis.ordinal()]) * invDirAxes[axis.ordinal()];
                boolean belowFirst = rayAxes[axis.ordinal()] < internal.splitPosition ||
                        (rayAxes[axis.ordinal()] == internal.splitPosition && rayVectorAxes[axis.ordinal()] <= 0);

                KdNode firstChild;
                KdNode secondChild;
                if (belowFirst) {
                    firstChild = internal.left;
                    secondChild = internal.right;
                } else {
                    firstChild = internal.right;
                    secondChild = internal.left;
                }
                if (plane > limit.getMax() || plane <= 0) {
                    node = firstChild;
                } else if (plane < limit.getMin()) {
                    node = secondChild;
                } else {
                    comb.add(new KDComb(secondChild, new BoundUtil.MinMax(plane, limit.getMax())));
                    node = firstChild;
                    limit.setMax(plane);
                }
            }
        }
        return Optional.ofNullable(result);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class KDComb {

        private KdNode node;

        private BoundUtil.MinMax limit;
    }
}
