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

        // Primitive arrays pinned by GetPrimitiveArrayCritical, keyed by their jarray handle so the
        // matching ReleasePrimitiveArrayCritical can unpin them.
        private static readonly global::System.Collections.Generic.Dictionary<global::System.IntPtr, global::System.Runtime.InteropServices.GCHandle> PinnedArrays
            = new global::System.Collections.Generic.Dictionary<global::System.IntPtr, global::System.Runtime.InteropServices.GCHandle>();

        [global::System.ThreadStatic]
        private static object PendingThrowable;

        private static global::System.IntPtr functionsBlock;
        private static global::System.IntPtr envSlot;
        private static global::System.IntPtr javaVmSlot;

        public static global::System.IntPtr Env;
        public static global::System.IntPtr JavaVm;

        private static byte* envHits;
        private static byte* vmHits;

        [global::System.Runtime.InteropServices.DllImport("kernel32")]
        private static extern int VirtualProtect(global::System.IntPtr addr, global::System.UIntPtr size, uint newProtect, out uint old);

        /// <summary>
        /// Points every function-table slot at a pure-native stub that records the slot was hit and
        /// returns a pointer-sized zero. Two properties matter and rule out a managed callback here:
        /// (1) these slots can be invoked while the Windows loader lock is held (a native library's
        /// load-time init calling back through JNIEnv), and reentering the managed runtime under that
        /// lock deadlocks/crashes; (2) native code reads the return register, so leaving it garbage is
        /// read as a non-null jobject / pending exception / true and cascades into more calls — a
        /// neutral zero makes an unimplemented slot degrade quietly. Real slots are assigned over these
        /// afterward; whatever remains is a self-recording no-op whose hits are dumped at exit.
        /// </summary>
        private static void FillNeutralTraps(global::System.IntPtr* slots, int count, byte* hits, int reservedCount)
        {
            // The leading reserved slots of a JNI interface table are not callable functions but scratch
            // the VM owns; LWJGL's ThreadLocalUtil stores per-thread capabilities in one of them and
            // expects it null initially, so leave the reserved slots null rather than trap them.
            for (int i = 0; i < reservedCount; i++)
            {
                slots[i] = global::System.IntPtr.Zero;
            }
            byte* code = (byte*)global::System.Runtime.InteropServices.NativeMemory.Alloc((nuint)(count * 16));
            for (int i = reservedCount; i < count; i++)
            {
                byte* s = code + i * 16;
                s[0] = 0x48; s[1] = 0xB8; *(long*)(s + 2) = (long)(hits + i); // mov rax, &hits[i]
                s[10] = 0xC6; s[11] = 0x00; s[12] = 0x01;                     // mov byte [rax], 1
                s[13] = 0x31; s[14] = 0xC0;                                   // xor eax, eax
                s[15] = 0xC3;                                                 // ret
                slots[i] = (global::System.IntPtr)s;
            }
            VirtualProtect((global::System.IntPtr)code, (global::System.UIntPtr)(uint)(count * 16), 0x40, out _);
        }

        private static void DumpUnimplementedSlots()
        {
            for (int i = 0; i < SlotCount; i++)
            {
                if (envHits[i] != 0) { global::System.Console.Error.WriteLine("j2cs: unimplemented JNIEnv slot " + i + " invoked by native code"); }
            }
            for (int i = 0; i < 8; i++)
            {
                if (vmHits[i] != 0) { global::System.Console.Error.WriteLine("j2cs: unimplemented JavaVM slot " + i + " invoked by native code"); }
            }
        }

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
                envHits = (byte*)global::System.Runtime.InteropServices.NativeMemory.AllocZeroed((nuint)SlotCount);
                FillNeutralTraps(slots, SlotCount, envHits, 4);
                slots[4] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, int>)&GetVersionImpl;
                slots[6] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&FindClassImpl;
                slots[27] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&AllocObjectImpl;
                slots[7] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&FromReflectedMethodImpl;
                slots[33] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetMethodIDImpl;
                slots[94] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetFieldIDImpl;
                slots[102] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, float>)&GetFloatFieldImpl;
                slots[111] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, float, void>)&SetFloatFieldImpl;
                slots[95] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetObjectFieldImpl;
                slots[100] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int>)&GetIntFieldImpl;
                slots[104] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&SetObjectFieldImpl;
                slots[109] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int, void>)&SetIntFieldImpl;
                slots[13] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int>)&ThrowImpl;
                slots[15] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr>)&ExceptionOccurredImpl;
                slots[16] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, void>)&ExceptionDescribeImpl;
                slots[17] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, void>)&ExceptionClearImpl;
                slots[21] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[22] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[23] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[25] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[113] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetStaticMethodIDImpl;
                slots[35] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&CallObjectMethodVImpl;
                slots[38] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, byte>)&CallBooleanMethodVImpl;
                slots[61] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, long, long, void>)&CallVoidMethodImpl;
                slots[62] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&CallVoidMethodVImpl;
                slots[141] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&CallStaticVoidMethodImpl;
                slots[142] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&CallStaticVoidMethodImpl;
                slots[143] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, void>)&CallStaticVoidMethodImpl;
                slots[167] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewStringUTFImpl;
                slots[219] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int>)&GetJavaVMImpl;
                slots[226] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&NewRefImpl;
                slots[227] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, void>)&DeleteRefImpl;
                slots[228] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, byte>)&ExceptionCheckImpl;
                slots[229] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, long, global::System.IntPtr>)&NewDirectByteBufferImpl;
                slots[230] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetDirectBufferAddressImpl;
                slots[231] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, long>)&GetDirectBufferCapacityImpl;
                slots[222] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetPrimitiveArrayCriticalImpl;
                slots[223] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int, void>)&ReleasePrimitiveArrayCriticalImpl;
                // Get/Release<Type>ArrayElements (183-190 / 191-198): native code (e.g. LWJGL's
                // glfwGetFramebufferSize into an int[]) pins a primitive array to read/write it. Pinning
                // is a non-copying view, so the critical get/release impls satisfy every element type.
                for (int __i = 183; __i <= 190; __i++)
                {
                    slots[__i] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, global::System.IntPtr>)&GetPrimitiveArrayCriticalImpl;
                }
                for (int __i = 191; __i <= 198; __i++)
                {
                    slots[__i] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int, void>)&ReleasePrimitiveArrayCriticalImpl;
                }
                slots[171] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int>)&GetArrayLengthImpl;

                envSlot = (global::System.IntPtr)global::System.Runtime.InteropServices.NativeMemory.Alloc(
                    (nuint)global::System.IntPtr.Size);
                *(global::System.IntPtr*)envSlot = (global::System.IntPtr)slots;
                functionsBlock = (global::System.IntPtr)slots;
                Env = envSlot;

                global::System.IntPtr* vmSlots = (global::System.IntPtr*)
                    global::System.Runtime.InteropServices.NativeMemory.Alloc(
                        (nuint)8, (nuint)global::System.IntPtr.Size);
                vmHits = (byte*)global::System.Runtime.InteropServices.NativeMemory.AllocZeroed((nuint)8);
                FillNeutralTraps(vmSlots, 8, vmHits, 3);
                vmSlots[4] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int>)&AttachCurrentThreadImpl;
                vmSlots[5] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, int>)&DetachCurrentThreadImpl;
                vmSlots[6] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, int, int>)&GetEnvImpl;
                vmSlots[7] = (global::System.IntPtr)(delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr, global::System.IntPtr, int>)&AttachCurrentThreadImpl;
                javaVmSlot = (global::System.IntPtr)global::System.Runtime.InteropServices.NativeMemory.Alloc(
                    (nuint)global::System.IntPtr.Size);
                *(global::System.IntPtr*)javaVmSlot = (global::System.IntPtr)vmSlots;
                JavaVm = javaVmSlot;

                global::System.AppDomain.CurrentDomain.ProcessExit += (_, _) => DumpUnimplementedSlots();
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
        private static global::System.IntPtr NewDirectByteBufferImpl(global::System.IntPtr env, global::System.IntPtr address, long capacity)
        {
            global::java.nio.ByteBuffer bb = global::java.nio.ByteBuffer.__wrapDirect((long)address, (int)capacity);
            return Intern(bb);
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr GetDirectBufferAddressImpl(global::System.IntPtr env, global::System.IntPtr buf)
        {
            return ResolveHandle(buf) is global::java.nio.Buffer b
                ? (global::System.IntPtr)b.DirectAddress
                : global::System.IntPtr.Zero;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static long GetDirectBufferCapacityImpl(global::System.IntPtr env, global::System.IntPtr buf)
        {
            return ResolveHandle(buf) is global::java.nio.Buffer b ? b.DirectCapacity : -1;
        }

        // Pins the primitive array and hands native code a pointer straight into its storage; because the
        // array is pinned (never a copy) isCopy is reported false and the matching release only unpins.
        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr GetPrimitiveArrayCriticalImpl(global::System.IntPtr env, global::System.IntPtr array, global::System.IntPtr isCopy)
        {
            if (isCopy != global::System.IntPtr.Zero)
            {
                *(byte*)isCopy = 0;
            }
            if (!(ResolveHandle(array) is global::System.Array arr))
            {
                return global::System.IntPtr.Zero;
            }
            global::System.Runtime.InteropServices.GCHandle gch =
                global::System.Runtime.InteropServices.GCHandle.Alloc(arr, global::System.Runtime.InteropServices.GCHandleType.Pinned);
            lock (Gate)
            {
                PinnedArrays[array] = gch;
            }
            return gch.AddrOfPinnedObject();
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void ReleasePrimitiveArrayCriticalImpl(global::System.IntPtr env, global::System.IntPtr array, global::System.IntPtr carray, int mode)
        {
            lock (Gate)
            {
                if (PinnedArrays.TryGetValue(array, out global::System.Runtime.InteropServices.GCHandle gch))
                {
                    gch.Free();
                    PinnedArrays.Remove(array);
                }
            }
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int GetArrayLengthImpl(global::System.IntPtr env, global::System.IntPtr array)
        {
            return ResolveHandle(array) is global::System.Array arr ? arr.Length : 0;
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

        // Native code (e.g. libbulletjme returning a Vector3f result) allocates a Java object with no
        // constructor via AllocObject, then populates it through setters; an unimplemented neutral trap
        // returned null and the follow-up setter call NRE'd, which poisoned the JNI exception state and
        // stalled the physics tick. Allocate an uninitialized instance of the class's CLR type.
        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr AllocObjectImpl(global::System.IntPtr env, global::System.IntPtr cls)
        {
            if (ResolveHandle(cls) is global::j2cs.reflect.ClassMeta meta && meta.Type != null)
            {
                object o = global::System.Runtime.CompilerServices.RuntimeHelpers.GetUninitializedObject(meta.Type);
                return o == null ? global::System.IntPtr.Zero : Intern(o);
            }
            return global::System.IntPtr.Zero;
        }

        // FromReflectedMethod: a jmethodID here is an interned java.lang.reflect.Method, so resolving
        // the reflected method and re-interning it yields the id LWJGL's callback setup expects.
        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr FromReflectedMethodImpl(global::System.IntPtr env, global::System.IntPtr method)
        {
            return ResolveHandle(method) is global::java.lang.reflect.Method m
                    ? Intern(m) : global::System.IntPtr.Zero;
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

        // Set/Get Object and Int fields: native code populates result structs (e.g. a sweep/ray test's
        // PhysicsSweepTestResult - its collision object and part indices) through these; the neutral
        // trap left the fields untouched so the result list came back empty.
        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void SetObjectFieldImpl(global::System.IntPtr env, global::System.IntPtr obj,
            global::System.IntPtr fieldId, global::System.IntPtr value)
        {
            if (ResolveHandle(fieldId) is global::java.lang.reflect.Field field)
            {
                field.set(ResolveHandle(obj) as global::java.lang.Object, ResolveHandle(value) as global::java.lang.Object);
            }
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void SetIntFieldImpl(global::System.IntPtr env, global::System.IntPtr obj,
            global::System.IntPtr fieldId, int value)
        {
            if (ResolveHandle(fieldId) is global::java.lang.reflect.Field field)
            {
                field.setInt(ResolveHandle(obj) as global::java.lang.Object, value);
            }
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr GetObjectFieldImpl(global::System.IntPtr env, global::System.IntPtr obj, global::System.IntPtr fieldId)
        {
            if (ResolveHandle(fieldId) is global::java.lang.reflect.Field field)
            {
                global::java.lang.Object v = field.get(ResolveHandle(obj) as global::java.lang.Object);
                return v == null ? global::System.IntPtr.Zero : Intern(v);
            }
            return global::System.IntPtr.Zero;
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static int GetIntFieldImpl(global::System.IntPtr env, global::System.IntPtr obj, global::System.IntPtr fieldId)
        {
            return ResolveHandle(fieldId) is global::java.lang.reflect.Field field
                ? field.getInt(ResolveHandle(obj) as global::java.lang.Object) : 0;
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
        private static void CallVoidMethodVImpl(global::System.IntPtr env,
            global::System.IntPtr obj, global::System.IntPtr methodId, global::System.IntPtr vaList)
        {
            if (ResolveHandle(methodId) is global::java.lang.reflect.Method m)
            {
                var receiver = ResolveHandle(obj) as global::java.lang.Object;
                try
                {
                    m.invoke(receiver, ReadVarArgs(vaList, m.ParamTypesInternal));
                }
                catch (global::System.Exception e)
                {
                    PendingThrowable = e;
                }
            }
        }

        // Native code (e.g. libbulletjme's broadphase overlap-filter callback) invokes boolean-returning
        // Java methods through this slot; leaving it a neutral trap returns 0/false and silently rejects
        // every candidate, so the actual method result must be returned.
        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static byte CallBooleanMethodVImpl(global::System.IntPtr env,
            global::System.IntPtr obj, global::System.IntPtr methodId, global::System.IntPtr vaList)
        {
            if (ResolveHandle(methodId) is global::java.lang.reflect.Method m)
            {
                var receiver = ResolveHandle(obj) as global::java.lang.Object;
                try
                {
                    global::java.lang.Object result = m.invoke(receiver, ReadVarArgs(vaList, m.ParamTypesInternal));
                    if (result is global::java.lang.Boolean b) { return (byte)(b.booleanValue() != 0 ? 1 : 0); }
                    if (result is global::java.lang.Number n) { return (byte)(n.intValue() != 0 ? 1 : 0); }
                    return 0;
                }
                catch (global::System.Exception e)
                {
                    PendingThrowable = e;
                }
            }
            return 0;
        }

        // LWJGL's libffi callback handler dispatches every GLFW/GL callback by calling the Java
        // CallbackI.callback(long ret, long args) through the varargs CallVoidMethod slot (not the
        // va_list `V` slot bullet uses). All LWJGL callbacks share that fixed (jlong, jlong) shape, so
        // the two trailing pointers arrive as ordinary arguments; without this the input callbacks no-op.
        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static void CallVoidMethodImpl(global::System.IntPtr env,
            global::System.IntPtr obj, global::System.IntPtr methodId, long arg0, long arg1)
        {
            if (ResolveHandle(methodId) is global::java.lang.reflect.Method m)
            {
                var receiver = ResolveHandle(obj) as global::java.lang.Object;
                try
                {
                    global::java.lang.Class[] pts = m.ParamTypesInternal;
                    var args = new global::java.lang.Object[pts.Length];
                    if (pts.Length >= 1) { args[0] = global::java.lang.Long.valueOf(arg0); }
                    if (pts.Length >= 2) { args[1] = global::java.lang.Long.valueOf(arg1); }
                    m.invoke(receiver, args);
                }
                catch (global::System.Exception e)
                {
                    PendingThrowable = e;
                }
            }
        }

        [global::System.Runtime.InteropServices.UnmanagedCallersOnly(
            CallConvs = new[] { typeof(global::System.Runtime.CompilerServices.CallConvCdecl) })]
        private static global::System.IntPtr CallObjectMethodVImpl(global::System.IntPtr env,
            global::System.IntPtr obj, global::System.IntPtr methodId, global::System.IntPtr vaList)
        {
            if (ResolveHandle(methodId) is global::java.lang.reflect.Method m)
            {
                var receiver = ResolveHandle(obj) as global::java.lang.Object;
                try
                {
                    global::java.lang.Object result = m.invoke(receiver, ReadVarArgs(vaList, m.ParamTypesInternal));
                    return result == null ? global::System.IntPtr.Zero : Intern(result);
                }
                catch (global::System.Exception e)
                {
                    PendingThrowable = e;
                }
            }
            return global::System.IntPtr.Zero;
        }

        /** Reads a win-x64 va_list (8 bytes per slot; float promoted to double) into boxed method args. */
        private static global::java.lang.Object[] ReadVarArgs(global::System.IntPtr vaList,
            global::java.lang.Class[] paramTypes)
        {
            var args = new global::java.lang.Object[paramTypes.Length];
            if (paramTypes.Length == 0 || vaList == global::System.IntPtr.Zero)
            {
                return args;
            }
            byte* p = (byte*)vaList;
            for (int i = 0; i < paramTypes.Length; i++)
            {
                switch (paramTypes[i].getName().Value)
                {
                    case "boolean": args[i] = global::java.lang.Boolean.valueOf(*(int*)p != 0 ? 1 : 0); break;
                    case "byte": args[i] = global::java.lang.Byte.valueOf((sbyte)*(int*)p); break;
                    case "char": args[i] = global::java.lang.Character.valueOf((char)*(int*)p); break;
                    case "short": args[i] = global::java.lang.Short.valueOf((short)*(int*)p); break;
                    case "int": args[i] = global::java.lang.Integer.valueOf(*(int*)p); break;
                    case "long": args[i] = global::java.lang.Long.valueOf(*(long*)p); break;
                    case "float": args[i] = global::java.lang.Float.valueOf((float)*(double*)p); break;
                    case "double": args[i] = global::java.lang.Double.valueOf(*(double*)p); break;
                    default: args[i] = ResolveHandle(*(global::System.IntPtr*)p) as global::java.lang.Object; break;
                }
                p += 8;
            }
            return args;
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
