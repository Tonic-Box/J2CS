namespace java.io
{
    public class PrintWriter : Writer
    {
        private Writer w;

        public PrintWriter(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_io_Writer__V(Writer target)
        {
            w = target;
        }

        public void __init_Ljava_io_OutputStream__V(OutputStream os)
        {
            w = new OutputStreamWriter(os);
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String fileName)
        {
            var fw = new FileWriter(global::java.lang.RawNew.I);
            fw.__init_Ljava_lang_String__V(fileName);
            w = fw;
        }

        public void __init_Ljava_io_File__V(File file)
        {
            var fw = new FileWriter(global::java.lang.RawNew.I);
            fw.__init_Ljava_io_File__V(file);
            w = fw;
        }

        public override void write(int c) { w.write(c); }
        public override void write(char[] cbuf, int off, int len) { w.write(cbuf, off, len); }
        public override void write(global::java.lang.String str) { w.write(str); }

        public void print(global::java.lang.String s) { w.write(s == null ? global::java.lang.String.Wrap("null") : s); }
        public void print(global::java.lang.Object o) { w.write(global::java.lang.String.Wrap(global::java.lang.JRuntime.Str(o))); }
        public void print(int i) { w.write(global::java.lang.String.Wrap(i.ToString(global::System.Globalization.CultureInfo.InvariantCulture))); }
        public void print(long l) { w.write(global::java.lang.String.Wrap(l.ToString(global::System.Globalization.CultureInfo.InvariantCulture))); }
        public void print(char c) { w.write(c); }
        public void print(double d) { w.write(global::java.lang.Double.toString(d)); }
        public void print_Z(int b) { w.write(global::java.lang.String.Wrap(b != 0 ? "true" : "false")); }

        public void println() { NewLine(); }
        public void println(global::java.lang.String s) { print(s); NewLine(); }
        public void println(global::java.lang.Object o) { print(o); NewLine(); }
        public void println(int i) { print(i); NewLine(); }
        public void println(long l) { print(l); NewLine(); }
        public void println(char c) { print(c); NewLine(); }
        public void println(double d) { print(d); NewLine(); }
        public void println_Z(int b) { print_Z(b); NewLine(); }

        public PrintWriter printf(global::java.lang.String fmt, global::java.lang.Object[] args)
        {
            w.write(global::java.lang.String.format(fmt, args));
            return this;
        }

        public PrintWriter format(global::java.lang.String fmt, global::java.lang.Object[] args)
        {
            w.write(global::java.lang.String.format(fmt, args));
            return this;
        }

        private void NewLine()
        {
            foreach (char ch in global::System.Environment.NewLine)
            {
                w.write(ch);
            }
        }

        public override void flush() { w.flush(); }

        public override void close()
        {
            w.flush();
            w.close();
        }
    }
}
