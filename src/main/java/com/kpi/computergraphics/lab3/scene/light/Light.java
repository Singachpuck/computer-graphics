package com.kpi.computergraphics.lab3.scene.light;

import com.kpi.computergraphics.lab3.base.Color;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;

public interface Light {
    Color checkColor(IntersectionInfo intersectionInfo);

    boolean checkShadow(IntersectionInfo intersectionInfo, List<SceneObject> objects);
}
