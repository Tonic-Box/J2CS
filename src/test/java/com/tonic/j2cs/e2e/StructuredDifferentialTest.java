package com.tonic.j2cs.e2e;

import com.tonic.j2cs.harness.Differential;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

@Execution(ExecutionMode.CONCURRENT)
class StructuredDifferentialTest {

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"HelloWorld", "Arith", "ControlFlow", "CmpTest", "Inherit"})
    void sameOutputStructured(String fixture) throws Exception {
        Differential.assertSameOutput(fixture, List.of(), true);
    }
}
