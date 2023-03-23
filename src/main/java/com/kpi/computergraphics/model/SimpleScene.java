package com.kpi.computergraphics.model;

import com.kpi.computergraphics.model.base.Line;
import com.kpi.computergraphics.model.base.Point3D;
import com.kpi.computergraphics.model.base.Vector3D;
import com.kpi.computergraphics.model.object.Object3D;
import com.kpi.computergraphics.service.LinearAlgebra;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SimpleScene {

    private final Camera camera;

    private final List<Object3D> objects;

    private final VectorLightSource vls;

    public static SimpleScene ofProperties(Map<String, Object> properties) {
        final Screen screen = new Screen((Integer) properties.get("screen.width"),
                (Integer) properties.get("screen.height"),
                (float) properties.get("screen.distance"),
                (Vector3D) properties.get("camera.rotation"));
        final Camera camera = new Camera((Point3D) properties.get("camera.pos"),
                (Vector3D) properties.get("camera.direction"),
                screen,
                (float) properties.get("camera.fov"));
        return new SimpleScene(camera,
                (List<Object3D>) properties.get("objects"),
                new VectorLightSource(((Vector3D) properties.get("light.vector"))));
    }

    public void show() {
        final Screen screen = camera.getScreen();
        final int screenHeightPx = screen.getHeight();
        final int screenWidthPx = screen.getWidth();
        final float screenHeight = this.findScreenHeight();
        final Point3D screenCenter = LinearAlgebra.moveByVectorAtDistance(camera.getPlace(), camera.getDirection(), screen.getDistance());
        final float step = screenHeight / screenHeightPx;
        final Vector3D shiftWidth = LinearAlgebra.vectorMultiply(screen.getRotation(), camera.getDirection());
        final Point3D screenBeginLeft = LinearAlgebra.moveByVectorAtDistance(screenCenter, shiftWidth, step*screenWidthPx / 2);
        final Point3D screenBegin = LinearAlgebra.moveByVectorAtDistance(screenBeginLeft, screen.getRotation(), screenHeight / 2);
        final Vector3D moveWidth = LinearAlgebra.invert(shiftWidth);
        final Vector3D moveHeight = LinearAlgebra.invert(screen.getRotation());

        for (int i = 0; i < screenHeightPx; i++) {
            for (int j = 0; j < screenWidthPx; j++) {
                final Point3D pLeft = LinearAlgebra.moveByVectorAtDistance(screenBegin, moveWidth, step * j);
                final Point3D target = LinearAlgebra.moveByVectorAtDistance(pLeft, moveHeight, step * i);
                final Line beam = new Line(target, new Vector3D(camera.getPlace(), target));

                SimpleEntry<Object3D, Point3D> closest = null;
                for (Object3D object : objects) {
                    final Point3D[] intersectPoints = object.intersects(beam);
                    if (intersectPoints != null && intersectPoints.length != 0) {
                        final List<SimpleEntry<Object3D, Point3D>> points = new ArrayList<>();
                        for (Point3D intersectPoint : intersectPoints) {
                            points.add(new SimpleEntry<>(object, intersectPoint));
                        }
                        if (closest != null) {
                            points.add(closest);
                        }

                        closest = this.findClosestPoint(points);
                    }
                }

                if (closest != null) {
                    final Vector3D normalVector = closest.getKey().normal(closest.getValue());

                    final float scalarLight = (float) LinearAlgebra.scalarMultiply(vls.getDirection(), normalVector);
                    final char toPrint;
                    if (scalarLight < 0) {
                        toPrint = ' ';
                    } else if (scalarLight < 0.2) {
                        toPrint = '.';
                    } else if (scalarLight < 0.5) {
                        toPrint = '*';
                    } else if (scalarLight < 0.8) {
                        toPrint = 'O';
                    } else {
                        toPrint = '#';
                    }
                    System.out.print(toPrint);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private float findScreenHeight() {
        return 2*camera.getScreen().getDistance()*(float)Math.tan(camera.getFov() * Math.PI / 180);
    }

    private SimpleEntry<Object3D, Point3D> findClosestPoint(List<SimpleEntry<Object3D, Point3D>> points) {
        if (points.isEmpty()) {
            return null;
        }

        Point3D cameraPoint = camera.getPlace();
        SimpleEntry<Object3D, Point3D> closest = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            final SimpleEntry<Object3D, Point3D> newPoint = points.get(i);
            if (LinearAlgebra.distance(cameraPoint, closest.getValue()) >
                    LinearAlgebra.distance(cameraPoint, newPoint.getValue())) {
                closest = newPoint;
            }
        }
        return closest;
    }
}
