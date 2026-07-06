namespace java.io
{
    public class PrintStream : global::java.lang.Object
    {
        private readonly global::System.IO.TextWriter writer;

        public PrintStream(global::System.IO.TextWriter writer) : base(global::java.lang.RawNew.I)
        {
            this.writer = writer;
        }

        public void println()
        {
            Newline();
        }

        public void println(int v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            Newline();
        }

        public void println(long v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            Newline();
        }

        public void println(float v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            Newline();
        }

        public void println(double v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            Newline();
        }

        public void println(char v)
        {
            writer.Write(v);
            Newline();
        }

        public void println_Z(int v)
        {
            writer.Write(global::java.lang.JRuntime.StrZ(v));
            Newline();
        }

        public void println(global::java.lang.String v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            Newline();
        }

        public void println(global::java.lang.Object v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            Newline();
        }

        public void print(int v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            writer.Flush();
        }

        public void print(long v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            writer.Flush();
        }

        public void print(float v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            writer.Flush();
        }

        public void print(double v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            writer.Flush();
        }

        public void print(char v)
        {
            writer.Write(v);
            writer.Flush();
        }

        public void print_Z(int v)
        {
            writer.Write(global::java.lang.JRuntime.StrZ(v));
            writer.Flush();
        }

        public void print(global::java.lang.String v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            writer.Flush();
        }

        public void print(global::java.lang.Object v)
        {
            writer.Write(global::java.lang.JRuntime.Str(v));
            writer.Flush();
        }

        public PrintStream printf(global::java.lang.String fmt, global::java.lang.Object[] args)
        {
            writer.Write(global::java.lang.JRuntime.Format(fmt == null ? null : fmt.Value, args));
            writer.Flush();
            return this;
        }

        public PrintStream format(global::java.lang.String fmt, global::java.lang.Object[] args)
        {
            return printf(fmt, args);
        }

        private void Newline()
        {
            writer.Write('\n');
            writer.Flush();
        }
    }
}
