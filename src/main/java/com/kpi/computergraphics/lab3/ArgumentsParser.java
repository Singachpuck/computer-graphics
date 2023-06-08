package com.kpi.computergraphics.lab3;

import java.util.Optional;

public class ArgumentsParser {

    public Arguments parse(String[] args) {
        Optional<String> source = Optional.empty();
        Optional<String> sceneName = Optional.empty();
        String output = null;
        String mode = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--source")) {
                source = Optional.of(args[i + 1]);
            }
            if (args[i].equals("--scene")) {
                sceneName = Optional.of(args[i + 1]);
            }
            if (args[i].equals("--output")) {
                output = args[i + 1];
            }
            if (args[i].equals("--mode")) {
                mode = args[i + 1];
            }
        }
        if (output == null) {
            throw new IllegalStateException("Output file is not set");
        }
        if (source.isEmpty() && sceneName.isEmpty()) {
            throw new IllegalStateException("Source file and scene is not set");
        }

        if (mode == null) {
            throw new IllegalStateException("Mode is not set");
        }

        return new Arguments(sceneName, source, output, mode);
    }

    public record Arguments(Optional<String> scene, Optional<String> source, String output, String mode) {
    }
}
