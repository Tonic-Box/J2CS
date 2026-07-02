package com.tonic.j2cs.frontend;

import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import java.util.List;

/**
 * The parsed input: every application class plus the resolved entry class (internal name form).
 */
public record LoadedInput(
        ClassPool pool,
        List<ClassFile> appClasses,
        String entryClassInternalName) {
}
