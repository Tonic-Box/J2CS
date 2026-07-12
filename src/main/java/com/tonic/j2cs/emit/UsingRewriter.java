package com.tonic.j2cs.emit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Post-processes one generated C# file to drop {@code global::} qualifiers and emit per-file
 * {@code using} directives instead, keeping {@code global::} only where shortening would be
 * ambiguous.
 *
 * <p>Strategy (safe by construction, backstopped by the compile-everything test suite):
 * <ul>
 *   <li>References under our own namespaces (lowercase roots: {@code java.*}, {@code javax.*},
 *       {@code jdefault}, {@code j2cs.*}, synthetics) are shortened via namespace {@code using}s.
 *       Namespace segments are the leading lowercase ({@code @?[a-z][a-z0-9]*}) parts; the first
 *       non-lowercase segment is the simple type name, the rest is member access.</li>
 *   <li>BCL references are shortened only for a fixed allowlist of <em>deep</em> namespaces and
 *       only when the simple name does not collide with any of our own type names. The root
 *       {@code System} namespace is never imported (its Object/String/Math/... collide with
 *       java.lang core types), so those stay {@code global::}.</li>
 *   <li>A name is shortened only if it resolves to exactly one fully-qualified name in the file and
 *       is not shadowed by a type declared in the file. Anything uncertain stays {@code global::}.</li>
 *   <li>The scan skips string/char literals and comments, so {@code global::} inside a literal is
 *       never touched.</li>
 * </ul>
 */
public final class UsingRewriter {

    private static final String GLOBAL = "global::";

    /** Deep BCL namespaces safe to import; root System is deliberately excluded. */
    private static final Set<String> BCL_NAMESPACES = Set.of(
            "System.Text", "System.Collections.Generic", "System.Collections.Concurrent",
            "System.Globalization", "System.Threading", "System.Threading.Tasks",
            "System.Numerics", "System.Buffers", "System.Buffers.Binary", "System.Linq", "System.IO",
            "Avalonia", "Avalonia.Controls", "Avalonia.Controls.Primitives", "Avalonia.Controls.Shapes",
            "Avalonia.Media", "Avalonia.Media.Imaging", "Avalonia.Layout", "Avalonia.Input",
            "Avalonia.Styling", "Avalonia.Threading", "Avalonia.Interactivity", "Avalonia.VisualTree");

    private static final Pattern DECL =
            Pattern.compile("\\b(?:class|interface|struct|enum)\\s+(@?[A-Za-z_][A-Za-z0-9_]*)");
    private static final Pattern NAMESPACE =
            Pattern.compile("(?m)^namespace\\s+([@\\w.]+)");

    private final Set<String> ownSimpleNames;
    private final Map<String, Set<String>> namespaceTypes;

    /**
     * @param ownSimpleNames simple C# type names of all shim + app + generated types, used to keep
     *                       BCL references global:: when they collide with one of our types.
     * @param namespaceTypes namespace → simple type names it declares, used to keep a reference
     *                       global:: when shortening it would make its simple name ambiguous
     *                       against another namespace already imported into the same file.
     */
    public UsingRewriter(Set<String> ownSimpleNames, Map<String, Set<String>> namespaceTypes) {
        this.ownSimpleNames = ownSimpleNames;
        this.namespaceTypes = namespaceTypes;
    }

    public Map<String, String> rewriteAll(Map<String, String> files, Map<String, Set<String>> membersByFile) {
        Map<String, String> out = new java.util.LinkedHashMap<>();
        for (Map.Entry<String, String> e : files.entrySet()) {
            out.put(e.getKey(), rewrite(e.getValue(), membersByFile.getOrDefault(e.getKey(), Set.of())));
        }
        return out;
    }

    public String rewrite(String cs) {
        return rewrite(cs, Set.of());
    }

