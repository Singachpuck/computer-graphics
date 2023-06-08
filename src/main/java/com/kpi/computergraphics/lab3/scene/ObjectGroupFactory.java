package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.List;

public interface ObjectGroupFactory {

    ObjectGroup getObjectGroup(List<SceneObject> objects);
}
