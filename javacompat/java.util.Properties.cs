namespace java.util
{
    public class Properties : HashMap
    {
        public Properties(global::java.lang.RawNew r) : base(r) { }

        public global::java.lang.Object setProperty(global::java.lang.String key, global::java.lang.String value) { return put(key, value); }

        public global::java.lang.String getProperty(global::java.lang.String key)
        {
            return get(key) as global::java.lang.String;
        }

        public global::java.lang.String getProperty(global::java.lang.String key, global::java.lang.String defaultValue)
        {
            var v = get(key);
            return v != null ? (global::java.lang.String)v : defaultValue;
        }

        public Set stringPropertyNames() { return keySet(); }

        public void load(global::java.io.InputStream inStream)
        {
            var bytes = new global::System.Collections.Generic.List<byte>();
            int b;
            while ((b = inStream.read()) != -1)
            {
                bytes.Add((byte)b);
            }
            // .properties are ISO-8859-1 by spec. Parse the common subset: skip blank and #/! comment
            // lines and split each entry on its first '=' or ':' (or run of whitespace), trimming both
            // sides. A trailing backslash continues the logical line onto the next physical one.
            string text = global::System.Text.Encoding.GetEncoding("ISO-8859-1").GetString(bytes.ToArray());
            string[] physical = text.Replace("\r\n", "\n").Replace('\r', '\n').Split('\n');
            for (int i = 0; i < physical.Length; i++)
            {
                string line = physical[i];
                int lead = 0;
                while (lead < line.Length && (line[lead] == ' ' || line[lead] == '\t' || line[lead] == '\f'))
                {
                    lead++;
                }
                line = line.Substring(lead);
                if (line.Length == 0 || line[0] == '#' || line[0] == '!')
                {
                    continue;
                }
                while (EndsWithOddBackslash(line) && i + 1 < physical.Length)
                {
                    line = line.Substring(0, line.Length - 1) + physical[++i].TrimStart();
                }
                int sep = SeparatorIndex(line);
                string key = sep < 0 ? line : line.Substring(0, sep);
                string val = sep < 0 ? "" : line.Substring(sep + 1);
                put(global::java.lang.String.Wrap(key.Trim()), global::java.lang.String.Wrap(val.Trim()));
            }
        }

        private static bool EndsWithOddBackslash(string s)
        {
            int n = 0;
            int i = s.Length - 1;
            while (i >= 0 && s[i] == '\\')
            {
                n++;
                i--;
            }
            return (n % 2) == 1;
        }

        private static int SeparatorIndex(string line)
        {
            for (int i = 0; i < line.Length; i++)
            {
                char c = line[i];
                if (c == '\\')
                {
                    i++;
                    continue;
                }
                if (c == '=' || c == ':')
                {
                    return i;
                }
            }
            return -1;
        }
    }
}
