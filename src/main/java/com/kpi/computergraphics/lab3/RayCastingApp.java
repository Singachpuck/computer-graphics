package com.kpi.computergraphics.lab3;

import com.kpi.computergraphics.lab3.optimization.kd.KdObjectGroup;
import com.kpi.computergraphics.lab3.optimization.kd.KdTreeFactory;
import com.kpi.computergraphics.lab3.render.RendererFactory;
import com.kpi.computergraphics.lab3.scene.ObjectGroupFactory;
import com.kpi.computergraphics.lab3.scene.Scene;
import com.kpi.computergraphics.lab3.scene.SceneFactory;
import com.kpi.computergraphics.lab3.scene.SimpleObjectGroup;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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

        Map<String, ObjectGroupFactory> objectGroups = new HashMap<>();
        if ("optimised".equals(arguments.mode())) {
            KdTreeFactory treeFactory = new KdTreeFactory(10);
            objectGroups.put("KDTree", (objects) -> new KdObjectGroup(objects, treeFactory));
        } else if ("simple".equals(arguments.mode())) {
            objectGroups.put("Simple", (SimpleObjectGroup::new));
        } else if ("test".equals(arguments.mode())) {
            KdTreeFactory treeFactory = new KdTreeFactory(10);
            objectGroups.put("Simple", (SimpleObjectGroup::new));
            objectGroups.put("KDTree", (objects) -> new KdObjectGroup(objects, treeFactory));
        }

        for (Map.Entry<String, ObjectGroupFactory> ogf : objectGroups.entrySet()) {
            try (OutputStream output = new BufferedOutputStream(new FileOutputStream(arguments.output() + ogf.getKey() + ".ppm"))) {
                long before = System.nanoTime();
                rendererFactory.create(scene, ogf.getValue(), output).render();
                long after = System.nanoTime();
                System.out.println(ogf.getKey() + " elapsed time: " + (after - before) / 1_000_000_000D);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
