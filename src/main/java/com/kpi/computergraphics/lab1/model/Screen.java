package com.kpi.computergraphics.lab1.model;

import com.kpi.computergraphics.lab1.model.base.Vector3D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Screen {

    private final int width;

    private final int height;

    private final float distance;

    private final Vector3D rotation;
}
