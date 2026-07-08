namespace java.io
{
    public class FileWriter : Writer
    {
        private static readonly global::System.Text.Encoding Utf8NoBom = new global::System.Text.UTF8Encoding(false);

        private string path;
        private bool append;
        private bool opened;
        private readonly global::System.Text.StringBuilder sb = new global::System.Text.StringBuilder();

        public FileWriter(global::java.lang.RawNew r) : base(r)
        {
            path = "";
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String name)
        {
            path = name.Value;
            append = false;
        }

        public void __init_Ljava_lang_String_Z_V(global::java.lang.String name, int ap)
        {
            path = name.Value;
            append = ap != 0;
        }

        public void __init_Ljava_io_File__V(File file)
        {
            path = file.path;
            append = false;
        }

        public void __init_Ljava_io_File_Z_V(File file, int ap)
        {
            path = file.path;
            append = ap != 0;
        }

        public override void write(int c)
        {
            sb.Append((char)c);
        }

        public override void write(char[] cbuf, int off, int len)
        {
            sb.Append(cbuf, off, len);
        }

        public override void write(global::java.lang.String str)
        {
            if (str != null)
            {
                sb.Append(str.Value);
            }
        }

        public override void flush()
        {
            if (sb.Length > 0)
            {
                string text = sb.ToString();
                sb.Clear();
                if (append || opened)
                {
                    global::System.IO.File.AppendAllText(path, text, Utf8NoBom);
                }
                else
                {
                    global::System.IO.File.WriteAllText(path, text, Utf8NoBom);
                }
                opened = true;
            }
        }

        public override void close()
        {
            flush();
        }
    }
}
