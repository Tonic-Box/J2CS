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
        private static global::System.IntPtr javaVmSlot;

        public static global::System.IntPtr Env;
        public static global::System.IntPtr JavaVm;

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
                slots[6] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&FindClassImpl;
                slots[33] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetMethodIDImpl;
                slots[94] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetFieldIDImpl;
                slots[102] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, float>)&GetFloatFieldImpl;
                slots[111] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, float, void>)&SetFloatFieldImpl;
                slots[13] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int>)&ThrowImpl;
                slots[15] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr>)&ExceptionOccurredImpl;
                slots[16] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, void>)&ExceptionDescribeImpl;
                slots[17] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, void>)&ExceptionClearImpl;
                slots[21] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[22] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[23] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[25] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[113] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetStaticMethodIDImpl;
                slots[141] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&CallStaticVoidMethodImpl;
                slots[142] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&CallStaticVoidMethodImpl;
                slots[143] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&CallStaticVoidMethodImpl;
                slots[167] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewStringUTFImpl;
                slots[219] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int>)&GetJavaVMImpl;
                slots[226] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[227] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[228] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, byte>)&ExceptionCheckImpl;

                envSlot = (global::System.IntPtr)global::System.Runtime.InteropServices.NativeMemory.Alloc(
                    (nuint)global::System.IntPtr.Size);
                *(global::System.IntPtr*)envSlot = (global::System.IntPtr)slots;
                functionsBlock = (global::System.IntPtr)slots;
                Env = envSlot;

                global::System.IntPtr* vmSlots = (global::System.IntPtr*)
                    global::System.Runtime.InteropServices.NativeMemory.Alloc(
                        (nuint)8, (nuint)global::System.IntPtr.Size);
                global::System.IntPtr vmTrap =
                    (global::System.IntPtr)(delegate* unmanaged[Cdecl]<void>)&TrapImpl;
                for (int i = 0; i < 8; i++)
                {
                    vmSlots[i] = vmTrap;
                }
                vmSlots[4] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int>)&AttachCurrentThreadImpl;
                vmSlots[5] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, int>)&DetachCurrentThreadImpl;
                vmSlots[6] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int, int>)&GetEnvImpl;
                vmSlots[7] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int>)&AttachCurrentThreadImpl;
                javaVmSlot = (global::System.IntPtr)global::System.Runtime.InteropServices.NativeMemory.Alloc(
                    (nuint)global::System.IntPtr.Size);
                *(global::System.IntPtr*)javaVmSlot = (global::System.IntPtr)vmSlots;
                JavaVm = javaVmSlot;
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

        public static global::System.IntPtr ToHandle(object o)
        {
            return Intern(o);
        }

        public static object ResolveObject(global::System.IntPtr h)
        {
            return ResolveHandle(h);
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
        private static global::System.IntPtr FindClassImpl(global::System.IntPtr env, global::System.IntPtr name)
        {
            if (name == global::System.IntPtr.Zero)
            {
                return global::System.IntPtr.Zero;
            }
            string dotted = DecodeModifiedUtf8((byte*)name).Replace('/', '.');
            global::j2cs.reflect.ClassMeta meta = global::j2cs.reflect.Registry.ByName(dotted);
            return meta != null ? Intern(meta) : global::System.IntPtr.Zero;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr GetFieldIDImpl(global::System.IntPtr env,
            global::System.IntPtr cls, global::System.IntPtr name, global::System.IntPtr sig)
        {
            if (!(ResolveHandle(cls) is global::j2cs.reflect.ClassMeta meta) || name == global::System.IntPtr.Zero)
            {
                return global::System.IntPtr.Zero;
            }
            global::java.lang.reflect.Field field = meta.FindField(DecodeModifiedUtf8((byte*)name));
            return field != null ? Intern(field) : global::System.IntPtr.Zero;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static float GetFloatFieldImpl(global::System.IntPtr env, global::System.IntPtr obj, global::System.IntPtr fieldId)
        {
            if (!(ResolveHandle(fieldId) is global::java.lang.reflect.Field field))
            {
                return 0f;
            }
            global::java.lang.Object boxed = field.get(ResolveHandle(obj) as global::java.lang.Object);
            return boxed is global::java.lang.Number number ? number.floatValue() : 0f;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void SetFloatFieldImpl(global::System.IntPtr env, global::System.IntPtr obj,
            global::System.IntPtr fieldId, float value)
        {
            if (ResolveHandle(fieldId) is global::java.lang.reflect.Field field)
            {
                field.setFloat(ResolveHandle(obj) as global::java.lang.Object, value);
            }
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr GetMethodIDImpl(global::System.IntPtr env,
            global::System.IntPtr cls, global::System.IntPtr name, global::System.IntPtr sig)
        {
            return ResolveMethodId(cls, name, sig);
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr GetStaticMethodIDImpl(global::System.IntPtr env,
            global::System.IntPtr cls, global::System.IntPtr name, global::System.IntPtr sig)
        {
            return ResolveMethodId(cls, name, sig);
        }

        private static global::System.IntPtr ResolveMethodId(global::System.IntPtr cls,
            global::System.IntPtr name, global::System.IntPtr sig)
        {
            if (!(ResolveHandle(cls) is global::j2cs.reflect.ClassMeta meta)
                || name == global::System.IntPtr.Zero || sig == global::System.IntPtr.Zero)
            {
                return global::System.IntPtr.Zero;
            }
            string methodName = DecodeModifiedUtf8((byte*)name);
            int paramCount = CountParams(DecodeModifiedUtf8((byte*)sig));
            foreach (global::java.lang.reflect.Method m in meta.Methods())
            {
                if (m.NameString == methodName && m.ParamTypesInternal.Length == paramCount)
                {
                    return Intern(m);
                }
            }
            return Intern(methodName);
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void CallStaticVoidMethodImpl(global::System.IntPtr env,
            global::System.IntPtr cls, global::System.IntPtr methodId, global::System.IntPtr args)
        {
            if (ResolveHandle(methodId) is global::java.lang.reflect.Method m
                && m.ParamTypesInternal.Length == 0)
            {
                try
                {
                    m.invoke(null, global::System.Array.Empty<global::java.lang.Object>());
                }
                catch (global::System.Exception e)
                {
                    PendingThrowable = e;
                }
            }
            else
            {
                global::System.Console.Error.WriteLine(
                    "j2cs: CallStaticVoidMethod on an unresolved or parameterized method (skipped)");
            }
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int GetJavaVMImpl(global::System.IntPtr env, global::System.IntPtr vm)
        {
            if (vm != global::System.IntPtr.Zero)
            {
                *(global::System.IntPtr*)vm = JavaVm;
            }
            return 0;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int GetEnvImpl(global::System.IntPtr vm, global::System.IntPtr penv, int version)
        {
            if (penv != global::System.IntPtr.Zero)
            {
                *(global::System.IntPtr*)penv = Env;
            }
            return 0;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int AttachCurrentThreadImpl(global::System.IntPtr vm, global::System.IntPtr penv, global::System.IntPtr args)
        {
            if (penv != global::System.IntPtr.Zero)
            {
                *(global::System.IntPtr*)penv = Env;
            }
            return 0;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int DetachCurrentThreadImpl(global::System.IntPtr vm)
        {
            return 0;
        }

        private static int CountParams(string sig)
        {
            int count = 0;
            int i = 1;
            while (i < sig.Length && sig[i] != ')')
            {
                char c = sig[i];
                if (c == '[')
                {
                    i++;
                    continue;
                }
                if (c == 'L')
                {
                    int semi = sig.IndexOf(';', i);
                    i = semi < 0 ? sig.Length : semi + 1;
                    count++;
                    continue;
                }
                i++;
                count++;
            }
            return count;
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
