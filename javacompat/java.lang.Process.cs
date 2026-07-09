namespace java.lang
{
    internal sealed class NativeInputStream : global::java.io.InputStream
    {
        private readonly global::System.IO.Stream s;
        internal NativeInputStream(global::System.IO.Stream stream) : base(global::java.lang.RawNew.I) { s = stream; }

        public override int read() { return s.ReadByte(); }

        public override int read(sbyte[] b, int off, int len)
        {
            var tmp = new byte[len];
            int n = s.Read(tmp, 0, len);
            if (n <= 0) { return -1; }
            for (int i = 0; i < n; i++) { b[off + i] = (sbyte)tmp[i]; }
            return n;
        }

        public override void close() { s.Dispose(); }
    }

    internal sealed class NativeOutputStream : global::java.io.OutputStream
    {
        private readonly global::System.IO.Stream s;
        internal NativeOutputStream(global::System.IO.Stream stream) : base(global::java.lang.RawNew.I) { s = stream; }

        public override void write(int b) { s.WriteByte((byte)b); }

        public override void write(sbyte[] b, int off, int len)
        {
            var u = new byte[len];
            for (int i = 0; i < len; i++) { u[i] = (byte)b[off + i]; }
            s.Write(u, 0, len);
        }

        public override void flush() { s.Flush(); }
        public override void close() { s.Dispose(); }
    }

    public class Process : global::java.lang.Object
    {
        internal global::System.Diagnostics.Process proc;

        public Process(global::java.lang.RawNew r) : base(r) { }

        public global::java.io.InputStream getInputStream() { return new NativeInputStream(proc.StandardOutput.BaseStream); }
        public global::java.io.InputStream getErrorStream() { return new NativeInputStream(proc.StandardError.BaseStream); }
        public global::java.io.OutputStream getOutputStream() { return new NativeOutputStream(proc.StandardInput.BaseStream); }

        public int waitFor() { proc.WaitForExit(); return proc.ExitCode; }
        public int exitValue() { return proc.ExitCode; }
        public int isAlive() { return !proc.HasExited ? 1 : 0; }

        public void destroy()
        {
            try { if (!proc.HasExited) { proc.Kill(); } }
            catch (global::System.Exception) { }
        }
    }
}
