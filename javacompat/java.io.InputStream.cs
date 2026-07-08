namespace java.io
{
    public class InputStream : global::java.lang.Object
    {
        public InputStream(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual int read()
        {
            return -1;
        }

        public virtual int read(sbyte[] b)
        {
            return read(b, 0, b.Length);
        }

        public virtual int read(sbyte[] b, int off, int len)
        {
            if (len == 0)
            {
                return 0;
            }
            int first = read();
            if (first < 0)
            {
                return -1;
            }
            b[off] = (sbyte)first;
            int i = 1;
            for (; i < len; i++)
            {
                int c = read();
                if (c < 0)
                {
                    break;
                }
                b[off + i] = (sbyte)c;
            }
            return i;
        }

        public virtual int available()
        {
            return 0;
        }

        public virtual long skip(long n)
        {
            long s = 0;
            while (s < n && read() >= 0)
            {
                s++;
            }
            return s;
        }

        public virtual sbyte[] readAllBytes()
        {
            var acc = new global::System.Collections.Generic.List<sbyte>();
            int c;
            while ((c = read()) >= 0)
            {
                acc.Add((sbyte)c);
            }
            return acc.ToArray();
        }

        public virtual void close()
        {
        }
    }
}
