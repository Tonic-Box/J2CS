package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.cfg.ExceptionHandler;
import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.naming.CsNamer;
import java.util.List;
import java.util.Map;

/**
 * Emits the dispatcher state machine wrapping a body with exception handlers: a
 * while(true)/try/switch(__state) resume loop, and per-region catch arms that bind the caught
 * throwable and continue at the handler's state.
 */
final class ExceptionMachineEmitter {

    private final CsWriter w;
    private final ExceptionLayout layout;
    private final LoweredMethod lowered;

    ExceptionMachineEmitter(CsWriter w, ExceptionLayout layout, LoweredMethod lowered) {
        this.w = w;
        this.layout = layout;
        this.lowered = lowered;
    }

    void emitWrappedBody(Runnable emitBlocks) {
        w.line("int __state = 0;");
        w.line("int __region = -1;");
        w.line("global::java.lang.Throwable __p = null;");
        w.open("while (true)");
        w.open("try");
        w.open("switch (__state)");
        for (Map.Entry<IRBlock, Integer> entry : layout.stateOf().entrySet()) {
            w.line("case " + entry.getValue() + ": goto B" + entry.getKey().getId() + ";");
        }
        w.close();
        emitBlocks.run();
        w.close();
        w.open("catch (global::System.Exception __e)");
        w.line("if (__e is global::System.NotSupportedException) throw;");
        w.line("__p = global::java.lang.JRuntime.Normalize(__e);");
        w.open("switch (__region)");
        emitCatchArms();
        w.close();
        w.line("throw;");
        w.close();
        w.close();
    }

    private void emitCatchArms() {
        List<List<ExceptionHandler>> regions = layout.regions();
        for (int region = 0; region < regions.size(); region++) {
            w.line("case " + region + ":");
            for (ExceptionHandler handler : regions.get(region)) {
                Integer state = layout.stateOf().get(handler.getHandlerBlock());
                SSAValue excValue = lowered.ir().getHandlerExceptionValue(handler.getHandlerBlock());
                Integer excSlot = excValue == null ? null : lowered.slotOf().get(excValue);
                String assign = "";
                if (excSlot != null) {
                    String cast = handler.getCatchType() == null
                            ? "__p"
                            : "(" + CsNamer.fqcn(handler.getCatchType().getInternalName()) + ")__p";
                    assign = "s" + excSlot + " = " + cast + "; ";
                }
                String action = assign + "__state = " + state + "; continue;";
                if (handler.getCatchType() == null) {
                    w.line("    { " + action + " }");
                } else {
                    w.line("    if (__p is " + CsNamer.fqcn(handler.getCatchType().getInternalName())
                            + ") { " + action + " }");
                }
            }
            w.line("    break;");
        }
    }
}
