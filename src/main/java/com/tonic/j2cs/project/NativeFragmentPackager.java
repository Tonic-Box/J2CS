package com.tonic.j2cs.project;

import com.tonic.j2cs.J2csException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Copies hand-written native fragments for bootstrapped classes into a generated solution.
 * Fragments are bootstrap-scoped: only classes actually being bootstrapped get their fragment,
 * so non-bootstrap solutions never receive a dangling partial class.
 */
public final class NativeFragmentPackager {

    private static final String RESOURCE_ROOT = "/javacompat/native/";

    public void copy(Path appDir, List<String> fragmentResources) {
        if (fragmentResources.isEmpty()) {
            return;
        }
        Path targetDir = appDir.resolve("native");
        try {
            Files.createDirectories(targetDir);
            for (String fileName : fragmentResources) {
                try (InputStream in = openResource(RESOURCE_ROOT + fileName)) {
                    Files.copy(in, targetDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            throw new J2csException("failed to copy native fragments: " + e.getMessage(), e);
        }
    }

    private InputStream openResource(String path) {
        InputStream in = NativeFragmentPackager.class.getResourceAsStream(path);
        if (in == null) {
            throw new J2csException("native fragment resource missing from classpath: " + path);
        }
        return in;
    }
}
