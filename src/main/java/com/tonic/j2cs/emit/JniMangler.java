package com.tonic.j2cs.emit;

import com.tonic.j2cs.types.TypeMapper;

/**
 * JNI native-method name mangling. Produces the exported symbol a classic-JNI native method binds
 * to, matching the JVM's own mangling so a P/Invoke against the loaded library resolves the same
 * entry point the JVM would. The long form (argument-descriptor suffix) is emitted only for names
 * that are overloaded within their declaring class, exactly as the JVM does.
 */
final class JniMangler {

    private JniMangler() {
    }

    static String shortName(String ownerInternal, String methodName) {
        return "Java_" + mangle(ownerInternal) + "_" + mangle(methodName);
    }

    static String longName(String ownerInternal, String methodName, String methodDescriptor) {
        String args = methodDescriptor.substring(1, methodDescriptor.indexOf(')'));
        return shortName(ownerInternal, methodName) + "__" + mangle(args);
    }

    static String entryPoint(String ownerInternal, String methodName, String descriptor, boolean overloaded) {
        return overloaded
                ? longName(ownerInternal, methodName, descriptor)
                : shortName(ownerInternal, methodName);
    }

    static String argDescriptors(String methodDescriptor) {
        return String.join("", TypeMapper.splitParams(methodDescriptor));
    }

    private static String mangle(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 8);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                sb.append(c);
            } else if (c == '/' || c == '.') {
                sb.append('_');
            } else if (c == '_') {
                sb.append("_1");
            } else if (c == ';') {
                sb.append("_2");
            } else if (c == '[') {
                sb.append("_3");
            } else {
                sb.append(String.format("_0%04x", (int) c));
            }
        }
        return sb.toString();
    }
}
