package com.tonic.j2cs.frontend;

import com.tonic.j2cs.J2csException;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads named JDK classes from the running platform image so they can be transpiled as
 * generated shim classes. Classes already present in the pool are reused.
 */
public final class BootstrapLoader {

    public List<ClassFile> load(ClassPool pool, List<String> fqcns) {
        List<ClassFile> loaded = new ArrayList<>();
        for (String fqcn : fqcns) {
            String internal = fqcn.replace('.', '/');
            ClassFile existing = pool.get(internal);
            if (existing != null) {
                loaded.add(existing);
                continue;
            }
            try {
                loaded.add(pool.loadPlatformClass(internal + ".class"));
            } catch (IOException e) {
                throw new J2csException("failed to load JDK class " + fqcn + ": " + e.getMessage(), e);
            }
        }
        return loaded;
    }
}
