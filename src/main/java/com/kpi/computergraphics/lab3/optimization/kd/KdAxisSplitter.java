package com.kpi.computergraphics.lab3.optimization.kd;

import com.kpi.computergraphics.lab3.base.Bound;
import com.kpi.computergraphics.lab3.base.LimitedAxis;
import com.kpi.computergraphics.lab3.base.Vector3D;
import com.kpi.computergraphics.lab3.optimization.BoundUtil;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class KdAxisSplitter<T extends SceneObject> {

    private double intersectCost;

    private double traversalCost;

    private double emptyBonus;

    public KdAxisSplitter() {
        this.intersectCost = 80;
        this.traversalCost = 1;
        this.emptyBonus = 0.01;
    }

    public SplitResult<T> split(List<Bound> bounds, Bound bound, List<T> shapes, int rPrev) {
        LimitedAxis.Axis bestAxis = null;
        int bestOffset = -1;
        double bestCost = Double.MAX_VALUE;
        double oldCost = this.intersectCost * shapes.size();
        double totalSurfaceArea = bound.getSurface();
        double invTotalSurfaceArea = 1 / totalSurfaceArea;
        Vector3D diagonal = bound.getDiagonal();
        LimitedAxis.Axis axis = bound.getMaximumExtentAxis();
        int retries = 0;
        List<List<BoundEdge<T>>> edges = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<BoundEdge<T>> innerList = new ArrayList<>();
            for (int j = 0; j < 2 * shapes.size(); j++) {
                innerList.add(null);
            }
            edges.add(innerList);
        }
        LimitedAxis[] boundAxes = new LimitedAxis[]{bound.getX(), bound.getY(), bound.getZ()};
        while (bestAxis == null && retries < 2) {
            this.computeEdges(edges, axis, shapes, bounds);
            int nBelow = 0,
                    nAbove = shapes.size();
            for (int i = 0; i < 2 * shapes.size(); i++) {
                if (edges.get(axis.ordinal()).get(i).type == EdgeType.END) {
                    --nAbove;
                }
                double edgeT = edges.get(axis.ordinal()).get(i).t;
                if (edgeT > boundAxes[axis.ordinal()].getStart() && edgeT < boundAxes[axis.ordinal()].getEnd()) {
                    double[] diagonalAxes = new double[]{diagonal.x(), diagonal.y(), diagonal.z()};
                    double belowSA = 2 * (diagonalAxes[(axis.ordinal() + 1) % 3] * diagonalAxes[(axis.ordinal() + 2) % 3] +
                            (edgeT - boundAxes[axis.ordinal()].getStart()) *
                                    (diagonalAxes[(axis.ordinal() + 1) % 3] + diagonalAxes[(axis.ordinal() + 2) % 3]));
                    double aboveSA = 2 * (diagonalAxes[(axis.ordinal() + 1) % 3] * diagonalAxes[(axis.ordinal() + 2) % 3] +
                            (boundAxes[axis.ordinal()].getEnd() - edgeT) *
                                    (diagonalAxes[(axis.ordinal() + 1) % 3] + diagonalAxes[(axis.ordinal() + 2) % 3]));

                    double pBelow = belowSA * invTotalSurfaceArea;
                    double pAbove = aboveSA * invTotalSurfaceArea;
                    double eb = nAbove == 0 || nBelow == 0 ? this.emptyBonus : 0;
                    double cost = this.traversalCost + this.intersectCost * (1 - eb) * (pBelow * nBelow + pAbove * nAbove);
                    if (cost < bestCost) {
                        bestCost = cost;
                        bestAxis = axis;
                        bestOffset = i;
                    }
                }
                if (edges.get(axis.ordinal()).get(i).type == EdgeType.START) ++nBelow;
            }
            retries++;
            axis = LimitedAxis.Axis.values()[(axis.ordinal() + 1) % 3];
        }

        if (bestCost > oldCost) {
            rPrev++;
        }
        if ((bestCost > 4 * oldCost && shapes.size() < 16) || bestAxis == null || rPrev == 3) {
            return null;
        }
        List<T> leftSplitMembers = new ArrayList<>();
        List<Bound> allLeftBounds = new ArrayList<>();
        for (int i = 0; i < bestOffset; i++) {
            if (edges.get(bestAxis.ordinal()).get(i).type == EdgeType.START) {
                leftSplitMembers.add(edges.get(bestAxis.ordinal()).get(i).primitive);
                allLeftBounds.add(bounds.get(edges.get(bestAxis.ordinal()).get(i).primitiveInd));
            }
        }
        List<T> rightSplitMembers = new ArrayList<>();
        List<Bound> allRightBounds = new ArrayList<>();
        for (int i = bestOffset + 1; i < 2 * shapes.size(); i++) {
            if (edges.get(bestAxis.ordinal()).get(i).type == EdgeType.END) {
                rightSplitMembers.add(edges.get(bestAxis.ordinal()).get(i).primitive);
                allRightBounds.add(bounds.get(edges.get(bestAxis.ordinal()).get(i).primitiveInd));
            }
        }
        double tSplit = edges.get(bestAxis.ordinal()).get(bestOffset).t;

        Bound leftBound = new Bound(
                new LimitedAxis(LimitedAxis.Axis.X, new BoundUtil.MinMax(boundAxes[LimitedAxis.Axis.X.ordinal()].getStart(),
                        boundAxes[LimitedAxis.Axis.X.ordinal()].getEnd())),
                new LimitedAxis(LimitedAxis.Axis.Y, new BoundUtil.MinMax(boundAxes[LimitedAxis.Axis.Y.ordinal()].getStart(),
                        boundAxes[LimitedAxis.Axis.Y.ordinal()].getEnd())),
                new LimitedAxis(LimitedAxis.Axis.Z, new BoundUtil.MinMax(boundAxes[LimitedAxis.Axis.Z.ordinal()].getStart(),
                        boundAxes[LimitedAxis.Axis.Z.ordinal()].getEnd()))
        );
        Bound rightBound = new Bound(
                new LimitedAxis(LimitedAxis.Axis.X, new BoundUtil.MinMax(boundAxes[LimitedAxis.Axis.X.ordinal()].getStart(),
                        boundAxes[LimitedAxis.Axis.X.ordinal()].getEnd())),
                new LimitedAxis(LimitedAxis.Axis.Y, new BoundUtil.MinMax(boundAxes[LimitedAxis.Axis.Y.ordinal()].getStart(),
                        boundAxes[LimitedAxis.Axis.Y.ordinal()].getEnd())),
                new LimitedAxis(LimitedAxis.Axis.Z, new BoundUtil.MinMax(boundAxes[LimitedAxis.Axis.Z.ordinal()].getStart(),
                        boundAxes[LimitedAxis.Axis.Z.ordinal()].getEnd()))
        );

        if (bestAxis == LimitedAxis.Axis.X) {
            leftBound.getX().setEnd(tSplit);
            rightBound.getX().setStart(tSplit);
        } else if (bestAxis == LimitedAxis.Axis.Y) {
            leftBound.getY().setEnd(tSplit);
            rightBound.getY().setStart(tSplit);
        } else {
            leftBound.getZ().setEnd(tSplit);
            rightBound.getZ().setStart(tSplit);
        }

        return new SplitResult<>(
                bestAxis,
                tSplit,
                rPrev,
                leftSplitMembers,
                rightSplitMembers,
                leftBound,
                rightBound,
                allLeftBounds,
                allRightBounds
        );
    }

    private void computeEdges(List<List<BoundEdge<T>>> edges, LimitedAxis.Axis axis, List<T> primitives, List<Bound> bounds) {
        for (int i = 0; i < primitives.size(); i++) {
            T primitive = primitives.get(i);
            Bound primBounds = bounds.get(i);
            LimitedAxis[] axes = new LimitedAxis[]{primBounds.getX(), primBounds.getY(), primBounds.getZ()};
            edges.get(axis.ordinal()).set(2 * i, new BoundEdge<>(axes[axis.ordinal()].getStart(), primitive, EdgeType.START, i));
            edges.get(axis.ordinal()).set(2 * i + 1, new BoundEdge<>(axes[axis.ordinal()].getEnd(), primitive, EdgeType.END, i));
        }

        edges.get(axis.ordinal()).sort((i1, i2) -> {
            if (i1.t == i2.t) {
                return i1.type.ordinal() - i2.type.ordinal();
            } else {
                return Double.compare(i1.t, i2.t);
            }
        });
    }

    enum EdgeType {
        START,
        END,
    }

    @Getter
    @AllArgsConstructor
    static class BoundEdge<T extends SceneObject> {

        public double t;
        public T primitive;
        public EdgeType type;
        public int primitiveInd;

    }

    record SplitResult<T extends SceneObject>(LimitedAxis.Axis splitType,
                                              double splitPosition,
                                              int newBadRefine,
                                              List<T> leftSplitMembers,
                                              List<T> rightSplitMembers,
                                              Bound leftBound,
                                              Bound rightBound,
                                              List<Bound> allLeftBounds,
                                              List<Bound> allRightBounds) {
    }
}
