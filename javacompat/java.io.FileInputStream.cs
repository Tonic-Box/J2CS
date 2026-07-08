namespace java.io
{
    public class FileInputStream : InputStream
    {
        private global::System.IO.FileStream fs;

        public FileInputStream(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String name)
        {
            fs = global::System.IO.File.OpenRead(name.Value);
        }

        public void __init_Ljava_io_File__V(File file)
        {
            fs = global::System.IO.File.OpenRead(file.path);
        }

        public override int read()
        {
            return fs.ReadByte();
        }

        public override int read(sbyte[] b, int off, int len)
        {
            var tmp = new byte[len];
            int n = fs.Read(tmp, 0, len);
            if (n <= 0)
            {
                return -1;
            }
            for (int i = 0; i < n; i++)
            {
                b[off + i] = (sbyte)tmp[i];
            }
            return n;
        }

        public override int available()
        {
            return (int)(fs.Length - fs.Position);
        }

        public override void close()
        {
            if (fs != null)
            {
                fs.Dispose();
            }
        }
    }
}
