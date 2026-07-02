package com.tonic.j2cs.harness;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Compiles Java fixture sources at test time with the running toolchain's javac.
 */
public final class JavacHelper {

    private JavacHelper() {
    }

    public static void compile(List<Path> sources, Path outDir) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        if (javac == null) {
            throw new IllegalStateException("system java compiler unavailable; tests require a JDK");
        }
        List<String> args = new ArrayList<>();
        args.add("-d");
        args.add(outDir.toString());
        for (Path source : sources) {
            args.add(source.toString());
        }
        int exitCode = javac.run(null, null, null, args.toArray(new String[0]));
        if (exitCode != 0) {
            throw new IllegalStateException("javac failed with exit code " + exitCode + " for " + sources);
        }
    }
}
