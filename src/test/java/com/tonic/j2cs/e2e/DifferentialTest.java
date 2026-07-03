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

    @Test
    void switchTest() throws Exception {
        Differential.assertSameOutput("SwitchTest");
    }

    @Test
    void cmpTest() throws Exception {
        Differential.assertSameOutput("CmpTest");
    }

    @Test
    void shapes() throws Exception {
        Differential.assertSameOutput("Shapes");
    }

    @Test
    void staticsTest() throws Exception {
        Differential.assertSameOutput("StaticsTest");
    }

    @Test
    void arraysTest() throws Exception {
        Differential.assertSameOutput("ArraysTest");
    }
}
