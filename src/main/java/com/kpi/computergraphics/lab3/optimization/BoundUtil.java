package com.kpi.computergraphics.lab3.optimization;

import com.kpi.computergraphics.lab3.base.Bound;
import com.kpi.computergraphics.lab3.base.Bounded;
import com.kpi.computergraphics.lab3.base.LimitedAxis;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BoundUtil {

    public static MinMax axisUnion(MinMax bound1, MinMax bound2) {
        return new MinMax(Math.min(bound1.getMin(), bound2.getMin()),
                Math.max(bound1.getMax(), bound2.getMax()));
    }

    public static Bound boundUnion(Bound bound1, Bound bound2) {
        return new Bound(
                new LimitedAxis(LimitedAxis.Axis.X, axisUnion(bound1.getX().getLimit(),
                        bound2.getX().getLimit())),
                new LimitedAxis(LimitedAxis.Axis.Y, axisUnion(bound1.getY().getLimit(),
                        bound2.getY().getLimit())),
                new LimitedAxis(LimitedAxis.Axis.Z, axisUnion(bound1.getZ().getLimit(),
                        bound2.getZ().getLimit()))
        );
    }

    public static <T extends Bounded> BoundResult boundListUnion(List<T> boundedItems) {
        if (boundedItems.size() == 0) {
            return BoundResult.empty();
        }

        Bound unionBounds = boundedItems.get(0).getBound();
        List<Bound> primitiveBounds = new ArrayList<>();
        primitiveBounds.add(unionBounds);
        for (int i = 1; i < boundedItems.size(); i++) {
            Bound bounds = boundedItems.get(i).getBound();
            unionBounds = boundUnion(unionBounds, bounds);
            primitiveBounds.add(bounds);
        }
        return new BoundResult(unionBounds, primitiveBounds);
    }

    public record BoundResult(Bound union, List<Bound> primitiveBounds) {

        public static BoundResult empty() {
            return new BoundResult(
                    new Bound(new LimitedAxis(LimitedAxis.Axis.X, new MinMax(0, 0)),
                            new LimitedAxis(LimitedAxis.Axis.Y, new MinMax(0, 0)),
                            new LimitedAxis(LimitedAxis.Axis.Z, new MinMax(0, 0))),
                    new ArrayList<>()
            );
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class MinMax {
        private double min;

        private double max;
    }
}
