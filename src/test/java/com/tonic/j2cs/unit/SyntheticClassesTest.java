package com.tonic.j2cs.unit;

import com.tonic.j2cs.emit.SyntheticClasses;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SyntheticClassesTest {

    @Test
    void sameSiteReusesOneClass() {
        SyntheticClasses sink = new SyntheticClasses();
        String first = sink.claimClassName("com/example/Main", 42);
        String second = sink.claimClassName("com/example/Main", 42);
        assertEquals(first, second);
    }

    @Test
    void distinctSitesGetDistinctNames() {
        SyntheticClasses sink = new SyntheticClasses();
        assertNotEquals(sink.claimClassName("com/example/Main", 42),
                sink.claimClassName("com/example/Main", 43));
    }

    @Test
    void crossClassSanitizationCollisionIsResolved() {
        SyntheticClasses sink = new SyntheticClasses();
        String a = sink.claimClassName("a/b_c", 1);
        String b = sink.claimClassName("a_b/c", 1);
        assertNotEquals(a, b);
    }

    @Test
    void registrationTracksAndEmits() {
        SyntheticClasses sink = new SyntheticClasses();
        String name = sink.claimClassName("com/example/Main", 7);
        assertFalse(sink.isRegistered("com/example/Main", 7));
        sink.register("com/example/Main", 7, name, "internal class " + name + " {}");
        assertTrue(sink.isRegistered("com/example/Main", 7));
        assertTrue(sink.files().containsKey("j2cs.synthetic." + name));
        assertTrue(sink.fqcnOf(name).startsWith("global::j2cs.synthetic."));
    }
}
