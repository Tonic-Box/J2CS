package com.tonic.j2cs.e2e;

import com.tonic.j2cs.harness.Differential;
import org.junit.jupiter.api.Test;

class DifferentialTest {

    @Test
    void helloWorld() throws Exception {
        Differential.assertSameOutput("HelloWorld");
    }

    @Test
    void arith() throws Exception {
        Differential.assertSameOutput("Arith");
    }

    @Test
    void controlFlow() throws Exception {
        Differential.assertSameOutput("ControlFlow");
    }
}
