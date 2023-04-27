package com.kpi.computergraphics.lab1.exception;

public class UnsupportedShapeException extends RuntimeException {

    public UnsupportedShapeException() {
        super();
    }

    public UnsupportedShapeException(String message) {
        super(message);
    }

    public UnsupportedShapeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedShapeException(Throwable cause) {
        super(cause);
    }
}
