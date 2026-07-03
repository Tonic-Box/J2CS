package com.tonic.j2cs.unit;

import com.tonic.analysis.ssa.SSA;
import com.tonic.analysis.ssa.cfg.ExceptionHandler;
import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.ir.LoadLocalInstruction;
import com.tonic.analysis.ssa.ir.StoreLocalInstruction;
import com.tonic.analysis.ssa.lower.PhiEliminator;
import com.tonic.analysis.ssa.transform.DeadCodeElimination;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.j2cs.frontend.HandlerSupport;
import com.tonic.j2cs.frontend.PhiCoalescer;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.SlotDecl;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import com.tonic.parser.MethodEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandlerFrontendTest {

    @TempDir
    Path dir;

    @Test
    void blockOrderReachesHandlerBlocks() throws IOException {
        Lowered result = lower("H1",
                "class H1 { static int f(int x) {"
                        + " int a = 1;"
                        + " try { a = 2; return 100 / x; }"
                        + " catch (ArithmeticException e) { return a; } } }",
                "f");
        for (ExceptionHandler handler : result.lowered.ir().getExceptionHandlers()) {
            assertTrue(result.lowered.blockOrder().contains(handler.getHandlerBlock()),
                    "handler block missing from block order");
        }
        assertTrue(result.lowered.blockOrder().size() >= result.lowered.ir().getBlocks().size());
    }

    @Test
    void handlerExceptionValueGetsSlot() throws IOException {
        Lowered result = lower("H2",
                "class H2 { static int f(int x) {"
                        + " try { return 100 / x; }"
                        + " catch (ArithmeticException e) { return e.hashCode(); } } }",
                "f");
        for (ExceptionHandler handler : result.lowered.ir().getExceptionHandlers()) {
            SSAValue value = result.lowered.ir().getHandlerExceptionValue(handler.getHandlerBlock());
            assertNotNull(value);
            assertNotNull(result.lowered.slotOf().get(value), "handler exception value has no slot");
        }
    }

    @Test
    void handlerPhiWebsShareSlots() throws IOException {
        Lowered result = lower("H3",
                "class H3 { static int f(int x) {"
                        + " int a = 1;"
                        + " try {"
                        + "  if (x > 1) { a = 3; }"
                        + "  a = a + x;"
                        + "  return 100 / x;"
                        + " } catch (ArithmeticException e) { return a; } } }",
                "f");
        assertFalse(result.captures.unionPairs().isEmpty(), "expected handler phi union pairs");
        for (HandlerSupport.UnionPair pair : result.captures.unionPairs()) {
            assertEquals(result.lowered.slotOf().get(pair.a()), result.lowered.slotOf().get(pair.b()),
                    "handler phi web must share one slot");
        }
    }

    @Test
    void multiCatchValueWidensToThrowable() throws IOException {
        Lowered result = lower("H4",
                "class H4 { static int f(String s) {"
                        + " try { return s.length(); }"
                        + " catch (NullPointerException | IllegalStateException e) { return -1; } } }",
                "f");
        assertFalse(result.captures.typeOverrides().isEmpty(), "expected a multi-catch widening");
        for (ExceptionHandler handler : result.lowered.ir().getExceptionHandlers()) {
            SSAValue value = result.lowered.ir().getHandlerExceptionValue(handler.getHandlerBlock());
            Integer slot = result.lowered.slotOf().get(value);
            assertNotNull(slot);
            SlotDecl decl = result.lowered.slots().get(slot);
            assertEquals("Ljava/lang/Throwable;", decl.computeType().getDescriptor());
        }
    }

    private record Lowered(LoweredMethod lowered, HandlerSupport.Captures captures) {
    }

    private Lowered lower(String className, String source, String methodName) throws IOException {
        Path src = Files.writeString(
                Files.createDirectories(dir.resolve("src-" + className)).resolve(className + ".java"),
                source);
        Path classes = dir.resolve("classes-" + className);
        JavacHelper.compile(List.of(src), classes);
        ClassPool pool = new ClassPool(true);
        ClassFile cf;
        try (InputStream in = Files.newInputStream(classes.resolve(className + ".class"))) {
            cf = pool.loadClass(in);
        }
        MethodEntry method = cf.getMethods().stream()
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElseThrow();
        IRMethod ir = new SSA(cf.getConstPool()).withExceptionLocalResolution().lift(method);
        strip(ir);
        DeadCodeElimination.removeUnreachableBlocks(ir);
        HandlerSupport.Captures captures = HandlerSupport.capture(ir);
        new PhiEliminator().eliminate(ir);
        return new Lowered(PhiCoalescer.coalesce(ir, new TypeMapper(), captures), captures);
    }

    private static void strip(IRMethod method) {
        for (IRBlock block : method.getBlocks()) {
            List<IRInstruction> toRemove = new ArrayList<>();
            for (IRInstruction instr : block.getInstructions()) {
                if (instr instanceof LoadLocalInstruction || instr instanceof StoreLocalInstruction) {
                    toRemove.add(instr);
                }
            }
            for (IRInstruction instr : toRemove) {
                block.removeInstruction(instr);
            }
        }
    }
}
