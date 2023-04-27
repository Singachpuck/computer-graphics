package com.kpi.computergraphics.lab1;

import com.kpi.computergraphics.lab1.model.SimpleScene;
import com.kpi.computergraphics.lab1.service.PropertiesParser;

import java.io.IOException;
import java.util.Map;

public class RayTracingApplication {

    static Map<String, Object> properties;

    static {
        try {
            properties = PropertiesParser.parse("settings.properties");
        } catch (IOException e) {
            throw new RuntimeException("Error reading settings.properties", e);
        }
    }

    public static void main(String[] args) {
        final SimpleScene simpleScene = SimpleScene.ofProperties(properties);
        simpleScene.show();
    }
}
