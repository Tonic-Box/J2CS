package com.tonic.j2cs.frontend;

import com.tonic.j2cs.J2csException;
import com.tonic.j2cs.bootstrap.BootstrapPolicy;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import com.tonic.parser.constpool.ClassRefItem;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Loads named JDK classes from the running platform image, transitively pulling their
 * bootstrappable closure (superclasses, interfaces, and referenced types that are on the
 * allowlist) so one requested root brings its whole subtree. Requested roots are honored even
 * if off the allowlist; the closure walk only adds allowlisted classes, which bounds it.
 */
public final class BootstrapLoader {

    public List<ClassFile> load(ClassPool pool, List<String> fqcns) {
        Set<String> seen = new LinkedHashSet<>();
        Deque<String> worklist = new ArrayDeque<>();
        for (String fqcn : fqcns) {
            String internal = fqcn.replace('.', '/');
            if (seen.add(internal)) {
                worklist.add(internal);
            }
        }
        List<ClassFile> loaded = new ArrayList<>();
        while (!worklist.isEmpty()) {
            String internal = worklist.removeFirst();
            ClassFile cf = loadOne(pool, internal);
            loaded.add(cf);
            for (String edge : edges(cf)) {
                if (BootstrapPolicy.expandsInto(edge) && seen.add(edge)) {
                    worklist.add(edge);
                }
            }
        }
        return loaded;
    }

    private ClassFile loadOne(ClassPool pool, String internal) {
        ClassFile existing = pool.get(internal);
        if (existing != null) {
            return existing;
        }
        try {
            return pool.loadPlatformClass(internal + ".class");
        } catch (IOException e) {
            throw new J2csException("failed to load JDK class " + internal.replace('/', '.')
                    + ": " + e.getMessage(), e);
        }
    }

    private Set<String> edges(ClassFile cf) {
        Set<String> edges = new LinkedHashSet<>();
        String superName = cf.getSuperClassName();
        if (superName != null && !superName.startsWith("[")) {
            edges.add(superName);
        }
        edges.addAll(cf.getInterfaceNames());
        cf.getConstPool().getItems().forEach(item -> {
            if (item instanceof ClassRefItem classRef) {
                String name = classRef.getClassName();
                if (name != null && !name.startsWith("[")) {
                    edges.add(name);
                }
            }
        });
        return edges;
    }
}
