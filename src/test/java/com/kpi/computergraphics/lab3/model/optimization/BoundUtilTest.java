package com.kpi.computergraphics.lab3.model.optimization;

import com.kpi.computergraphics.lab3.base.Bound;
import com.kpi.computergraphics.lab3.base.Bounded;
import com.kpi.computergraphics.lab3.base.LimitedAxis;
import com.kpi.computergraphics.lab3.optimization.BoundUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundUtilTest {
    @Test
    public void axisUnionTest() {
        BoundUtil.MinMax bound1 = new BoundUtil.MinMax(0.0, 5.0);
        BoundUtil.MinMax bound2 = new BoundUtil.MinMax(2.0, 7.0);

        BoundUtil.MinMax result = BoundUtil.axisUnion(bound1, bound2);

        assertEquals(0.0, result.getMin());
        assertEquals(7.0, result.getMax());
    }

    @Test
    public void boundUnionTest() {
        LimitedAxis x1 = new LimitedAxis(LimitedAxis.Axis.X, new BoundUtil.MinMax(0.0, 5.0));
        LimitedAxis y1 = new LimitedAxis(LimitedAxis.Axis.Y, new BoundUtil.MinMax(1.0, 6.0));
        LimitedAxis z1 = new LimitedAxis(LimitedAxis.Axis.Z, new BoundUtil.MinMax(2.0, 7.0));
        Bound bound1 = new Bound(x1, y1, z1);

        LimitedAxis x2 = new LimitedAxis(LimitedAxis.Axis.X, new BoundUtil.MinMax(-2.0, 3.0));
        LimitedAxis y2 = new LimitedAxis(LimitedAxis.Axis.Y, new BoundUtil.MinMax(-3.0, 2.0));
        LimitedAxis z2 = new LimitedAxis(LimitedAxis.Axis.Z, new BoundUtil.MinMax(-4.0, 1.0));
        Bound bound2 = new Bound(x2, y2, z2);

        Bound result = BoundUtil.boundUnion(bound1, bound2);

        assertEquals(-2.0, result.getX().getLimit().getMin());
        assertEquals(5.0, result.getX().getLimit().getMax());
        assertEquals(-3.0, result.getY().getLimit().getMin());
        assertEquals(6.0, result.getY().getLimit().getMax());
        assertEquals(-4.0, result.getZ().getLimit().getMin());
        assertEquals(7.0, result.getZ().getLimit().getMax());
    }

    @Test
    public void boundListUnionEmptyTest() {
        List<Bounded> boundedItems = new ArrayList<>();
        BoundUtil.BoundResult result = BoundUtil.boundListUnion(boundedItems);

        assertEquals(0, result.union().getX().getLimit().getMin());
        assertEquals(0, result.union().getX().getLimit().getMax());

        assertEquals(0, result.union().getY().getLimit().getMin());
        assertEquals(0, result.union().getY().getLimit().getMax());

        assertEquals(0, result.union().getZ().getLimit().getMin());
        assertEquals(0, result.union().getZ().getLimit().getMax());

        assertEquals(0, result.primitiveBounds().size());
    }
}
