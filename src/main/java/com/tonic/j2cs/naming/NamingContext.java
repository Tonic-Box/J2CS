package com.tonic.j2cs.naming;

import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Per-run naming state: one MemberNamer per application class, so call sites in any class
 * resolve member names against the owner's namer.
 */
public final class NamingContext {

    private final TypeMapper typeMapper;
    private final Map<String, MemberNamer> namers = new HashMap<>();

    public NamingContext(TypeMapper typeMapper, List<ClassFile> appClasses) {
        this.typeMapper = typeMapper;
        for (ClassFile cf : appClasses) {
            namers.put(cf.getClassName(), new MemberNamer(cf, typeMapper));
        }
    }

    public TypeMapper typeMapper() {
        return typeMapper;
    }

    public boolean isAppClass(String internalName) {
        return namers.containsKey(internalName);
    }

    public MemberNamer namerOf(String internalName) {
        MemberNamer namer = namers.get(internalName);
        if (namer == null) {
            throw new IllegalArgumentException("no namer for class " + internalName);
        }
        return namer;
    }
}
