package com.tonic.j2cs.e2e;

import com.tonic.j2cs.harness.Differential;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

/**
 * Runs the non-bootstrap fixtures with structured emission on. A method that hits an
 * unsupported node falls back to the goto emitter, so every fixture must still match the JVM;
 * a structured body that miscompiles fails the .NET build or diverges here.
 */
@Execution(ExecutionMode.CONCURRENT)
class StructuredDifferentialTest {

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "HelloWorld", "Arith", "ControlFlow", "NestedLoops", "Monitors", "SwitchTest", "CmpTest", "Shapes",
            "StaticsTest", "ArraysTest", "MultiArray", "StringsTest", "FizzBuzz", "Inherit",
            "Interfaces", "Generics", "TypeReconcile", "ArrayClone", "EnumAssert", "ShimGrowth",
            "Exceptions1", "Exceptions2", "Exceptions3", "Lambdas1", "Lambdas2",
            "Boxing1", "Boxing2", "BoxingGenerics", "Collections1", "Collections2", "Collections3"})
    void sameOutputStructured(String fixture) throws Exception {
        Differential.assertSameOutput(fixture, List.of(), true);
    }
}
