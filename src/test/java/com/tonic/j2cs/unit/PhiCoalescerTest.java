package com.tonic.j2cs.unit;

import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.lower.CopyInfo;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.j2cs.frontend.IrLifter;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.MethodPlan;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhiCoalescerTest {

    private static final String LOOP_SOURCE =
            "public class Loop {\n"
                    + "    static int sum(int n) {\n"
                    + "        int s = 0;\n"
                    + "        for (int i = 0; i < n; i++) {\n"
                    + "            s = s + i;\n"
                    + "        }\n"
                    + "        return s;\n"
                    + "    }\n"
                    + "}\n";

    @TempDir
    Path dir;

    @Test
    void phiGroupsShareOneSlot() throws IOException {
        LoweredMethod lowered = lowerLoopSum("first");
        IRMethod ir = lowered.ir();
        Map<SSAValue, List<CopyInfo>> mapping = ir.getPhiCopyMapping();
        assertFalse(mapping.isEmpty());
        for (Map.Entry<SSAValue, List<CopyInfo>> entry : mapping.entrySet()) {
            Integer phiSlot = lowered.slotOf().get(entry.getKey());
            for (CopyInfo copy : entry.getValue()) {
                assertEquals(phiSlot, lowered.slotOf().get(copy.copyValue()),
                        "copy v" + copy.copyValue().getId() + " must share the phi slot");
            }
        }
    }

    @Test
    void slotIdsAreDense() throws IOException {
        LoweredMethod lowered = lowerLoopSum("dense");
        int slotCount = lowered.slots().size();
        for (int i = 0; i < slotCount; i++) {
            assertEquals(i, lowered.slots().get(i).slotId());
        }
        for (Integer slot : lowered.slotOf().values()) {
            assertTrue(slot >= 0 && slot < slotCount);
        }
    }

    @Test
    void parametersGetSlots() throws IOException {
        LoweredMethod lowered = lowerLoopSum("params");
        for (SSAValue param : lowered.ir().getParameters()) {
            assertTrue(lowered.slotOf().containsKey(param));
        }
    }

    @Test
    void coalescingIsDeterministicAcrossLifts() throws IOException {
        String first = canonical(lowerLoopSum("det1"));
        String second = canonical(lowerLoopSum("det2"));
        assertEquals(first, second);
    }

    @Test
    void abstractMethodsDegradeToUnsupported() throws IOException {
        ClassFile cf = compile("Abs", "public abstract class Abs { public abstract int f(); }", "abs");
        MethodPlan plan = new IrLifter(new TypeMapper(), false).lower(cf, method(cf, "f"));
        assertInstanceOf(MethodPlan.Unsupported.class, plan);
        assertTrue(((MethodPlan.Unsupported) plan).reason().contains("no bytecode"));
    }

    private LoweredMethod lowerLoopSum(String tag) throws IOException {
        ClassFile cf = compile("Loop", LOOP_SOURCE, tag);
        MethodPlan plan = new IrLifter(new TypeMapper(), false).lower(cf, method(cf, "sum"));
        assertInstanceOf(MethodPlan.Supported.class, plan,
                () -> "expected supported plan but was: " + plan);
        return ((MethodPlan.Supported) plan).method();
    }

    private static String canonical(LoweredMethod lowered) {
        StringBuilder sb = new StringBuilder();
        for (SSAValue param : lowered.ir().getParameters()) {
            sb.append("p").append(lowered.slotOf().get(param)).append(';');
        }
        for (IRBlock block : lowered.ir().getReversePostOrder()) {
            sb.append("B").append(block.getId()).append(':');
            for (IRInstruction instr : block.getInstructions()) {
                SSAValue result = instr.getResult();
                sb.append(result == null ? "-" : lowered.slotOf().get(result)).append(',');
            }
        }
        sb.append("|slots=").append(lowered.slots().size());
        return sb.toString();
    }

    private ClassFile compile(String className, String source, String tag) throws IOException {
        Path src = Files.writeString(dir.resolve(tag + "-" + className + ".java"), source);
        Path renamed = dir.resolve(tag).resolve(className + ".java");
        Files.createDirectories(renamed.getParent());
        Files.move(src, renamed);
        Path classes = dir.resolve("classes-" + tag);
        JavacHelper.compile(List.of(renamed), classes);
        ClassPool pool = new ClassPool(true);
        try (InputStream in = Files.newInputStream(classes.resolve(className + ".class"))) {
            return pool.loadClass(in);
        }
    }

    private static MethodEntry method(ClassFile cf, String name) {
        for (MethodEntry m : cf.getMethods()) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        throw new AssertionError("method " + name + " not found");
    }
}
