namespace java.io
{
    public class ByteArrayInputStream : InputStream
    {
        private sbyte[] data;
        private int pos;
        private int count;

        public ByteArrayInputStream(global::java.lang.RawNew r) : base(r)
        {
            data = new sbyte[0];
        }

        public void __init__B_V(sbyte[] buf)
        {
            data = buf;
            pos = 0;
            count = buf.Length;
        }

        public void __init__BII_V(sbyte[] buf, int offset, int length)
        {
            data = buf;
            pos = offset;
            count = offset + length;
        }

        public override int read()
        {
            return pos < count ? data[pos++] & 0xFF : -1;
        }

        public override int read(sbyte[] b, int off, int len)
        {
            if (pos >= count)
            {
                return -1;
            }
            int avail = count - pos;
            int n = len < avail ? len : avail;
            for (int i = 0; i < n; i++)
            {
                b[off + i] = data[pos++];
            }
            return n;
        }

        public override int available()
        {
            return count - pos;
        }

        public override long skip(long n)
        {
            long avail = count - pos;
            long s = n < avail ? n : avail;
            if (s < 0)
            {
                s = 0;
            }
            pos += (int)s;
            return s;
        }
    }
}
