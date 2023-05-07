package com.kpi.computergraphics.lab3.model;

import com.kpi.computergraphics.lab3.model.base.Matrix;
import com.kpi.computergraphics.lab3.model.base.Vector3D;
import com.kpi.computergraphics.lab3.model.traceabletransformable.Transformable;
import com.kpi.computergraphics.lab3.service.MatrixTransformation;
public class Camera implements Transformable {
    public Vector3D rightVector;
    public Vector3D upVector;
    public Vector3D focalPoint;
    public Vector3D viewVector;
    public final double fov;
    public final int horizontalResolution;
    public final int verticalResolution;
    public Matrix cameraTransformMatrix;

    public Camera(Vector3D focalPoint, Vector3D viewVector, double fov, int horizontalResolution,
                  int verticalResolution, Matrix cameraTransformMatrix) {
        this.focalPoint = focalPoint;
        this.viewVector = viewVector;
        this.fov = fov;
        this.horizontalResolution = horizontalResolution;
        this.verticalResolution = verticalResolution;
        this.cameraTransformMatrix = cameraTransformMatrix;

        if (this.viewVector.crossProduct(new Vector3D(0, 0, 1)).length() == 0) {
            this.rightVector = new Vector3D(1, 0, 0);
        } else {
            this.rightVector = this.viewVector.crossProduct(new Vector3D(0, 0, 1)).normalize();
        }
        this.upVector = this.viewVector.crossProduct(this.rightVector).normalize();
    }

    public Camera(Vector3D focalPoint, Vector3D viewVector, double fov, int horizontalResolution,
                  int verticalResolution) {
        this.focalPoint = focalPoint;
        this.viewVector = viewVector;
        this.fov = fov;
        this.horizontalResolution = horizontalResolution;
        this.verticalResolution = verticalResolution;

        if (this.viewVector.crossProduct(new Vector3D(0, 0, 1)).length() == 0) {
            this.rightVector = new Vector3D(1, 0, 0);
        } else {
            this.rightVector = this.viewVector.crossProduct(new Vector3D(0, 0, 1)).normalize();
        }
        this.upVector = this.viewVector.crossProduct(this.rightVector).normalize();
    }

    public void transform(Matrix matrix) {
        this.focalPoint = MatrixTransformation.transform(this.focalPoint, matrix);
        this.viewVector = MatrixTransformation.transform(this.viewVector, matrix);
        this.rightVector = MatrixTransformation.transform(this.rightVector, matrix);
        this.upVector = MatrixTransformation.transform(this.upVector, matrix);
    }

    public double getWidthToHeightRatio() {
        return this.horizontalResolution / this.verticalResolution;
    }

    public Vector3D getScreenCenter() {
        return this.focalPoint.add(this.viewVector);
    }

    public double getScreenWidth() {
        return 2 * this.viewVector.length() * Math.tan(this.fov / 2);
    }

    public double getScreenHeight() {
        return this.getScreenWidth() / this.getWidthToHeightRatio();
    }

    public Vector3D getScreenPixelVector(int x, int y) {
        Vector3D screenPixelPosition = this.getScreenPixelCoordinates(x, y);
        Vector3D vector = screenPixelPosition.subtract(this.focalPoint);
        if (this.cameraTransformMatrix != null) {
            vector = MatrixTransformation.transform(vector, this.cameraTransformMatrix);
        }
        return vector;
    }

    public Vector3D getScreenPixelCoordinates(int x, int y) {
        Vector3D fromFocalPointToPointOnScreen = this.viewVector
                .subtract(this.rightVector.multiply(((x - this.horizontalResolution / 2) * this.getScreenWidth()) / this.horizontalResolution))
                .subtract(this.upVector.multiply(((y - this.verticalResolution / 2) * this.getScreenHeight()) / this.verticalResolution));
        return new Vector3D(this.focalPoint.x(), this.focalPoint.y(), this.focalPoint.z())
                .add(fromFocalPointToPointOnScreen);
    }
}
