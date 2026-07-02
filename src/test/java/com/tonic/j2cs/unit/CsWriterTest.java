package com.tonic.j2cs.unit;

import com.tonic.j2cs.emit.CsWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsWriterTest {

    @Test
    void nestsIndentation() {
        String text = new CsWriter()
                .open("namespace demo")
                .open("internal class A")
                .line("int x = 0;")
                .line()
                .close()
                .close()
                .toString();
        assertEquals("namespace demo\n{\n    internal class A\n    {\n        int x = 0;\n\n    }\n}\n", text);
    }
}
