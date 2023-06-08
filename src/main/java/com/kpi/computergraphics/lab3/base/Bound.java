package com.kpi.computergraphics.lab3.base;

import com.kpi.computergraphics.lab3.optimization.BoundUtil;
import lombok.Getter;

@Getter
public class Bound {

    private final LimitedAxis x;

    private final LimitedAxis y;

    private final LimitedAxis z;

    private final double surface;

    private final Vector3D diagonal;

    private final LimitedAxis.Axis maximumExtentAxis;

    public Bound(LimitedAxis x, LimitedAxis y, LimitedAxis z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.diagonal = this.initDiagonal();
        this.surface = this.initSurface();
        this.maximumExtentAxis = this.initMaximumExtentAxis();
    }

    private Vector3D initDiagonal() {
        return new Vector3D(
                this.x.getStart() - this.x.getEnd(),
                this.y.getStart() - this.y.getEnd(),
                this.z.getStart() - this.z.getEnd()
        );
    }

    private double initSurface() {
        return 2 * (diagonal.x() * diagonal.y() +
                diagonal.x() * diagonal.z() +
                diagonal.y() * diagonal.z());
    }

    private LimitedAxis.Axis initMaximumExtentAxis() {
        if (diagonal.x() > diagonal.y() && diagonal.x() > diagonal.z()) {
            return LimitedAxis.Axis.X;
        }
        if (diagonal.y() > diagonal.z()) {
            return LimitedAxis.Axis.Y;
        }
        return LimitedAxis.Axis.Z;
    }

    public BoundUtil.MinMax shoot(Ray ray) {
        LimitedAxis[] axisArr = new LimitedAxis[]{x, y, z};
        double[] posArr = new double[]{ray.start().x(),
                ray.start().y(),
                ray.start().z()};
        double[] vectorArr = new double[]{ray.vector().x(),
                ray.vector().y(),
                ray.vector().z()};

        double v = 0,
                v2 = Double.MAX_VALUE;
        for (int i = 0; i < 3; i++) {

            double a = (axisArr[i].getStart() - posArr[i]) / vectorArr[i];
            double b = (axisArr[i].getEnd() - posArr[i]) / vectorArr[i];

            if (a > b) {
                double tmp = b;
                b = a;
                a = tmp;
            }

            v = Math.max(a, v);
            v2 = Math.min(b, v2);
            if (v > v2) {
                return null;
            }
        }
        return new BoundUtil.MinMax(v, v2);
    }
}
