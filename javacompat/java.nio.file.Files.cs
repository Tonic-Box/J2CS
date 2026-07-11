namespace java.nio.file
{
    public sealed class Files : global::java.lang.Object
    {
        private static readonly global::System.Text.Encoding Utf8NoBom = new global::System.Text.UTF8Encoding(false);

        public Files(global::java.lang.RawNew r) : base(r)
        {
        }

        private static bool HasReplace(global::java.nio.file.CopyOption[] options)
        {
            if (options != null)
            {
                foreach (var o in options)
                {
                    if (ReferenceEquals(o, StandardCopyOption.REPLACE_EXISTING))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        private static bool HasAppend(global::java.nio.file.OpenOption[] options)
        {
            if (options != null)
            {
                foreach (var o in options)
                {
                    if (ReferenceEquals(o, StandardOpenOption.APPEND))
                    {
                        return true;
                    }
                }
            }
            return false;
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
            string text = global::java.lang.JRuntime.Str((global::java.lang.Object)csq);
            if (HasAppend(options)) { global::System.IO.File.AppendAllText(path.value, text, Utf8NoBom); }
            else { global::System.IO.File.WriteAllText(path.value, text, Utf8NoBom); }
            return path;
        }

        public static Path writeString(Path path, global::java.lang.CharSequence csq,
                global::java.nio.charset.Charset cs, global::java.nio.file.OpenOption[] options)
        {
            var enc = cs == null ? Utf8NoBom : cs.encoding;
            string text = global::java.lang.JRuntime.Str((global::java.lang.Object)csq);
            if (HasAppend(options)) { global::System.IO.File.AppendAllText(path.value, text, enc); }
            else { global::System.IO.File.WriteAllText(path.value, text, enc); }
            return path;
        }

        public static sbyte[] readAllBytes(Path path)
        {
            return global::java.lang.JRuntime.SignedBytes(global::System.IO.File.ReadAllBytes(path.value));
        }

        public static Path write(Path path, sbyte[] bytes, global::java.nio.file.OpenOption[] options)
        {
            var ub = global::java.lang.JRuntime.UnsignedBytes(bytes);
            if (HasAppend(options))
            {
                using (var fs = new global::System.IO.FileStream(path.value, global::System.IO.FileMode.Append))
                {
                    fs.Write(ub, 0, ub.Length);
                }
            }
            else
            {
                global::System.IO.File.WriteAllBytes(path.value, ub);
            }
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

        public static int isReadable(Path path)
        {
            return global::System.IO.File.Exists(path.value) ? 1 : 0;
        }

        public static int isSameFile(Path a, Path b)
        {
            return global::System.IO.Path.GetFullPath(a.value) == global::System.IO.Path.GetFullPath(b.value) ? 1 : 0;
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

        public static Path copy(Path source, Path target, global::java.nio.file.CopyOption[] options)
        {
            if (global::System.IO.Directory.Exists(source.value))
            {
                global::System.IO.Directory.CreateDirectory(target.value);
            }
            else
            {
                global::System.IO.File.Copy(source.value, target.value, HasReplace(options));
            }
            return target;
        }

        public static Path move(Path source, Path target, global::java.nio.file.CopyOption[] options)
        {
            if (HasReplace(options))
            {
                if (global::System.IO.File.Exists(target.value)) { global::System.IO.File.Delete(target.value); }
                else if (global::System.IO.Directory.Exists(target.value)) { global::System.IO.Directory.Delete(target.value, true); }
            }
            if (global::System.IO.Directory.Exists(source.value))
            {
                global::System.IO.Directory.Move(source.value, target.value);
            }
            else
            {
                global::System.IO.File.Move(source.value, target.value);
            }
            return target;
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

        public static global::java.util.stream.Stream walk(Path start, global::java.nio.file.FileVisitOption[] options)
        {
            return walk(start, int.MaxValue, options);
        }

        public static global::java.util.stream.Stream walk(Path start, int maxDepth,
                global::java.nio.file.FileVisitOption[] options)
        {
            var items = new global::System.Collections.Generic.List<global::java.lang.Object>();
            WalkInto(start.value, 0, maxDepth, items);
            return global::java.util.stream.Stream.Wrap(items);
        }

        private static void WalkInto(string path, int depth, int maxDepth,
                global::System.Collections.Generic.List<global::java.lang.Object> acc)
        {
            acc.Add(new Path(path));
            if (depth >= maxDepth || !global::System.IO.Directory.Exists(path))
            {
                return;
            }
            foreach (var entry in global::System.IO.Directory.GetFileSystemEntries(path))
            {
                WalkInto(entry, depth + 1, maxDepth, acc);
            }
        }

        public static global::java.io.BufferedReader newBufferedReader(Path path)
        {
            var fr = new global::java.io.FileReader(global::java.lang.RawNew.I);
            fr.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(path.value));
            var br = new global::java.io.BufferedReader(global::java.lang.RawNew.I);
            br.__init_Ljava_io_Reader__V(fr);
            return br;
        }

        public static global::java.io.BufferedReader newBufferedReader(Path path, global::java.nio.charset.Charset cs)
        {
            var fis = new global::java.io.FileInputStream(global::java.lang.RawNew.I);
            fis.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(path.value));
            var isr = new global::java.io.InputStreamReader(global::java.lang.RawNew.I);
            isr.__init_Ljava_io_InputStream_Ljava_nio_charset_Charset__V(fis, cs);
            var br = new global::java.io.BufferedReader(global::java.lang.RawNew.I);
            br.__init_Ljava_io_Reader__V(isr);
            return br;
        }

        public static global::java.io.BufferedWriter newBufferedWriter(Path path,
                global::java.nio.file.OpenOption[] options)
        {
            var fw = new global::java.io.FileWriter(global::java.lang.RawNew.I);
            if (HasAppend(options))
            {
                fw.__init_Ljava_lang_String_Z_V(global::java.lang.String.Wrap(path.value), 1);
            }
            else
            {
                fw.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(path.value));
            }
            var bw = new global::java.io.BufferedWriter(global::java.lang.RawNew.I);
            bw.__init_Ljava_io_Writer__V(fw);
            return bw;
        }

        public static global::java.io.BufferedWriter newBufferedWriter(Path path,
                global::java.nio.charset.Charset cs, global::java.nio.file.OpenOption[] options)
        {
            var fos = new global::java.io.FileOutputStream(global::java.lang.RawNew.I);
            fos.__init_Ljava_lang_String_Z_V(global::java.lang.String.Wrap(path.value), HasAppend(options) ? 1 : 0);
            var osw = new global::java.io.OutputStreamWriter(global::java.lang.RawNew.I);
            osw.__init_Ljava_io_OutputStream_Ljava_nio_charset_Charset__V(fos, cs);
            var bw = new global::java.io.BufferedWriter(global::java.lang.RawNew.I);
            bw.__init_Ljava_io_Writer__V(osw);
            return bw;
        }

        public static global::java.io.InputStream newInputStream(Path path, global::java.nio.file.OpenOption[] options)
        {
            var fis = new global::java.io.FileInputStream(global::java.lang.RawNew.I);
            fis.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(path.value));
            return fis;
        }

        public static global::java.io.OutputStream newOutputStream(Path path, global::java.nio.file.OpenOption[] options)
        {
            var fos = new global::java.io.FileOutputStream(global::java.lang.RawNew.I);
            fos.__init_Ljava_lang_String_Z_V(global::java.lang.String.Wrap(path.value), HasAppend(options) ? 1 : 0);
            return fos;
        }

        public static global::java.util.stream.Stream find(Path start, int maxDepth,
                global::java.util.function.BiPredicate matcher, global::java.nio.file.FileVisitOption[] options)
        {
            var items = new global::System.Collections.Generic.List<global::java.lang.Object>();
            FindInto(start.value, 0, maxDepth, matcher, items);
            return global::java.util.stream.Stream.Wrap(items);
        }

        private static void FindInto(string path, int depth, int maxDepth,
                global::java.util.function.BiPredicate matcher,
                global::System.Collections.Generic.List<global::java.lang.Object> acc)
        {
            var attrs = new global::java.nio.file.attribute.BasicFileAttributes(path);
            if (matcher.test(new Path(path), attrs) != 0)
            {
                acc.Add(new Path(path));
            }
            if (depth >= maxDepth || !global::System.IO.Directory.Exists(path))
            {
                return;
            }
            foreach (var entry in global::System.IO.Directory.GetFileSystemEntries(path))
            {
                FindInto(entry, depth + 1, maxDepth, matcher, acc);
            }
        }

        public static global::java.nio.file.attribute.BasicFileAttributes readAttributes(Path path,
                global::java.lang.Class type, global::java.nio.file.LinkOption[] options)
        {
            return new global::java.nio.file.attribute.BasicFileAttributes(path.value);
        }
    }
}
