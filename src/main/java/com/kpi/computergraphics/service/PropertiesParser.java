package com.kpi.computergraphics.service;

import com.kpi.computergraphics.model.base.Point3D;
import com.kpi.computergraphics.model.base.Vector3D;
import com.kpi.computergraphics.model.object.Object3D;
import com.kpi.computergraphics.model.object.Sphere;

import java.io.IOException;
import java.util.*;

public class PropertiesParser {

    public static Map<String, Object> parse(String pathToSettings) throws IOException {
        final Map<String, Object> settings = new HashMap<>();
        final Properties properties = new Properties();
        properties.load(PropertiesParser.class.getClassLoader().getResourceAsStream(pathToSettings));

        Enumeration<String> propNames = (Enumeration<String>) properties.propertyNames();

        final List<Map<String, String>> objectsProps = new ArrayList<>();
        if (propNames.hasMoreElements()) {
            String key;
            while (propNames.hasMoreElements()) {
                key = propNames.nextElement();
                if (key.equals("screen.width") || key.equals("screen.height")) {
                    settings.put(key, Integer.valueOf(properties.getProperty(key)));
                } else if (key.startsWith("objects[")) {
                    final int index = Integer.parseInt(key.substring(8, key.indexOf("]")));

                    while (index > objectsProps.size() - 1) {
                        objectsProps.add(new HashMap<>());
                    }

                    final Map<String, String> objectProps = objectsProps.get(index);
                    //objects[0].
                    objectProps.put(key.substring(11), properties.getProperty(key));

                } else if (key.equals("camera.pos")) {
                    settings.put(key, parsePointFromProp(properties.getProperty(key)));
                } else if (key.equals("light.vector") || key.equals("camera.direction") || key.equals("camera.rotation")) {
                    settings.put(key, parseVectorFromProp(properties.getProperty(key)));
                } else if (key.equals("camera.fov") || key.equals("screen.distance")) {
                    settings.put(key, Float.valueOf(properties.getProperty(key)));
                }
            }

            ObjectPropertiesParser.parseObjects(objectsProps, settings);
        }
        return settings;
    }

    private static Point3D parsePointFromProp(String prop) {
        float[] coords = new float[3];
        byte i = 0;
        for (final String pos : prop.split(",")) {
            if (i >= 3) {
                throw new IllegalStateException("coordinates property is not valid");
            }

            coords[i] = Float.parseFloat(pos);
            i++;
        }

        if (i != 3) {
            throw new IllegalStateException("coordinates property is not valid");
        }

        return new Point3D(coords);
    }

    private static Vector3D parseVectorFromProp(String prop) {
        float[] coords = new float[3];
        byte i = 0;
        for (final String pos : prop.split(",")) {
            if (i >= 3) {
                throw new IllegalStateException("coordinates property is not valid");
            }

            coords[i] = Float.parseFloat(pos);
            i++;
        }

        if (i != 3) {
            throw new IllegalStateException("coordinates property is not valid");
        }

        return new Vector3D(coords);
    }

    static class ObjectPropertiesParser {

        static void parseObjects(List<Map<String, String>> objectsProps, Map<String, Object> settings) {
            final List<Object3D> objects = new ArrayList<>();

            for (Map<String, String> objectProps : objectsProps) {
                if (!objectProps.containsKey("type")) {
                    throw new IllegalStateException("object property doesn't contain type");
                }

                final String type = objectProps.get("type");
                if ("sphere".equals(type)) {
                    final Sphere sphere = new Sphere();
                    sphere.setCenter(PropertiesParser.parsePointFromProp(objectProps.get("center")));
                    sphere.setRadius(Float.parseFloat(objectProps.get("radius")));
                    objects.add(sphere);
                }
            }

            settings.put("objects", objects);
        }
    }
}
