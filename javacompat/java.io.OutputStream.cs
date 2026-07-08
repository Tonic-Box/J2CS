namespace java.io
{
    public class OutputStream : global::java.lang.Object
    {
        public OutputStream(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual void write(int b)
        {
        }

        public virtual void write(sbyte[] b)
        {
            write(b, 0, b.Length);
        }

        public virtual void write(sbyte[] b, int off, int len)
        {
            for (int i = 0; i < len; i++)
            {
                write(b[off + i] & 0xFF);
            }
        }

        public virtual void flush()
        {
        }

        public virtual void close()
        {
        }
    }
}
