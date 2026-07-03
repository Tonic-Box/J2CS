package com.tonic.j2cs.emit;

/**
 * Indentation-aware text sink for generated C#.
 */
public final class CsWriter {

    private static final String INDENT = "    ";

    private final StringBuilder sb = new StringBuilder();
    private int depth;

    public CsWriter() {
        this(0);
    }

    public CsWriter(int initialDepth) {
        this.depth = initialDepth;
    }

    public CsWriter raw(String text) {
        sb.append(text);
        return this;
    }

    public CsWriter line(String text) {
        if (!text.isEmpty()) {
            sb.append(INDENT.repeat(depth)).append(text);
        }
        sb.append('\n');
        return this;
    }

    public CsWriter line() {
        sb.append('\n');
        return this;
    }

    public CsWriter open(String header) {
        line(header);
        line("{");
        depth++;
        return this;
    }

    public CsWriter close() {
        depth--;
        line("}");
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
