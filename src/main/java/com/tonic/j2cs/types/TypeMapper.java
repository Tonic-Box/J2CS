package com.tonic.j2cs.types;

import com.tonic.analysis.ssa.type.IRType;
import com.tonic.j2cs.J2csException;
import com.tonic.j2cs.naming.CsNamer;
import java.util.ArrayList;
import java.util.List;

/**
 * Maps JVM type descriptors to C# types. Storage positions (fields, array elements, params,
 * returns) keep sub-int widths; compute positions (slot locals) widen Z/B/S/C to int, matching
 * the JVM's operand-stack model.
 */
public final class TypeMapper {

    public static final CsType INT = new CsType("int", CsType.Kind.PRIMITIVE);

    public CsType storageType(String descriptor) {
        return map(descriptor, false);
    }

    public CsType computeType(String descriptor) {
        return map(descriptor, true);
    }

    public CsType computeType(IRType type) {
        return map(type.getDescriptor(), true);
    }

    public CsType returnType(String methodDescriptor) {
        return storageType(returnDescriptor(methodDescriptor));
    }

    public static String returnDescriptor(String methodDescriptor) {
        int close = methodDescriptor.indexOf(')');
        if (close < 0) {
            throw new J2csException("malformed method descriptor: " + methodDescriptor);
        }
        return methodDescriptor.substring(close + 1);
    }

    /** The internal name of a plain reference descriptor (L...;), else null. */
    public static String unwrapReference(String descriptor) {
        return descriptor.startsWith("L") && descriptor.endsWith(";")
                ? descriptor.substring(1, descriptor.length() - 1)
                : null;
    }

    public static boolean isPrimitiveDescriptor(String descriptor) {
        char c = descriptor.charAt(0);
        return c != 'L' && c != '[';
    }

    public List<CsType> paramTypes(String methodDescriptor) {
        List<CsType> params = new ArrayList<>();
        for (String descriptor : splitParams(methodDescriptor)) {
            params.add(storageType(descriptor));
        }
        return params;
    }

    public static List<String> splitParams(String methodDescriptor) {
        int close = methodDescriptor.indexOf(')');
        if (!methodDescriptor.startsWith("(") || close < 0) {
            throw new J2csException("malformed method descriptor: " + methodDescriptor);
        }
        List<String> descriptors = new ArrayList<>();
        int i = 1;
        while (i < close) {
            int start = i;
            while (methodDescriptor.charAt(i) == '[') {
                i++;
            }
            char c = methodDescriptor.charAt(i);
            if (c == 'L') {
                i = methodDescriptor.indexOf(';', i) + 1;
                if (i == 0) {
                    throw new J2csException("malformed method descriptor: " + methodDescriptor);
                }
            } else {
                i++;
            }
            descriptors.add(methodDescriptor.substring(start, i));
        }
        return descriptors;
    }

    private CsType map(String descriptor, boolean compute) {
        if (descriptor.isEmpty()) {
            throw new J2csException("empty type descriptor");
        }
        char c = descriptor.charAt(0);
        return switch (c) {
            case 'Z', 'I' -> INT;
            case 'B' -> compute ? INT : new CsType("sbyte", CsType.Kind.PRIMITIVE);
            case 'S' -> compute ? INT : new CsType("short", CsType.Kind.PRIMITIVE);
            case 'C' -> compute ? INT : new CsType("char", CsType.Kind.PRIMITIVE);
            case 'J' -> new CsType("long", CsType.Kind.PRIMITIVE);
            case 'F' -> new CsType("float", CsType.Kind.PRIMITIVE);
            case 'D' -> new CsType("double", CsType.Kind.PRIMITIVE);
            case 'V' -> new CsType("void", CsType.Kind.VOID);
            case 'L' -> {
                if (!descriptor.endsWith(";")) {
                    throw new J2csException("malformed reference descriptor: " + descriptor);
                }
                yield new CsType(CsNamer.fqcn(descriptor.substring(1, descriptor.length() - 1)),
                        CsType.Kind.REF, descriptor);
            }
            case '[' -> {
                CsType element = map(descriptor.substring(1), false);
                yield new CsType(element.csText() + "[]", CsType.Kind.ARRAY, descriptor);
            }
            default -> throw new J2csException("unknown type descriptor: " + descriptor);
        };
    }
}
