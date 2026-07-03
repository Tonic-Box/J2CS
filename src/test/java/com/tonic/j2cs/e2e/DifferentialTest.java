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
    void lambdas1() throws Exception {
        Differential.assertSameOutput("Lambdas1");
    }

    @Test
    void boxing1() throws Exception {
        Differential.assertSameOutput("Boxing1");
    }

    @Test
    void boxing2() throws Exception {
        Differential.assertSameOutput("Boxing2");
    }

    @Test
    void boxingGenerics() throws Exception {
        Differential.assertSameOutput("BoxingGenerics");
    }

    @Test
    void collections1() throws Exception {
        Differential.assertSameOutput("Collections1");
    }

    @Test
    void collections2() throws Exception {
        Differential.assertSameOutput("Collections2");
    }

    @Test
    void collections3() throws Exception {
        Differential.assertSameOutput("Collections3");
    }

    @Test
    void bootstrapBoolean() throws Exception {
        Differential.assertSameOutput("Bootstrap", java.util.List.of("java.lang.Boolean"));
    }

    @Test
    void bootstrapWrappers() throws Exception {
        Differential.assertSameOutput("WrapperBootstrap", java.util.List.of(
                "java.lang.Number", "java.lang.Integer", "java.lang.Long",
                "java.lang.Short", "java.lang.Byte"));
    }

    @Test
    void bootstrapWrappers2() throws Exception {
        Differential.assertSameOutput("WrapperBootstrap2", java.util.List.of(
                "java.lang.Number", "java.lang.Integer", "java.lang.Long", "java.lang.Short",
                "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Character"));
    }

    @Test
    void lambdas2() throws Exception {
        Differential.assertSameOutput("Lambdas2");
        String report = java.nio.file.Files.readString(
                java.nio.file.Path.of("build", "e2e", "Lambdas2", "out", "j2cs-report.txt"));
        org.junit.jupiter.api.Assertions.assertTrue(
                report.contains("functional interface not in input: java/lang/Runnable"),
                "report should list the unsupported Runnable lambda");
    }

    @Test
    void unsupportedGraceful() throws Exception {
        Differential.assertSameOutput("UnsupportedGraceful");
        String report = java.nio.file.Files.readString(
                java.nio.file.Path.of("build", "e2e", "UnsupportedGraceful", "out", "j2cs-report.txt"));
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("allocation of type not in input"),
                "report should list the TreeSet allocation");
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("java/util/TreeMap"),
                "report should list the stubbed TreeMap type");
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("superclass not in input: java/lang/Enum"),
                "report should show the enum degrading via its stubbed superclass");
        org.junit.jupiter.api.Assertions.assertTrue(report.contains("native method"),
                "report should list the native method");
    }
}
