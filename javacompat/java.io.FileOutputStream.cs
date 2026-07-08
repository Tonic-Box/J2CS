namespace java.io
{
    public class FileOutputStream : OutputStream
    {
        private global::System.IO.FileStream fs;

        public FileOutputStream(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String name)
        {
            fs = global::System.IO.File.Create(name.Value);
        }

        public void __init_Ljava_lang_String_Z_V(global::java.lang.String name, int append)
        {
            fs = new global::System.IO.FileStream(name.Value,
                    append != 0 ? global::System.IO.FileMode.Append : global::System.IO.FileMode.Create);
        }

        public void __init_Ljava_io_File__V(File file)
        {
            fs = global::System.IO.File.Create(file.path);
        }

        public void __init_Ljava_io_File_Z_V(File file, int append)
        {
            fs = new global::System.IO.FileStream(file.path,
                    append != 0 ? global::System.IO.FileMode.Append : global::System.IO.FileMode.Create);
        }

        public override void write(int b)
        {
            fs.WriteByte((byte)b);
        }

        public override void write(sbyte[] b, int off, int len)
        {
            var u = new byte[len];
            for (int i = 0; i < len; i++)
            {
                u[i] = (byte)b[off + i];
            }
            fs.Write(u, 0, len);
        }

        public override void flush()
        {
            fs.Flush();
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
