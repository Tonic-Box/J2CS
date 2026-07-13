namespace java.lang
{
    public static class J2csNative
    {
        private static readonly object Gate = new object();

        private static readonly global::System.Collections.Generic.List<global::System.IntPtr> Handles
            = new global::System.Collections.Generic.List<global::System.IntPtr>();

        public static global::System.IntPtr Env = global::System.IntPtr.Zero;

        public static void Load(string path)
        {
            global::j2cs.jni.J2csJni.EnsureInitialized();
            Env = global::j2cs.jni.J2csJni.Env;
            string resolved = Resolve(path);
            global::System.IntPtr handle = global::System.Runtime.InteropServices.NativeLibrary.Load(resolved);
            bool isNew;
            lock (Gate)
            {
                isNew = !Handles.Contains(handle);
                if (isNew)
                {
                    Handles.Add(handle);
                }
            }
            // A real JVM invokes JNI_OnLoad after loading a native library so the library can cache the
            // JavaVM; libraries that dispatch callbacks (e.g. LWJGL's libffi closures) resolve their
            // JNIEnv through that cached VM, and without it the callback path dereferences a null VM.
            if (isNew && global::System.Runtime.InteropServices.NativeLibrary.TryGetExport(handle, "JNI_OnLoad", out global::System.IntPtr onLoad))
            {
                global::System.Runtime.InteropServices.Marshal.GetDelegateForFunctionPointer<JniOnLoadFn>(onLoad)(
                    global::j2cs.jni.J2csJni.JavaVm, global::System.IntPtr.Zero);
            }
        }

        [global::System.Runtime.InteropServices.UnmanagedFunctionPointer(global::System.Runtime.InteropServices.CallingConvention.Cdecl)]
        private delegate int JniOnLoadFn(global::System.IntPtr vm, global::System.IntPtr reserved);

        public static void LoadLibrary(string name)
        {
            string mapped = MapLibraryName(name);
            string candidate = global::System.IO.Path.Combine(
                global::System.AppContext.BaseDirectory, "nativelibs", mapped);
            Load(global::System.IO.File.Exists(candidate) ? candidate : mapped);
        }

        public static string MapLibraryName(string name)
        {
            if (name.EndsWith(".dll") || name.EndsWith(".so") || name.EndsWith(".dylib"))
            {
                return name;
            }
            if (global::System.OperatingSystem.IsWindows())
            {
                return name + ".dll";
            }
            if (global::System.OperatingSystem.IsMacOS())
            {
                return "lib" + name + ".dylib";
            }
            return "lib" + name + ".so";
        }

        public static global::System.IntPtr Export(string symbol)
        {
            lock (Gate)
            {
                foreach (global::System.IntPtr handle in Handles)
                {
                    if (global::System.Runtime.InteropServices.NativeLibrary.TryGetExport(handle, symbol, out global::System.IntPtr addr))
                    {
                        return addr;
                    }
                }
            }
            throw new global::System.EntryPointNotFoundException("j2cs: native symbol not found: " + symbol);
        }

        /**
         * True when {@code path} names a native library the transpiler packaged under nativelibs/.
         * Native-loader code (e.g. jME's NativeLibraryLoader) probes File.exists()/canRead() before
         * System.load; those probes consult this so discovery succeeds against the packaged copy.
         */
        public static bool IsPackagedNative(string path)
        {
            return ResolvePackaged(path) != null;
        }

        private static string Resolve(string path)
        {
            if (path == null)
            {
                return null;
            }
            if (global::System.IO.File.Exists(path))
            {
                return path;
            }
            return ResolvePackaged(path) ?? path;
        }

        private static string ResolvePackaged(string path)
        {
            if (path == null)
            {
                return null;
            }
            string name = global::System.IO.Path.GetFileName(path);
            if (name.Length == 0)
            {
                return null;
            }
            int underscore = name.LastIndexOf('_');
            string bare = underscore >= 0 && underscore + 1 < name.Length
                ? name.Substring(underscore + 1)
                : null;
            foreach (string dir in BaseDirs())
            {
                string candidate = global::System.IO.Path.Combine(dir, "nativelibs", name);
                if (global::System.IO.File.Exists(candidate))
                {
                    return candidate;
                }
                if (bare != null)
                {
                    string bareCandidate = global::System.IO.Path.Combine(dir, "nativelibs", bare);
                    if (global::System.IO.File.Exists(bareCandidate))
                    {
                        return bareCandidate;
                    }
                }
            }
            return null;
        }

        private static global::System.Collections.Generic.IEnumerable<string> BaseDirs()
        {
            yield return global::System.AppContext.BaseDirectory;
            string exe = global::System.Environment.ProcessPath;
            if (exe != null)
            {
                yield return global::System.IO.Path.GetDirectoryName(exe);
            }
            yield return global::System.IO.Directory.GetCurrentDirectory();
        }
    }
}
