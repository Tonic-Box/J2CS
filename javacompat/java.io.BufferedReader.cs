namespace java.io
{
    public class BufferedReader : Reader
    {
        private Reader inr;
        private int pending = -2;

        public BufferedReader(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_io_Reader__V(Reader r)
        {
            inr = r;
        }

        public void __init_Ljava_io_Reader_I_V(Reader r, int size)
        {
            inr = r;
        }

        private int Next()
        {
            if (pending != -2)
            {
                int p = pending;
                pending = -2;
                return p;
            }
            return inr.read();
        }

        public override int read()
        {
            return Next();
        }

        public override int read(char[] cbuf, int off, int len)
        {
            int i = 0;
            for (; i < len; i++)
            {
                int c = Next();
                if (c < 0)
                {
                    break;
                }
                cbuf[off + i] = (char)c;
            }
            return i == 0 && len > 0 ? -1 : i;
        }

        public global::java.lang.String readLine()
        {
            var sb = new global::System.Text.StringBuilder();
            bool any = false;
            while (true)
            {
                int c = Next();
                if (c < 0)
                {
                    break;
                }
                any = true;
                if (c == '\n')
                {
                    break;
                }
                if (c == '\r')
                {
                    int n = Next();
                    if (n != '\n' && n >= 0)
                    {
                        pending = n;
                    }
                    break;
                }
                sb.Append((char)c);
            }
            return any ? global::java.lang.String.Wrap(sb.ToString()) : null;
        }

        public global::java.util.stream.Stream lines()
        {
            var items = new global::System.Collections.Generic.List<global::java.lang.Object>();
            global::java.lang.String ln;
            while ((ln = readLine()) != null)
            {
                items.Add(ln);
            }
            return global::java.util.stream.Stream.Wrap(items);
        }

        public override void close()
        {
            if (inr != null)
            {
                inr.close();
            }
        }
    }
}