    public String rewrite(String cs, Set<String> memberNames) {
        Matcher nm = NAMESPACE.matcher(cs);
        if (!nm.find()) {
            return cs;
        }
        String fileNs = nm.group(1);

        Set<String> declared = new java.util.HashSet<>();
        Matcher dm = DECL.matcher(cs);
        while (dm.find()) {
            declared.add(dm.group(1));
        }

        List<Ref> refs = scan(cs);
        if (refs.isEmpty()) {
            return cs;
        }

        // Group by simple name: only names that resolve to a single FQN are shortenable.
        Map<String, Set<String>> fqnsBySimple = new java.util.HashMap<>();
        for (Ref r : refs) {
            fqnsBySimple.computeIfAbsent(r.simple, k -> new java.util.HashSet<>()).add(r.fqn());
        }

        List<Ref> candidates = new ArrayList<>();
        for (Ref r : refs) {
            if (fqnsBySimple.get(r.simple).size() != 1) {
                continue;
            }
            // A member (field, enum constant, method) of this file's class shadows a same-named type
            // in unqualified expression context, so the shortened simple name would bind to the member
            // (e.g. an enum constant Float breaking a shortened java.lang.Float.TYPE). Keep it global::.
            if (memberNames.contains(r.simple)) {
                continue;
            }
            if (declared.contains(r.simple) && !r.namespace.equals(fileNs)) {
                continue;
            }
            if (r.bcl && ownSimpleNames.contains(r.simple)) {
                continue;
            }
            candidates.add(r);
        }
        // Shortening a reference imports its namespace, which also brings every other type in that
        // namespace into scope. If a candidate's simple name is declared by a second namespace that
        // is likewise imported into this file, the bare name would be ambiguous — keep it global::.
        Set<String> usedNamespaces = new java.util.HashSet<>();
        usedNamespaces.add(fileNs);
        for (Ref r : candidates) {
            usedNamespaces.add(r.namespace);
        }
        Set<String> imports = new TreeSet<>();
        List<Ref> toStrip = new ArrayList<>();
        for (Ref r : candidates) {
            if (!r.bcl && ambiguousAcross(r.simple, usedNamespaces)) {
                continue;
            }
            toStrip.add(r);
            if (!r.namespace.equals(fileNs)) {
                imports.add(r.namespace);
            }
        }
        if (toStrip.isEmpty()) {
            return cs;
        }

        StringBuilder body = new StringBuilder(cs.length());
        int cursor = 0;
        for (Ref r : toStrip) {
            body.append(cs, cursor, r.start);
            cursor = r.simpleStart;
        }
        body.append(cs, cursor, cs.length());

        if (imports.isEmpty()) {
            return body.toString();
        }
        return injectUsings(body.toString(), fileNs, imports);
    }

