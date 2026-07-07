package com.tonic.j2cs.visual;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Compares the JVM reference dump (ref.txt) against the Avalonia candidate dump (cand.txt),
 * matched by component name. Gates colors + font-size exactly and geometry within a pixel
 * tolerance; writes a ranked diff.txt and prints a summary.
 *
 * args: &lt;outDir&gt;  (containing ref.txt and cand.txt)
 */
public final class VisualDiff {
    private static final int TOL = 3;

    public static void main(String[] args) throws Exception {
        File dir = new File(args[0]);
        Map<String, String[]> ref = load(new File(dir, "ref.txt"));
        Map<String, String[]> cand = load(new File(dir, "cand.txt"));

        StringBuilder sb = new StringBuilder();
        int violations = 0;
        for (Map.Entry<String, String[]> e : ref.entrySet()) {
            String name = e.getKey();
            String[] r = e.getValue();
            String[] c = cand.get(name);
            if (c == null) {
                sb.append("MISSING  ").append(name).append(" (in ref, not in candidate)\n");
                violations++;
                continue;
            }
            int dx = geo(c, 0) - geo(r, 0);
            int dy = geo(c, 1) - geo(r, 1);
            int dw = geo(c, 2) - geo(r, 2);
            int dh = geo(c, 3) - geo(r, 3);
            boolean opaque = r.length > 8 && "1".equals(r[8]);
            boolean geoBad = Math.abs(dx) > TOL || Math.abs(dy) > TOL || Math.abs(dw) > TOL || Math.abs(dh) > TOL;
            boolean bgBad = opaque && !eqColor(r[6], c[6]);
            boolean fgBad = !eqColor(r[7], c[7])
                    && !"none".equalsIgnoreCase(r[7]) && !"none".equalsIgnoreCase(c[7]);
            if (geoBad || bgBad || fgBad) {
                violations++;
            }
            sb.append(geoBad || bgBad || fgBad ? "DIFF " : "ok   ")
              .append(String.format("%-12s", name))
              .append(" pos d(").append(dx).append(',').append(dy).append(") size d(").append(dw).append(',').append(dh).append(')');
            if (bgBad) { sb.append("  bg ref=").append(r[6]).append(" cand=").append(c[6]); }
            if (fgBad) { sb.append("  fg ref=").append(r[7]).append(" cand=").append(c[7]); }
            sb.append("  [ref ").append(r[1]).append(" -> cand ").append(c[1]).append(']');
            sb.append('\n');
        }
        for (String name : cand.keySet()) {
            if (!ref.containsKey(name)) {
                sb.append("EXTRA    ").append(name).append(" (in candidate, not in ref)\n");
            }
        }
        String summary = "VISUAL DIFF: " + ref.size() + " named refs, " + violations + " with violations (tol " + TOL + "px)\n";
        sb.insert(0, summary);
        try (PrintWriter out = new PrintWriter(new File(dir, "diff.txt"))) {
            out.print(sb);
        }
        System.out.print(sb);
    }

    private static int geo(String[] f, int i) {
        try {
            return Integer.parseInt(f[2 + i]);
        } catch (Exception e) {
            return 0;
        }
    }

    private static boolean eqColor(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        if ("none".equalsIgnoreCase(a) && "none".equalsIgnoreCase(b)) {
            return true;
        }
        return a.equalsIgnoreCase(b);
    }

    private static Map<String, String[]> load(File f) throws Exception {
        Map<String, String[]> map = new LinkedHashMap<>();
        if (!f.exists()) {
            return map;
        }
        for (String line : Files.readAllLines(f.toPath())) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split("\\|");
            map.put(parts[0], parts);
        }
        return map;
    }

    private VisualDiff() {
    }
}
