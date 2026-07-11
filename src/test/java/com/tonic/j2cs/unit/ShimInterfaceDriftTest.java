package com.tonic.j2cs.unit;

import com.tonic.j2cs.shims.ShimRegistry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Drift guard for ShimRegistry.SHIM_INTERFACES: it must list exactly the shim sources whose C#
 * form is an interface (declared {@code public interface} in javacompat), since callers rely on it
 * to decide whether an Object-member call on a shim receiver needs an upcast to Object.
 */
class ShimInterfaceDriftTest {

    private static final Path SHIM_DIR = Path.of("javacompat");

    @Test
    void shimInterfacesMatchInterfaceSourceFiles() throws IOException {
        Set<String> declared;
        try (Stream<Path> list = Files.list(SHIM_DIR)) {
            declared = list.filter(p -> p.getFileName().toString().endsWith(".cs"))
                    .filter(ShimInterfaceDriftTest::declaresInterface)
                    .map(p -> {
                        String file = p.getFileName().toString();
                        return file.substring(0, file.length() - ".cs".length()).replace('.', '/');
                    })
                    .collect(Collectors.toCollection(TreeSet::new));
        }
        assertEquals(declared, new TreeSet<>(ShimRegistry.shimInterfaces()),
                "ShimRegistry.SHIM_INTERFACES must list exactly the shim .cs files declaring a public interface");
    }

    private static boolean declaresInterface(Path file) {
        try {
            return Files.readAllLines(file).stream().anyMatch(l -> l.contains("public interface "));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
