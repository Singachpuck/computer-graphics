package com.kpi.computergraphics.model.object;

import com.kpi.computergraphics.model.base.Point3D;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sphere extends Object3D{

    private Point3D center;

    private float radius;
}
