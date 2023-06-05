package com.kpi.computergraphics.lab3;

import com.kpi.computergraphics.lab3.model.SceneObjectsFactory;
import com.kpi.computergraphics.lab3.service.ArgumentsParser;
import com.kpi.computergraphics.lab3.service.SceneFactory;
import com.kpi.computergraphics.lab3.service.renderer.PpmRendererFactory;
import com.kpi.computergraphics.lab3.service.renderer.RendererFactory;

public class CompositionRoot {
    public static void main(String[] args) {
        ArgumentsParser argumentsParser = new ArgumentsParser();
        SceneObjectsFactory sceneObjectsFactory = new SceneObjectsFactory();
        SceneFactory sceneFactory = new SceneFactory(sceneObjectsFactory);
        RendererFactory rendererFactory = new PpmRendererFactory();
        RayCastingApp rayCastingApp = new RayCastingApp(argumentsParser, sceneFactory, rendererFactory);
        rayCastingApp.start(args);
    }
}
