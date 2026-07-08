namespace java.io
{
    public class ByteArrayOutputStream : OutputStream
    {
        private readonly global::System.Collections.Generic.List<sbyte> buf =
                new global::System.Collections.Generic.List<sbyte>();

        public ByteArrayOutputStream(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int size)
        {
        }

        public override void write(int b)
        {
            buf.Add((sbyte)b);
        }

        public override void write(sbyte[] b, int off, int len)
        {
            for (int i = 0; i < len; i++)
            {
                buf.Add(b[off + i]);
            }
        }

        public sbyte[] toByteArray()
        {
            return buf.ToArray();
        }

        public int size()
        {
            return buf.Count;
        }

        public void reset()
        {
            buf.Clear();
        }

        public void writeTo(OutputStream outStream)
        {
            outStream.write(buf.ToArray());
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(
                    new global::System.Text.UTF8Encoding(false).GetString(
                            global::java.lang.JRuntime.UnsignedBytes(buf.ToArray())));
        }
    }
}
