package com.tonic.j2cs.frontend;

import com.tonic.j2cs.J2csException;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import com.tonic.parser.MethodEntry;
import com.tonic.type.AccessFlags;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Loads a .class or .jar input into an empty ClassPool and resolves the entry class.
 */
public final class InputLoader {

    public static final String MAIN_DESCRIPTOR = "([Ljava/lang/String;)V";

    public LoadedInput load(Path input, String mainOverride) {
        if (!Files.isRegularFile(input)) {
            throw new J2csException("input file not found: " + input);
        }
        String fileName = input.getFileName().toString().toLowerCase();
        ClassPool pool = new ClassPool(true);
        String manifestMain = null;
        if (fileName.endsWith(".jar")) {
            manifestMain = loadJar(pool, input);
        } else if (fileName.endsWith(".class")) {
            loadSingleClass(pool, input);
        } else {
            throw new J2csException("unsupported input type (expected .class or .jar): " + input);
        }
        List<ClassFile> appClasses = dedupByInternalName(pool.getClasses());
        if (appClasses.isEmpty()) {
            throw new J2csException("no classes found in input: " + input);
        }
        rejectPlatformPackages(appClasses);
        String entry = resolveEntry(appClasses, mainOverride, manifestMain, input);
        return new LoadedInput(pool, appClasses, entry);
    }

    /**
     * A multi-release jar carries the same class under both its base path and
     * {@code META-INF/versions/N/...}; the pool loads every entry, yielding several ClassFiles that
     * share one internal name. Naming builds a single member namer per name while emission iterates
     * every ClassFile, so a divergent duplicate reaches emission with unregistered members (and two
     * C# types with the same name would not compile anyway). Keep the first ClassFile per name.
     */
    private static List<ClassFile> dedupByInternalName(List<ClassFile> classes) {
        Map<String, ClassFile> byName = new LinkedHashMap<>();
        for (ClassFile cf : classes) {
            byName.putIfAbsent(cf.getClassName(), cf);
        }
        return List.copyOf(byName.values());
    }

    private String loadJar(ClassPool pool, Path input) {
        try (JarFile jar = new JarFile(input.toFile())) {
            Manifest manifest = jar.getManifest();
            String manifestMain = null;
            if (manifest != null) {
                manifestMain = manifest.getMainAttributes().getValue("Main-Class");
            }
            pool.loadJar(jar);
            return manifestMain;
        } catch (IOException e) {
            throw new J2csException("failed to read jar " + input + ": " + e.getMessage(), e);
        }
    }

    private void loadSingleClass(ClassPool pool, Path input) {
        try (InputStream in = Files.newInputStream(input)) {
            pool.loadClass(in);
        } catch (IOException e) {
            throw new J2csException("failed to read class file " + input + ": " + e.getMessage(), e);
        }
    }

    private void rejectPlatformPackages(List<ClassFile> appClasses) {
        List<String> offenders = new ArrayList<>();
        for (ClassFile cf : appClasses) {
            String name = cf.getClassName();
            if (name.startsWith("java/") || name.startsWith("javax/")) {
                offenders.add(name);
            }
        }
        if (!offenders.isEmpty()) {
            throw new J2csException("input defines classes in reserved platform packages: " + offenders);
        }
    }

    private String resolveEntry(List<ClassFile> appClasses, String mainOverride, String manifestMain, Path input) {
        if (mainOverride != null) {
            String internal = toInternalName(mainOverride);
            return requireEntryClass(appClasses, internal, "--main " + mainOverride);
        }
        if (manifestMain != null) {
            String internal = toInternalName(manifestMain);
            return requireEntryClass(appClasses, internal, "manifest Main-Class " + manifestMain);
        }
        List<String> candidates = new ArrayList<>();
        for (ClassFile cf : appClasses) {
            if (findStaticMain(cf) != null) {
                candidates.add(cf.getClassName());
            }
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        if (candidates.isEmpty()) {
            throw new J2csException("no class with a static main(String[]) found in " + input
                    + "; specify one with --main <fqcn>");
        }
        throw new J2csException("multiple classes with a static main(String[]) found: " + candidates
                + "; specify one with --main <fqcn>");
    }

    private String requireEntryClass(List<ClassFile> appClasses, String internal, String source) {
        for (ClassFile cf : appClasses) {
            if (cf.getClassName().equals(internal)) {
                if (findStaticMain(cf) == null) {
                    throw new J2csException("entry class " + internal + " (" + source
                            + ") has no static main(String[]) method");
                }
                return internal;
            }
        }
        throw new J2csException("entry class " + internal + " (" + source + ") not found in input");
    }

    private MethodEntry findStaticMain(ClassFile cf) {
        for (MethodEntry method : cf.getMethods()) {
            if (method.getName().equals("main")
                    && method.getDesc().equals(MAIN_DESCRIPTOR)
                    && (method.getAccess() & AccessFlags.ACC_STATIC) != 0) {
                return method;
            }
        }
        return null;
    }

    private static String toInternalName(String name) {
        return name.replace('.', '/');
    }
}
