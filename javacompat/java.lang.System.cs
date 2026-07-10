namespace java.lang
{
    public class System : Object
    {
        public static readonly global::java.io.PrintStream @out =
                new global::java.io.PrintStream(global::System.Console.Out);

        public static readonly global::java.io.PrintStream err =
                new global::java.io.PrintStream(global::System.Console.Error);

        private static readonly global::System.Collections.Concurrent.ConcurrentDictionary<string, string> Props =
                new global::System.Collections.Concurrent.ConcurrentDictionary<string, string>();

        private System() : base(RawNew.I)
        {
        }

        static System()
        {
            Props["os.name"] = OsName();
            Props["os.arch"] = OsArch();
            Props["os.version"] = global::System.Environment.OSVersion.Version.ToString();
            Props["file.separator"] = global::System.IO.Path.DirectorySeparatorChar.ToString();
            Props["path.separator"] = global::System.IO.Path.PathSeparator.ToString();
            Props["line.separator"] = "\n";
            Props["java.io.tmpdir"] = global::System.IO.Path.GetTempPath();
            Props["user.dir"] = global::System.Environment.CurrentDirectory;
            Props["user.home"] = global::System.Environment.GetFolderPath(
                global::System.Environment.SpecialFolder.UserProfile);
            Props["user.name"] = global::System.Environment.UserName;
            Props["java.version"] = "17.0.0";
            Props["java.vendor"] = "j2cs";
            Props["java.specification.version"] = "17";
        }

        private static string OsName()
        {
            if (global::System.OperatingSystem.IsWindows())
            {
                return "Windows 10";
            }
            if (global::System.OperatingSystem.IsMacOS())
            {
                return "Mac OS X";
            }
            if (global::System.OperatingSystem.IsLinux())
            {
                return "Linux";
            }
            return global::System.Runtime.InteropServices.RuntimeInformation.OSDescription;
        }

        private static string OsArch()
        {
            switch (global::System.Runtime.InteropServices.RuntimeInformation.OSArchitecture)
            {
                case global::System.Runtime.InteropServices.Architecture.X64:
                    return "amd64";
                case global::System.Runtime.InteropServices.Architecture.X86:
                    return "x86";
                case global::System.Runtime.InteropServices.Architecture.Arm64:
                    return "aarch64";
                case global::System.Runtime.InteropServices.Architecture.Arm:
                    return "arm";
                default:
                    return "amd64";
            }
        }

        public static String getProperty(String key)
        {
            if (key != null && Props.TryGetValue(key.Value, out var v))
            {
                return String.Wrap(v);
            }
            return null;
        }

        public static String getProperty(String key, String def)
        {
            var v = getProperty(key);
            return v != null ? v : def;
        }

        public static String setProperty(String key, String value)
        {
            if (key == null)
            {
                return null;
            }
            string old = Props.TryGetValue(key.Value, out var prev) ? prev : null;
            Props[key.Value] = value == null ? "" : value.Value;
            return old == null ? null : String.Wrap(old);
        }

        public static String lineSeparator()
        {
            return String.Wrap(global::System.Environment.NewLine);
        }

        public static long nanoTime()
        {
            return (long)(global::System.Diagnostics.Stopwatch.GetTimestamp()
                    * (1_000_000_000.0 / global::System.Diagnostics.Stopwatch.Frequency));
        }

        public static long currentTimeMillis()
        {
            return global::System.DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
        }

        public static void exit(int status)
        {
            global::System.Environment.Exit(status);
        }

        public static void load(String filename)
        {
            global::java.lang.J2csNative.Load(filename == null ? null : filename.Value);
        }

        public static void loadLibrary(String libname)
        {
            global::java.lang.J2csNative.LoadLibrary(libname == null ? null : libname.Value);
        }

        public static String mapLibraryName(String libname)
        {
            return String.Wrap(global::java.lang.J2csNative.MapLibraryName(libname.Value));
        }

        public static void arraycopy(global::System.Array src, int srcPos,
                global::System.Array dest, int destPos, int length)
        {
            if (src == null || dest == null)
            {
                throw JRuntime.Simple(new NullPointerException(RawNew.I));
            }
            if (srcPos < 0 || destPos < 0 || length < 0
                    || (long)srcPos + length > src.Length
                    || (long)destPos + length > dest.Length)
            {
                throw JRuntime.Simple(new ArrayIndexOutOfBoundsException(RawNew.I));
            }
            try
            {
                global::System.Array.Copy(src, srcPos, dest, destPos, length);
            }
            catch (global::System.ArrayTypeMismatchException)
            {
                throw JRuntime.Simple(new ArrayStoreException(RawNew.I));
            }
            catch (global::System.InvalidCastException)
            {
                throw JRuntime.Simple(new ArrayStoreException(RawNew.I));
            }
        }
    }
}
