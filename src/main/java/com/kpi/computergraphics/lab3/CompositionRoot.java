package com.kpi.computergraphics.lab3;

import com.kpi.computergraphics.lab3.render.PpmRendererFactory;
import com.kpi.computergraphics.lab3.render.RendererFactory;
import com.kpi.computergraphics.lab3.scene.SceneFactory;

public class CompositionRoot {
    public static void main(String[] args) {
        ArgumentsParser argumentsParser = new ArgumentsParser();
        SceneFactory sceneFactory = new SceneFactory();
        RendererFactory rendererFactory = new PpmRendererFactory();
        RayCastingApp rayCastingApp = new RayCastingApp(argumentsParser, sceneFactory, rendererFactory);
        rayCastingApp.start(args);
    }
}
