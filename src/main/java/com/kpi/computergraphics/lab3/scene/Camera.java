package com.kpi.computergraphics.lab3.scene;

import com.kpi.computergraphics.lab3.base.*;
import com.kpi.computergraphics.lab3.scene.objects.IntersectionInfo;
import com.kpi.computergraphics.lab3.scene.objects.SceneObject;

import java.util.Optional;

public class Camera implements SceneObject {
    public final double fov;
    public final int horizontalResolution;
    public final int verticalResolution;
    public Vector3D rightVector;
    public Vector3D upVector;
    public Vector3D focalPoint;
    public Vector3D viewVector;
    public Matrix cameraTransformMatrix;

    Camera(Vector3D focalPoint, Vector3D viewVector, double fov, int horizontalResolution,
                  int verticalResolution, Matrix cameraTransformMatrix) {
        this.focalPoint = focalPoint;
        this.viewVector = viewVector;
        this.fov = fov;
        this.horizontalResolution = horizontalResolution;
        this.verticalResolution = verticalResolution;
        this.cameraTransformMatrix = cameraTransformMatrix;

        if (viewVector.crossProduct(new Vector3D(0, 0, 1)).length() == 0) {
            this.rightVector = new Vector3D(1, 0, 0);
        } else {
            rightVector = viewVector.crossProduct(new Vector3D(0, 0, 1)).normalize();
        }
        upVector = viewVector.crossProduct(rightVector).normalize();
    }

    Camera(Vector3D focalPoint, Vector3D viewVector, double fov, int horizontalResolution,
                  int verticalResolution) {
        this.focalPoint = focalPoint;
        this.viewVector = viewVector;
        this.fov = fov;
        this.horizontalResolution = horizontalResolution;
        this.verticalResolution = verticalResolution;

        if (viewVector.crossProduct(new Vector3D(0, 0, 1)).length() == 0) {
            rightVector = new Vector3D(1, 0, 0);
        } else {
            rightVector = viewVector.crossProduct(new Vector3D(0, 0, 1)).normalize();
        }
        upVector = viewVector.crossProduct(rightVector).normalize();
    }

    @Override
    public Optional<IntersectionInfo> findIntersection(Ray ray) {
        return Optional.empty();
    }

    @Override
    public Bound getBound() {
        return null;
    }

    public void transform(Matrix matrix) {
        focalPoint = MatrixTransformation.transform(focalPoint, matrix);
        viewVector = MatrixTransformation.transform(viewVector, matrix);
        rightVector = MatrixTransformation.transform(rightVector, matrix);
        upVector = MatrixTransformation.transform(upVector, matrix);
    }

    public double getWidthToHeightRatio() {
        return horizontalResolution / verticalResolution;
    }

    public Vector3D getScreenCenter() {
        return focalPoint.add(viewVector);
    }

    public double getScreenWidth() {
        return 2 * viewVector.length() * Math.tan(fov / 2);
    }

    public double getScreenHeight() {
        return getScreenWidth() / getWidthToHeightRatio();
    }

    public Vector3D getScreenPixelVector(int x, int y) {
        Vector3D screenPixelPosition = getScreenPixelCoordinates(x, y);
        Vector3D vector = screenPixelPosition.subtract(focalPoint);
        if (cameraTransformMatrix != null) {
            vector = MatrixTransformation.transform(vector, cameraTransformMatrix);
        }
        return vector;
    }

    public Vector3D getScreenPixelCoordinates(int x, int y) {
        Vector3D fromFocalPointToPointOnScreen = viewVector
                .subtract(rightVector.multiply(((x - horizontalResolution / 2) * getScreenWidth()) / horizontalResolution))
                .subtract(upVector.multiply(((y - verticalResolution / 2) * getScreenHeight()) / verticalResolution));
        return new Vector3D(focalPoint.x(), focalPoint.y(), focalPoint.z())
                .add(fromFocalPointToPointOnScreen);
    }
}
