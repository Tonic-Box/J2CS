namespace java.io
{
    public class Reader : global::java.lang.Object
    {
        public Reader(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual int read()
        {
            char[] one = new char[1];
            int n = read(one, 0, 1);
            return n < 0 ? -1 : one[0];
        }

        public virtual int read(char[] cbuf)
        {
            return read(cbuf, 0, cbuf.Length);
        }

        public virtual int read(char[] cbuf, int off, int len)
        {
            return -1;
        }

        public virtual void close()
        {
        }
    }
}
