package com.tonic.j2cs.emit;

import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.util.Optional;

/** Alternative body emission tried before the goto emitter; empty means fall back. */
public interface BodyOverride {

    Optional<String> tryEmit(ClassFile classFile, MethodEntry method, int indentDepth);
}
