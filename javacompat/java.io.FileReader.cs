namespace java.io
{
    public class FileReader : Reader
    {
        private string content;
        private int pos;

        public FileReader(global::java.lang.RawNew r) : base(r)
        {
            content = "";
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String name)
        {
            content = global::System.IO.File.ReadAllText(name.Value, new global::System.Text.UTF8Encoding(false));
        }

        public void __init_Ljava_io_File__V(File file)
        {
            content = global::System.IO.File.ReadAllText(file.path, new global::System.Text.UTF8Encoding(false));
        }

        public override int read()
        {
            return pos < content.Length ? content[pos++] : -1;
        }

        public override int read(char[] cbuf, int off, int len)
        {
            if (pos >= content.Length)
            {
                return -1;
            }
            int n = global::System.Math.Min(len, content.Length - pos);
            for (int i = 0; i < n; i++)
            {
                cbuf[off + i] = content[pos++];
            }
            return n;
        }
    }
}
