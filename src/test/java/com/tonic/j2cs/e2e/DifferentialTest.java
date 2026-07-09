package com.tonic.j2cs.e2e;

import com.tonic.j2cs.harness.Differential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Execution(ExecutionMode.CONCURRENT)
class DifferentialTest {

    private static Arguments fixture(String name, String... bootstrap) {
        return Arguments.of(name, List.of(bootstrap));
    }

    static Stream<Arguments> fixtures() {
        return Stream.of(
                fixture("HelloWorld"),
                fixture("Arith"),
                fixture("NumericConstants"),
                fixture("ControlFlow"),
                fixture("NestedLoops"),
                fixture("Monitors"),
                fixture("SwitchTest"),
                fixture("EnumSwitch"),
                fixture("CmpTest"),
                fixture("Shapes"),
                fixture("StaticsTest"),
                fixture("ArraysTest"),
                fixture("MultiArray"),
                fixture("StringsTest"),
                fixture("StringOps"),
                fixture("TextOps"),
                fixture("MapComparator"),
                fixture("ArraysCollections"),
                fixture("StreamOps"),
                fixture("CollectorsOps"),
                fixture("RandomTest"),
                fixture("RegexTest"),
                fixture("CollectionsExtra"),
                fixture("TimeTest"),
                fixture("RuntimeMem"),
                fixture("Printf"),
                fixture("Streams"),
                fixture("Reflection"),
                fixture("FizzBuzz"),
                fixture("Inherit"),
                fixture("Interfaces"),
                fixture("Generics"),
                fixture("TypeReconcile"),
                fixture("ArrayClone"),
                fixture("EnumAssert"),
                fixture("ShimGrowth"),
                fixture("Exceptions1"),
                fixture("Exceptions2"),
                fixture("Exceptions3"),
                fixture("Lambdas1"),
                fixture("Lambdas2"),
                fixture("BoundMethodRef"),
                fixture("ArrayBox"),
                fixture("LambdaArrayAdapt"),
                fixture("ArrayClassInfo"),
                fixture("ReflectArray"),
                fixture("MapOrdering"),
                fixture("CollectorsGaps"),
                fixture("VarargsInterface"),
                fixture("PrimitiveStreams"),
                fixture("NioFiles"),
                fixture("NioExtras"),
                fixture("IoDemo"),
                fixture("TryResources"),
                fixture("Tier1"),
                fixture("TimeText"),
                fixture("DateCal"),
                fixture("UtilCollections"),
                fixture("EnumStrings"),
                fixture("Concurrency"),
                fixture("NavEnum"),
                fixture("NetUri"),
                fixture("Crypto"),
                fixture("Boxing1"),
                fixture("Boxing2"),
                fixture("BoxingGenerics"),
                fixture("Collections1"),
                fixture("Collections2"),
                fixture("Collections3"),
                fixture("Bootstrap", "java.lang.Boolean"),
                fixture("WrapperBootstrap",
                        "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.Byte"),
                fixture("WrapperBootstrap2",
                        "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.Byte",
                        "java.lang.Double", "java.lang.Float", "java.lang.Character"),
                fixture("ObjectBootstrap", "java.lang.Object"),
                fixture("ObjectsBootstrap", "java.util.Objects"),
                fixture("CollectionsBootstrap", "java.util.ArrayList"),
                fixture("HashMapBootstrap", "java.util.HashMap", "java.util.HashSet"),
                fixture("SystemBootstrap", "java.lang.System"),
                fixture("MathBootstrap", "java.lang.Math"),
                fixture("ExceptionBootstrap",
                        "java.lang.NullPointerException", "java.lang.ArithmeticException",
                        "java.lang.ClassCastException", "java.lang.ArrayIndexOutOfBoundsException",
                        "java.lang.IllegalArgumentException", "java.lang.IllegalStateException",
                        "java.lang.NumberFormatException"),
                fixture("AllBootstrap",
                        "java.lang.Object", "java.lang.Math", "java.lang.System",
                        "java.util.Objects", "java.lang.Long", "java.lang.Character",
                        "java.lang.ClassCastException", "java.lang.ArrayStoreException"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("fixtures")
    void sameOutput(String fixture, List<String> bootstrap) throws Exception {
        Differential.assertSameOutput(fixture, bootstrap);
    }

    @Test
    void unsupportedGraceful() throws Exception {
        Differential.assertSameOutput("UnsupportedGraceful");
        String report = Files.readString(
                Path.of("build", "e2e", "UnsupportedGraceful", "out", "j2cs-report.txt"));
        Assertions.assertTrue(report.contains("allocation of type not in input"),
                "report should list the TreeSet allocation");
        Assertions.assertTrue(report.contains("native method"),
                "report should list the native method");
    }
}
