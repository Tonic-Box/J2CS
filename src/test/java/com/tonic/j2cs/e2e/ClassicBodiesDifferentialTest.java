package com.tonic.j2cs.e2e;

import com.tonic.j2cs.harness.Differential;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

/**
 * The --classic-bodies escape hatch: structured emission is the default, but the goto/label
 * emitter remains the permanent fallback, so a representative fixture set is verified through it
 * to keep that path honest.
 */
@Execution(ExecutionMode.CONCURRENT)
class ClassicBodiesDifferentialTest {

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "HelloWorld", "Arith", "ControlFlow", "NestedLoops", "SwitchTest", "CmpTest", "Shapes",
            "ArraysTest", "MultiArray", "StringsTest", "Inherit", "Interfaces", "Generics",
            "Exceptions2", "Lambdas1", "Boxing2", "Collections3"})
    void sameOutputClassic(String fixture) throws Exception {
        Differential.assertSameOutput(fixture, List.of(), true);
    }
}
