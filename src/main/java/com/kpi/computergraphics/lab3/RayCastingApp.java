package com.kpi.computergraphics.lab3;

import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.SceneFactory;
import com.kpi.computergraphics.lab3.render.RendererFactory;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class RayCastingApp {
    private final ArgumentsParser argumentsParser;
    private final SceneFactory sceneFactory;
    private final RendererFactory rendererFactory;

    public RayCastingApp(ArgumentsParser argumentsParser,
                         SceneFactory sceneFactory,
                         RendererFactory rendererFactory) {
        this.argumentsParser = argumentsParser;
        this.sceneFactory = sceneFactory;
        this.rendererFactory = rendererFactory;
    }

    public void start(String[] args) {
        var arguments = argumentsParser.parse(args);
        Scene scene;
        if (arguments.scene().isPresent()) {
            scene = sceneFactory.get(arguments.scene().get());
        } else {
            scene = sceneFactory.build(arguments.source().get());
        }
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream("src/main/resources/" + arguments.output()))) {
            rendererFactory.create(scene, output).render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
