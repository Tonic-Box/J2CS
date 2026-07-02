package com.tonic.j2cs.harness;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Stream;

/**
 * Packs a directory of compiled classes into a jar, optionally with a Main-Class manifest.
 */
public final class TestJars {

    private TestJars() {
    }

    public static Path jar(Path jarPath, Path classesDir, String mainClassDotted) throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        if (mainClassDotted != null) {
            manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, mainClassDotted);
        }
        try (OutputStream out = Files.newOutputStream(jarPath);
             JarOutputStream jar = new JarOutputStream(out, manifest);
             Stream<Path> files = Files.walk(classesDir)) {
            for (Path file : (Iterable<Path>) files.filter(Files::isRegularFile)::iterator) {
                String entryName = classesDir.relativize(file).toString().replace('\\', '/');
                jar.putNextEntry(new JarEntry(entryName));
                jar.write(Files.readAllBytes(file));
                jar.closeEntry();
            }
        }
        return jarPath;
    }
}
