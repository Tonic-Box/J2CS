namespace java.io
{
    public sealed class File : global::java.lang.Object
    {
        public static readonly global::java.lang.String separator =
                global::java.lang.String.Wrap(global::System.IO.Path.DirectorySeparatorChar.ToString());
        public static readonly char separatorChar = global::System.IO.Path.DirectorySeparatorChar;
        public static readonly global::java.lang.String pathSeparator =
                global::java.lang.String.Wrap(global::System.IO.Path.PathSeparator.ToString());
        public static readonly char pathSeparatorChar = global::System.IO.Path.PathSeparator;

        private static readonly char[] TrailSeparators =
        {
            global::System.IO.Path.DirectorySeparatorChar,
            global::System.IO.Path.AltDirectorySeparatorChar,
            '/'
        };

        internal string path;

        public File(global::java.lang.RawNew r) : base(r)
        {
            path = "";
        }

        internal File(string p) : base(global::java.lang.RawNew.I)
        {
            path = p;
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String pathname)
        {
            path = global::java.nio.file.Path.Normalize(pathname == null ? "" : pathname.Value);
        }

        public void __init_Ljava_lang_String_Ljava_lang_String__V(global::java.lang.String parent,
                global::java.lang.String child)
        {
            string p = parent == null ? "" : parent.Value;
            string c = child == null ? "" : child.Value;
            path = global::java.nio.file.Path.Normalize(
                    p.Length == 0 ? c : global::System.IO.Path.Combine(p, c));
        }

        public void __init_Ljava_io_File_Ljava_lang_String__V(File parent, global::java.lang.String child)
        {
            string p = parent == null ? "" : parent.path;
            string c = child == null ? "" : child.Value;
            path = global::java.nio.file.Path.Normalize(
                    p.Length == 0 ? c : global::System.IO.Path.Combine(p, c));
        }

        public global::java.lang.String getName()
        {
            return global::java.lang.String.Wrap(global::System.IO.Path.GetFileName(path.TrimEnd(TrailSeparators)));
        }

        public global::java.lang.String getParent()
        {
            string d = global::System.IO.Path.GetDirectoryName(path.TrimEnd(TrailSeparators));
            return string.IsNullOrEmpty(d) ? null : global::java.lang.String.Wrap(d);
        }

        public File getParentFile()
        {
            string d = global::System.IO.Path.GetDirectoryName(path.TrimEnd(TrailSeparators));
            return string.IsNullOrEmpty(d) ? null : new File(d);
        }

        public global::java.lang.String getPath()
        {
            return global::java.lang.String.Wrap(path);
        }

        public global::java.lang.String getAbsolutePath()
        {
            return global::java.lang.String.Wrap(global::System.IO.Path.GetFullPath(path));
        }

        public File getAbsoluteFile()
        {
            return new File(global::System.IO.Path.GetFullPath(path));
        }

        public global::java.lang.String getCanonicalPath()
        {
            return global::java.lang.String.Wrap(global::System.IO.Path.GetFullPath(path));
        }

        public int exists()
        {
            return global::System.IO.File.Exists(path) || global::System.IO.Directory.Exists(path)
                    || global::java.lang.J2csNative.IsPackagedNative(path) ? 1 : 0;
        }

        public int isDirectory()
        {
            return global::System.IO.Directory.Exists(path) ? 1 : 0;
        }

        public int isFile()
        {
            return global::System.IO.File.Exists(path) ? 1 : 0;
        }

        public int isAbsolute()
        {
            return global::System.IO.Path.IsPathRooted(path) ? 1 : 0;
        }

        public int mkdir()
        {
            if (global::System.IO.Directory.Exists(path))
            {
                return 0;
            }
            global::System.IO.Directory.CreateDirectory(path);
            return 1;
        }

        public int mkdirs()
        {
            if (global::System.IO.Directory.Exists(path))
            {
                return 0;
            }
            global::System.IO.Directory.CreateDirectory(path);
            return 1;
        }

        public int createNewFile()
        {
            if (global::System.IO.File.Exists(path) || global::System.IO.Directory.Exists(path))
            {
                return 0;
            }
            using (global::System.IO.File.Create(path))
            {
            }
            return 1;
        }

        public int delete()
        {
            try
            {
                if (global::System.IO.Directory.Exists(path))
                {
                    global::System.IO.Directory.Delete(path);
                    return 1;
                }
                if (global::System.IO.File.Exists(path))
                {
                    global::System.IO.File.Delete(path);
                    return 1;
                }
            }
            catch (global::System.IO.IOException)
            {
            }
            return 0;
        }

        public int renameTo(File dest)
        {
            try
            {
                if (global::System.IO.Directory.Exists(path))
                {
                    global::System.IO.Directory.Move(path, dest.path);
                    return 1;
                }
                if (global::System.IO.File.Exists(path))
                {
                    global::System.IO.File.Move(path, dest.path);
                    return 1;
                }
            }
            catch (global::System.IO.IOException)
            {
            }
            return 0;
        }

        public long length()
        {
            return global::System.IO.File.Exists(path) ? new global::System.IO.FileInfo(path).Length : 0L;
        }

        public int canRead()
        {
            return exists();
        }

        public int canWrite()
        {
            return exists();
        }

        public global::java.lang.String[] list()
        {
            if (!global::System.IO.Directory.Exists(path))
            {
                return null;
            }
            var entries = global::System.IO.Directory.GetFileSystemEntries(path);
            var result = new global::java.lang.String[entries.Length];
            for (int i = 0; i < entries.Length; i++)
            {
                result[i] = global::java.lang.String.Wrap(global::System.IO.Path.GetFileName(entries[i]));
            }
            return result;
        }

        public File[] listFiles()
        {
            if (!global::System.IO.Directory.Exists(path))
            {
                return null;
            }
            var entries = global::System.IO.Directory.GetFileSystemEntries(path);
            var result = new File[entries.Length];
            for (int i = 0; i < entries.Length; i++)
            {
                result[i] = new File(entries[i]);
            }
            return result;
        }

        public global::java.nio.file.Path toPath()
        {
            return new global::java.nio.file.Path(path);
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is File f && f.path == path ? 1 : 0;
        }

        public override int hashCode()
        {
            return path.GetHashCode();
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(path);
        }
    }
}
