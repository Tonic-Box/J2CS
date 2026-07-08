namespace java.nio.file
{
    public sealed class Path : global::java.lang.Object
    {
        internal readonly string value;

        public Path(global::java.lang.RawNew r) : base(r)
        {
            value = "";
        }

        internal Path(string v) : base(global::java.lang.RawNew.I)
        {
            value = v;
        }

        internal static string Normalize(string s)
        {
            return s.Replace('/', global::System.IO.Path.DirectorySeparatorChar);
        }

        private static readonly char[] Separators =
        {
            global::System.IO.Path.DirectorySeparatorChar,
            global::System.IO.Path.AltDirectorySeparatorChar,
            '/'
        };

        public Path getFileName()
        {
            string n = global::System.IO.Path.GetFileName(value.TrimEnd(Separators));
            return string.IsNullOrEmpty(n) ? null : new Path(n);
        }

        public Path getParent()
        {
            string d = global::System.IO.Path.GetDirectoryName(value.TrimEnd(Separators));
            return string.IsNullOrEmpty(d) ? null : new Path(d);
        }

        public int getNameCount()
        {
            return value.Split(Separators, global::System.StringSplitOptions.RemoveEmptyEntries).Length;
        }

        public Path getName(int index)
        {
            var parts = value.Split(Separators, global::System.StringSplitOptions.RemoveEmptyEntries);
            return new Path(parts[index]);
        }

        public Path resolve(Path other)
        {
            return new Path(global::System.IO.Path.Combine(value, other.value));
        }

        public Path resolve(global::java.lang.String other)
        {
            return new Path(global::System.IO.Path.Combine(value, Normalize(other == null ? "" : other.Value)));
        }

        public Path resolveSibling(global::java.lang.String other)
        {
            Path parent = getParent();
            string o = Normalize(other == null ? "" : other.Value);
            return parent == null ? new Path(o) : new Path(global::System.IO.Path.Combine(parent.value, o));
        }

        public Path toAbsolutePath()
        {
            return new Path(global::System.IO.Path.GetFullPath(value));
        }

        public Path normalize()
        {
            var parts = value.Split(Separators, global::System.StringSplitOptions.RemoveEmptyEntries);
            var stack = new global::System.Collections.Generic.List<string>();
            foreach (var part in parts)
            {
                if (part == ".")
                {
                    continue;
                }
                if (part == ".." && stack.Count > 0 && stack[stack.Count - 1] != "..")
                {
                    stack.RemoveAt(stack.Count - 1);
                }
                else
                {
                    stack.Add(part);
                }
            }
            bool rooted = value.Length > 0 && (value[0] == '/' || value[0] == global::System.IO.Path.DirectorySeparatorChar);
            string joined = string.Join(global::System.IO.Path.DirectorySeparatorChar.ToString(), stack);
            return new Path(rooted ? global::System.IO.Path.DirectorySeparatorChar + joined : joined);
        }

        public int startsWith(Path other)
        {
            return value.StartsWith(other.value, global::System.StringComparison.Ordinal) ? 1 : 0;
        }

        public int endsWith(Path other)
        {
            return value.EndsWith(other.value, global::System.StringComparison.Ordinal) ? 1 : 0;
        }

        public bool IsAbsolute()
        {
            return global::System.IO.Path.IsPathRooted(value);
        }

        public int isAbsolute()
        {
            return IsAbsolute() ? 1 : 0;
        }

        public global::java.io.File toFile()
        {
            return new global::java.io.File(value);
        }

        public global::java.util.Iterator iterator()
        {
            var list = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            list.__init__V();
            foreach (var part in value.Split(Separators, global::System.StringSplitOptions.RemoveEmptyEntries))
            {
                list.add(new Path(part));
            }
            return list.iterator();
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Path p && p.value == value ? 1 : 0;
        }

        public override int hashCode()
        {
            return value.GetHashCode();
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(value);
        }
    }
}
