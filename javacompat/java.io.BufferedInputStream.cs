namespace java.io
{
    // Thin buffering wrapper: delegates to the wrapped stream. The shim InputStream already reads
    // eagerly (readAllBytes), so no separate buffer is needed for correctness.
    public class BufferedInputStream : InputStream
    {
        private InputStream wrapped;

        public BufferedInputStream(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_io_InputStream__V(InputStream input)
        {
            wrapped = input;
        }

        public void __init_Ljava_io_InputStream_I_V(InputStream input, int size)
        {
            wrapped = input;
        }

        public override int read()
        {
            return wrapped != null ? wrapped.read() : -1;
        }

        public override int read(sbyte[] b, int off, int len)
        {
            return wrapped != null ? wrapped.read(b, off, len) : -1;
        }

        public override sbyte[] readAllBytes()
        {
            return wrapped != null ? wrapped.readAllBytes() : new sbyte[0];
        }

        public override int available()
        {
            return wrapped != null ? wrapped.available() : 0;
        }

        public override long skip(long n)
        {
            return wrapped != null ? wrapped.skip(n) : 0L;
        }

        public override void close()
        {
            if (wrapped != null)
            {
                wrapped.close();
            }
        }
    }
}
