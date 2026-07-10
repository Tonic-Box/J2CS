namespace j2cs.jni
{
    /**
     * A synthesized JNIEnv the transpiled world passes into classic-JNI native functions. There is
     * no JVM, so J2csJni builds a native-callable function table (a JNINativeInterface_ layout whose
     * slots are unmanaged callbacks into managed code) and hands the native code a JNIEnv* pointing
     * at it. Native code then calls back through the table - NewStringUTF, exception plumbing, refs.
     * jobjects/jstrings are opaque handles into a table mapping to the managed objects.
     */
    public static unsafe class J2csJni
    {
        private const int SlotCount = 240;

        private static readonly object Gate = new object();
        private static readonly global::System.Collections.Generic.List<object> Handles
            = new global::System.Collections.Generic.List<object>();

        [global::System.ThreadStatic]
        private static object PendingThrowable;

        private static global::System.IntPtr functionsBlock;
        private static global::System.IntPtr envSlot;

        public static global::System.IntPtr Env;

        public static void EnsureInitialized()
        {
            lock (Gate)
            {
                if (Env != global::System.IntPtr.Zero)
                {
                    return;
                }
                global::System.IntPtr* slots = (global::System.IntPtr*)
                    global::System.Runtime.InteropServices.NativeMemory.Alloc(
                        (nuint)SlotCount, (nuint)global::System.IntPtr.Size);
                global::System.IntPtr trap =
                    (global::System.IntPtr)(delegate* unmanaged[Cdecl]<void>)&TrapImpl;
                for (int i = 0; i < SlotCount; i++)
                {
                    slots[i] = trap;
                }
                slots[4] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, int>)&GetVersionImpl;
                slots[13] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int>)&ThrowImpl;
                slots[15] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr>)&ExceptionOccurredImpl;
                slots[16] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, void>)&ExceptionDescribeImpl;
                slots[17] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, void>)&ExceptionClearImpl;
                slots[21] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[22] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[23] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[25] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[167] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewStringUTFImpl;
                slots[228] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, byte>)&ExceptionCheckImpl;

                envSlot = (global::System.IntPtr)global::System.Runtime.InteropServices.NativeMemory.Alloc(
                    (nuint)global::System.IntPtr.Size);
                *(global::System.IntPtr*)envSlot = (global::System.IntPtr)slots;
                functionsBlock = (global::System.IntPtr)slots;
                Env = envSlot;
            }
        }

        private static global::System.IntPtr Intern(object o)
        {
            if (o == null)
            {
                return global::System.IntPtr.Zero;
            }
            lock (Gate)
            {
                Handles.Add(o);
                return (global::System.IntPtr)Handles.Count;
            }
        }

        private static object ResolveHandle(global::System.IntPtr h)
        {
            if (h == global::System.IntPtr.Zero)
            {
                return null;
            }
            long idx = (long)h - 1;
            lock (Gate)
            {
                return idx >= 0 && idx < Handles.Count ? Handles[(int)idx] : null;
            }
        }

        public static global::java.lang.String ResolveString(global::System.IntPtr h)
        {
            return ResolveHandle(h) as global::java.lang.String;
        }

        public static global::System.IntPtr NewStringHandle(global::java.lang.String s)
        {
            return Intern(s);
        }

        public static bool HasPendingException()
        {
            return PendingThrowable != null;
        }

        public static object TakePendingException()
        {
            object t = PendingThrowable;
            PendingThrowable = null;
            return t;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int GetVersionImpl(global::System.IntPtr env)
        {
            return 0x00010006;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr NewStringUTFImpl(global::System.IntPtr env, global::System.IntPtr utf)
        {
            if (utf == global::System.IntPtr.Zero)
            {
                return global::System.IntPtr.Zero;
            }
            string s = DecodeModifiedUtf8((byte*)utf);
            return Intern(global::java.lang.String.Wrap(s));
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static byte ExceptionCheckImpl(global::System.IntPtr env)
        {
            return PendingThrowable != null ? (byte)1 : (byte)0;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr ExceptionOccurredImpl(global::System.IntPtr env)
        {
            return PendingThrowable != null ? Intern(PendingThrowable) : global::System.IntPtr.Zero;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void ExceptionClearImpl(global::System.IntPtr env)
        {
            PendingThrowable = null;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void ExceptionDescribeImpl(global::System.IntPtr env)
        {
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int ThrowImpl(global::System.IntPtr env, global::System.IntPtr throwable)
        {
            PendingThrowable = ResolveHandle(throwable);
            return 0;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr NewRefImpl(global::System.IntPtr env, global::System.IntPtr obj)
        {
            return obj;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void DeleteRefImpl(global::System.IntPtr env, global::System.IntPtr obj)
        {
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void TrapImpl()
        {
            global::System.Console.Error.WriteLine(
                "j2cs: unimplemented JNIEnv function invoked by native code");
        }

        /** Decodes a NUL-terminated JNI modified-UTF-8 C string (CESU-8; NUL as 0xC0 0x80). */
        private static string DecodeModifiedUtf8(byte* p)
        {
            var sb = new global::System.Text.StringBuilder();
            int i = 0;
            while (p[i] != 0)
            {
                int b = p[i++];
                if (b < 0x80)
                {
                    sb.Append((char)b);
                }
                else if ((b & 0xE0) == 0xC0)
                {
                    int b2 = p[i++];
                    sb.Append((char)(((b & 0x1F) << 6) | (b2 & 0x3F)));
                }
                else
                {
                    int b2 = p[i++];
                    int b3 = p[i++];
                    sb.Append((char)(((b & 0x0F) << 12) | ((b2 & 0x3F) << 6) | (b3 & 0x3F)));
                }
            }
            return sb.ToString();
        }
    }
}
