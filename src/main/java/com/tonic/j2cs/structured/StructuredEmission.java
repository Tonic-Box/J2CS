package com.tonic.j2cs.structured;

import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.report.TranspileReport;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.util.Optional;

/**
 * Structured-body pipeline: recover the AST for a method and print it as C#. Empty on any
 * failure; the caller falls back to the goto emitter, so output is never worse than classic.
 */
public final class StructuredEmission implements com.tonic.j2cs.emit.BodyOverride {

    private final StructuredRecovery recovery = new StructuredRecovery();
    private final StructuredBodyEmitter emitter;
    private final TranspileReport report;

    public StructuredEmission(NamingContext naming, com.tonic.j2cs.emit.SyntheticClasses synthetics,
                              TranspileReport report) {
        this.emitter = new StructuredBodyEmitter(naming, synthetics);
        this.report = report;
    }

    @Override
    public Optional<String> tryEmit(ClassFile classFile, MethodEntry method, int indentDepth) {
        String where = classFile.getClassName() + "." + method.getName() + method.getDesc();
        Optional<StructuredRecovery.Recovered> recovered;
        try {
            recovered = recovery.recover(classFile, method);
        } catch (RuntimeException e) {
            report.classicBody(where + ": " + e.getMessage());
            return Optional.empty();
        }
        if (recovered.isEmpty()) {
            report.classicBody(where + ": recovery degraded");
            return Optional.empty();
        }
        try {
            String body = emitter.emit(classFile, method, recovered.get(), indentDepth, true);
            report.structuredBody();
            return Optional.of(body);
        } catch (RuntimeException foldFailure) {
            // A folded for-init can put a counter that is actually live past its loop out of scope.
            // Retry without folding (identical to the pre-fold emission) before degrading to classic.
            try {
                String body = emitter.emit(classFile, method, recovered.get(), indentDepth, false);
                report.structuredBody();
                return Optional.of(body);
            } catch (RuntimeException e) {
                report.classicBody(where + ": " + e.getMessage());
                return Optional.empty();
            }
        }
    }
}
