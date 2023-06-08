package com.kpi.computergraphics.lab3.base;

import com.kpi.computergraphics.lab3.optimization.BoundUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LimitedAxis {

    private Axis axis;

    private BoundUtil.MinMax limit;

    public double getStart() {
        return limit.getMin();
    }

    public double getEnd() {
        return limit.getMax();
    }

    public void setStart(double start) {
        this.limit.setMin(start);
    }

    public void setEnd(double end) {
        this.limit.setMax(end);
    }

    public enum Axis {
        X, Y, Z;
    }
}
