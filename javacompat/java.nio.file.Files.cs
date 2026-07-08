namespace java.nio.file
{
    public sealed class Files : global::java.lang.Object
    {
        private static readonly global::System.Text.Encoding Utf8NoBom = new global::System.Text.UTF8Encoding(false);

        public Files(global::java.lang.RawNew r) : base(r)
        {
        }

        public static global::java.lang.String readString(Path path)
        {
            return global::java.lang.String.Wrap(global::System.IO.File.ReadAllText(path.value, Utf8NoBom));
        }

        public static global::java.lang.String readString(Path path, global::java.nio.charset.Charset cs)
        {
            var enc = cs == null ? Utf8NoBom : cs.encoding;
            return global::java.lang.String.Wrap(global::System.IO.File.ReadAllText(path.value, enc));
        }

        public static Path writeString(Path path, global::java.lang.CharSequence csq,
                global::java.nio.file.OpenOption[] options)
        {
            global::System.IO.File.WriteAllText(path.value, global::java.lang.JRuntime.Str((global::java.lang.Object)csq), Utf8NoBom);
            return path;
        }

        public static Path writeString(Path path, global::java.lang.CharSequence csq,
                global::java.nio.charset.Charset cs, global::java.nio.file.OpenOption[] options)
        {
            var enc = cs == null ? Utf8NoBom : cs.encoding;
            global::System.IO.File.WriteAllText(path.value, global::java.lang.JRuntime.Str((global::java.lang.Object)csq), enc);
            return path;
        }

        public static sbyte[] readAllBytes(Path path)
        {
            return global::java.lang.JRuntime.SignedBytes(global::System.IO.File.ReadAllBytes(path.value));
        }

        public static Path write(Path path, sbyte[] bytes, global::java.nio.file.OpenOption[] options)
        {
            global::System.IO.File.WriteAllBytes(path.value, global::java.lang.JRuntime.UnsignedBytes(bytes));
            return path;
        }

        public static global::java.util.List readAllLines(Path path)
        {
            var list = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            list.__init__V();
            foreach (var line in global::System.IO.File.ReadAllLines(path.value, Utf8NoBom))
            {
                list.add(global::java.lang.String.Wrap(line));
            }
            return list;
        }

        public static int exists(Path path, global::java.nio.file.LinkOption[] options)
        {
            return global::System.IO.File.Exists(path.value) || global::System.IO.Directory.Exists(path.value) ? 1 : 0;
        }

        public static int notExists(Path path, global::java.nio.file.LinkOption[] options)
        {
            return global::System.IO.File.Exists(path.value) || global::System.IO.Directory.Exists(path.value) ? 0 : 1;
        }

        public static int isDirectory(Path path, global::java.nio.file.LinkOption[] options)
        {
            return global::System.IO.Directory.Exists(path.value) ? 1 : 0;
        }

        public static int isRegularFile(Path path, global::java.nio.file.LinkOption[] options)
        {
            return global::System.IO.File.Exists(path.value) ? 1 : 0;
        }

        public static Path createDirectories(Path path, global::java.nio.file.attribute.FileAttribute[] attrs)
        {
            global::System.IO.Directory.CreateDirectory(path.value);
            return path;
        }

        public static Path createDirectory(Path path, global::java.nio.file.attribute.FileAttribute[] attrs)
        {
            global::System.IO.Directory.CreateDirectory(path.value);
            return path;
        }

        public static Path createFile(Path path, global::java.nio.file.attribute.FileAttribute[] attrs)
        {
            using (global::System.IO.File.Create(path.value))
            {
            }
            return path;
        }

        public static void delete(Path path)
        {
            if (global::System.IO.Directory.Exists(path.value))
            {
                global::System.IO.Directory.Delete(path.value);
            }
            else
            {
                global::System.IO.File.Delete(path.value);
            }
        }

        public static int deleteIfExists(Path path)
        {
            if (global::System.IO.Directory.Exists(path.value))
            {
                global::System.IO.Directory.Delete(path.value);
                return 1;
            }
            if (global::System.IO.File.Exists(path.value))
            {
                global::System.IO.File.Delete(path.value);
                return 1;
            }
            return 0;
        }

        public static long size(Path path)
        {
            return new global::System.IO.FileInfo(path.value).Length;
        }

        public static global::java.util.stream.Stream lines(Path path)
        {
            var items = new global::System.Collections.Generic.List<global::java.lang.Object>();
            foreach (var line in global::System.IO.File.ReadAllLines(path.value, Utf8NoBom))
            {
                items.Add(global::java.lang.String.Wrap(line));
            }
            return global::java.util.stream.Stream.Wrap(items);
        }

        public static global::java.util.stream.Stream list(Path path)
        {
            var items = new global::System.Collections.Generic.List<global::java.lang.Object>();
            foreach (var entry in global::System.IO.Directory.GetFileSystemEntries(path.value))
            {
                items.Add(new Path(entry));
            }
            return global::java.util.stream.Stream.Wrap(items);
        }
    }
}
