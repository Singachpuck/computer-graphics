package com.kpi.computergraphics.lab3;

import com.kpi.computergraphics.lab3.model.Scene;
import com.kpi.computergraphics.lab3.service.ArgumentsParser;
import com.kpi.computergraphics.lab3.service.SceneFactory;
import com.kpi.computergraphics.lab3.service.renderer.RendererFactory;

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
