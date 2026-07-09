namespace java.io
{
    public class Writer : global::java.lang.Object, Closeable
    {
        public Writer(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual void write(int c)
        {
            write(new char[] { (char)c }, 0, 1);
        }

        public virtual void write(char[] cbuf)
        {
            write(cbuf, 0, cbuf.Length);
        }

        public virtual void write(char[] cbuf, int off, int len)
        {
        }

        public virtual void write(global::java.lang.String str)
        {
            if (str == null)
            {
                return;
            }
            char[] cs = str.Value.ToCharArray();
            write(cs, 0, cs.Length);
        }

        public virtual Writer append(global::java.lang.CharSequence csq)
        {
            write(global::java.lang.String.Wrap(global::java.lang.JRuntime.Str((global::java.lang.Object)csq)));
            return this;
        }

        public virtual Writer append(char c)
        {
            write(c);
            return this;
        }

        public virtual void flush()
        {
        }

        public virtual void close()
        {
        }
    }
}
