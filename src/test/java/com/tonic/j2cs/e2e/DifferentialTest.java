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

    @Test
    void stringsTest() throws Exception {
        Differential.assertSameOutput("StringsTest");
    }

    @Test
    void fizzBuzz() throws Exception {
        Differential.assertSameOutput("FizzBuzz");
    }

    @Test
    void inherit() throws Exception {
        Differential.assertSameOutput("Inherit");
    }

    @Test
    void interfaces() throws Exception {
        Differential.assertSameOutput("Interfaces");
    }

    @Test
    void generics() throws Exception {
        Differential.assertSameOutput("Generics");
    }

    @Test
    void shimGrowth() throws Exception {
        Differential.assertSameOutput("ShimGrowth");
    }

    @Test
    void exceptions1() throws Exception {
        Differential.assertSameOutput("Exceptions1");
    }

    @Test
    void exceptions2() throws Exception {
        Differential.assertSameOutput("Exceptions2");
    }

    @Test
    void exceptions3() throws Exception {
        Differential.assertSameOutput("Exceptions3");
    }

    @Test
    void unsupportedGraceful() throws Exception {
        Differential.assertSameOutput("UnsupportedGraceful");
        String report = java.nio.file.Files.readString(
                java.nio.file.Path.of("build", "e2e", "UnsupportedGraceful", "out", "j2cs-report.txt"));
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("allocation of type not in input"),
                "report should list the ArrayList allocation");
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("java/util/List"),
                "report should list the stubbed List type");
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("superclass not in input: java/lang/Enum"),
                "report should show the enum degrading via its stubbed superclass");
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("native method"),
                "report should list the native method");
    }
}
