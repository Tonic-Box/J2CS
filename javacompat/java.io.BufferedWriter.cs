namespace java.io
{
    public class BufferedWriter : Writer
    {
        private Writer outw;

        public BufferedWriter(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_io_Writer__V(Writer w)
        {
            outw = w;
        }

        public void __init_Ljava_io_Writer_I_V(Writer w, int size)
        {
            outw = w;
        }

        public override void write(int c)
        {
            outw.write(c);
        }

        public override void write(char[] cbuf, int off, int len)
        {
            outw.write(cbuf, off, len);
        }

        public override void write(global::java.lang.String str)
        {
            outw.write(str);
        }

        public void newLine()
        {
            foreach (char ch in global::System.Environment.NewLine)
            {
                outw.write(ch);
            }
        }

        public override void flush()
        {
            outw.flush();
        }

        public override void close()
        {
            outw.flush();
            outw.close();
        }
    }
}
