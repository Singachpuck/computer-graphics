package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

public interface PresetScene<T extends SceneObject> {
    Scene<T> getScene();
}