    /** Whether {@code simple} is declared by two or more of the given namespaces. */
    private boolean ambiguousAcross(String simple, Set<String> namespaces) {
        int count = 0;
        for (String ns : namespaces) {
            Set<String> types = namespaceTypes.get(ns);
            if (types != null && types.contains(simple)) {
                if (++count >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String injectUsings(String cs, String fileNs, Set<String> imports) {
        int nsIdx = cs.indexOf("namespace " + fileNs);
        if (nsIdx < 0) {
            nsIdx = cs.indexOf("namespace ");
        }
        int brace = cs.indexOf('{', nsIdx);
        if (brace < 0) {
            return cs;
        }
        int lineEnd = cs.indexOf('\n', brace);
        int insertAt = lineEnd < 0 ? brace + 1 : lineEnd + 1;
        StringBuilder block = new StringBuilder();
        for (String ns : imports) {
            block.append("    using ").append(ns).append(";\n");
        }
        block.append('\n');
        return cs.substring(0, insertAt) + block + cs.substring(insertAt);
    }

    /** One shortenable global:: reference. */
    private static final class Ref {
        final int start;        // index of the 'g' in global::
        final int simpleStart;  // index where the simple type name begins
        final String namespace;
        final String simple;
        final boolean bcl;

        Ref(int start, int simpleStart, String namespace, String simple, boolean bcl) {
            this.start = start;
            this.simpleStart = simpleStart;
            this.namespace = namespace;
            this.simple = simple;
            this.bcl = bcl;
        }

        String fqn() {
            return namespace + "." + simple;
        }
    }

    private List<Ref> scan(String cs) {
        List<Ref> refs = new ArrayList<>();
        int n = cs.length();
        int i = 0;
        while (i < n) {
            char c = cs.charAt(i);
            // Comments.
            if (c == '/' && i + 1 < n && cs.charAt(i + 1) == '/') {
                i = endOfLine(cs, i);
                continue;
            }
            if (c == '/' && i + 1 < n && cs.charAt(i + 1) == '*') {
                i = cs.indexOf("*/", i + 2);
                i = i < 0 ? n : i + 2;
                continue;
            }
            // Literals.
            if (c == '"') {
                i = endOfString(cs, i, false);
                continue;
            }
            if (c == '@' && i + 1 < n && cs.charAt(i + 1) == '"') {
                i = endOfString(cs, i + 1, true);
                continue;
            }
            if (c == '$' && i + 1 < n && cs.charAt(i + 1) == '"') {
                i = endOfString(cs, i + 1, false);
                continue;
            }
            if (c == '\'') {
                i = endOfChar(cs, i);
                continue;
            }
            // global:: reference.
            if (c == 'g' && cs.startsWith(GLOBAL, i)) {
                int after = i + GLOBAL.length();
                int[] end = new int[1];
                List<int[]> segs = readSegments(cs, after, end);
                if (segs.size() >= 2) {
                    Ref r = classify(cs, i, segs);
                    if (r != null) {
                        refs.add(r);
                    }
                }
                i = end[0];
                continue;
            }
            i++;
        }
        return refs;
    }

    /** Reads dotted identifier segments starting at p; end[0] is set past the last segment. */
    private static List<int[]> readSegments(String cs, int p, int[] end) {
        List<int[]> segs = new ArrayList<>();
        int n = cs.length();
        while (p < n && isIdentStart(cs.charAt(p))) {
            int s = p;
            p++;
            while (p < n && isIdentPart(cs.charAt(p))) {
                p++;
            }
            segs.add(new int[]{s, p});
            if (p + 1 < n && cs.charAt(p) == '.' && isIdentStart(cs.charAt(p + 1))) {
                p++;
            } else {
                break;
            }
        }
        end[0] = p;
        return segs;
    }

    private Ref classify(String cs, int start, List<int[]> segs) {
        String root = seg(cs, segs.get(0));
        boolean lowerRoot = !root.isEmpty()
                && (Character.isLowerCase(root.charAt(0)) || (root.charAt(0) == '@' && root.length() > 1));
        if (lowerRoot) {
            int nsCount = 0;
            while (nsCount < segs.size() && isNamespaceSegment(seg(cs, segs.get(nsCount)))) {
                nsCount++;
            }
            if (nsCount == 0 || nsCount >= segs.size()) {
                return null; // no type segment found
            }
            String namespace = joinSegs(cs, segs, 0, nsCount);
            int[] typeSpan = segs.get(nsCount);
            return new Ref(start, typeSpan[0], namespace, seg(cs, typeSpan), false);
        }
        // BCL: longest allowlisted namespace prefix.
        for (int k = segs.size() - 1; k >= 1; k--) {
            String candidate = joinSegs(cs, segs, 0, k);
            if (BCL_NAMESPACES.contains(candidate)) {
                int[] typeSpan = segs.get(k);
                return new Ref(start, typeSpan[0], candidate, seg(cs, typeSpan), true);
            }
        }
        return null;
    }

    private static boolean isNamespaceSegment(String s) {
        int i = 0;
        if (i < s.length() && s.charAt(i) == '@') {
            i++;
        }
        if (i >= s.length() || !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
            return false;
        }
        for (i++; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9'))) {
                return false;
            }
        }
        return true;
    }

    private static String seg(String cs, int[] span) {
        return cs.substring(span[0], span[1]);
    }

    private static String joinSegs(String cs, List<int[]> segs, int from, int to) {
        StringBuilder sb = new StringBuilder();
        for (int k = from; k < to; k++) {
            if (k > from) {
                sb.append('.');
            }
            sb.append(seg(cs, segs.get(k)));
        }
        return sb.toString();
    }

    private static boolean isIdentStart(char c) {
        return c == '_' || c == '@' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isIdentPart(char c) {
        return c == '_' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    private static int endOfLine(String cs, int i) {
        int nl = cs.indexOf('\n', i);
        return nl < 0 ? cs.length() : nl;
    }

    private static int endOfString(String cs, int quote, boolean verbatim) {
        int n = cs.length();
        int i = quote + 1;
        while (i < n) {
            char c = cs.charAt(i);
            if (verbatim) {
                if (c == '"') {
                    if (i + 1 < n && cs.charAt(i + 1) == '"') {
                        i += 2;
                        continue;
                    }
                    return i + 1;
                }
                i++;
            } else {
                if (c == '\\') {
                    i += 2;
                    continue;
                }
                if (c == '"') {
                    return i + 1;
                }
                i++;
            }
        }
        return n;
    }

    private static int endOfChar(String cs, int i) {
        int n = cs.length();
        int p = i + 1;
        while (p < n) {
            char c = cs.charAt(p);
            if (c == '\\') {
                p += 2;
                continue;
            }
            if (c == '\'') {
                return p + 1;
            }
            p++;
        }
        return n;
    }
}
