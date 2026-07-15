namespace j2cs.reflect
{
    using JObject = global::java.lang.Object;
    using JClass = global::java.lang.Class;
    using Annotation = global::java.lang.annotation.Annotation;

    /// <summary>
    /// Reflection metadata for the few shim types that libraries inspect reflectively. LWJGL reads a
    /// direct buffer's native pointer through <c>Unsafe.getLong(buffer, offsetOf(Buffer.address))</c>,
    /// and discovers the Unsafe singleton by scanning <c>Unsafe.class.getDeclaredFields()</c> for the
    /// static field whose type is Unsafe. Neither shim carries reflection metadata by default, so it
    /// is registered here once at startup — after __Bootstrap.InitAll and before any app &lt;clinit&gt;.
    /// Each field's declared type uses its owning meta's Class object so identity comparisons
    /// (e.g. <c>field.getType().equals(Unsafe.class)</c>) hold.
    /// </summary>
    internal static class ShimMeta
    {
        private static bool registered;

        internal static void RegisterAll()
        {
            if (registered)
            {
                return;
            }
            registered = true;

            // java.nio.Buffer's mark/position/limit/capacity/address, exposed so LWJGL's MemoryUtil
            // can locate them by value-probing a buffer's int fields (getFieldOffset) and read/write
            // them through Unsafe. Names are cosmetic — the probe matches on type (int) and value.
            Annotation[] noAnns = global::System.Array.Empty<Annotation>();
            JClass intType = JClass.Of("int");
            ClassMeta buffer = ClassMeta.New("java.nio.Buffer", typeof(global::java.nio.Buffer), "java.lang.Object");
            buffer.AddField("mark", intType, 0,
                o => global::java.lang.Integer.valueOf(((global::java.nio.Buffer)o).markPos),
                (o, v) => ((global::java.nio.Buffer)o).markPos = ((global::java.lang.Number)v).intValue(), noAnns);
            buffer.AddField("position", intType, 0,
                o => global::java.lang.Integer.valueOf(((global::java.nio.Buffer)o).pos),
                (o, v) => ((global::java.nio.Buffer)o).pos = ((global::java.lang.Number)v).intValue(), noAnns);
            buffer.AddField("limit", intType, 0,
                o => global::java.lang.Integer.valueOf(((global::java.nio.Buffer)o).lim),
                (o, v) => ((global::java.nio.Buffer)o).lim = ((global::java.lang.Number)v).intValue(), noAnns);
            buffer.AddField("capacity", intType, 0,
                o => global::java.lang.Integer.valueOf(((global::java.nio.Buffer)o).cap),
                (o, v) => ((global::java.nio.Buffer)o).cap = ((global::java.lang.Number)v).intValue(), noAnns);
            buffer.AddField("address", JClass.Of("long"), 0,
                o => global::java.lang.Long.valueOf(((global::java.nio.Buffer)o).address),
                (o, v) => ((global::java.nio.Buffer)o).address = ((global::java.lang.Number)v).longValue(), noAnns);
            // The attachment/parent link: a view (asIntBuffer/duplicate/slice) holds its source buffer
            // here. MemoryUtil finds its offset via getFieldOffsetObject (a view's attachment equals its
            // source). Typed as Buffer so the type carries a CLR type and is assignable from any buffer.
            buffer.AddField("att", buffer.ClassObject, 0,
                o => ((global::java.nio.Buffer)o).att,
                (o, v) => ((global::java.nio.Buffer)o).att = v, noAnns);
            Registry.Register(buffer);

            // Each concrete buffer type needs a super-chain (e.g. ByteBuffer -> Buffer -> Object) so
            // getFieldOffset, which starts at the concrete class and walks up, reaches Buffer's fields.
            // None declare int/reference fields of their own that would collide with the probes.
            Registry.Register(ClassMeta.New("java.nio.ByteBuffer", typeof(global::java.nio.ByteBuffer), "java.nio.Buffer"));
            Registry.Register(ClassMeta.New("java.nio.ShortBuffer", typeof(global::java.nio.ShortBuffer), "java.nio.Buffer"));
            Registry.Register(ClassMeta.New("java.nio.CharBuffer", typeof(global::java.nio.CharBuffer), "java.nio.Buffer"));
            Registry.Register(ClassMeta.New("java.nio.IntBuffer", typeof(global::java.nio.IntBuffer), "java.nio.Buffer"));
            Registry.Register(ClassMeta.New("java.nio.LongBuffer", typeof(global::java.nio.LongBuffer), "java.nio.Buffer"));
            Registry.Register(ClassMeta.New("java.nio.FloatBuffer", typeof(global::java.nio.FloatBuffer), "java.nio.Buffer"));
            Registry.Register(ClassMeta.New("java.nio.DoubleBuffer", typeof(global::java.nio.DoubleBuffer), "java.nio.Buffer"));

            // Native code (e.g. libbulletjme collecting ray/sweep test hits) resolves java/util/List
            // and calls List.add(Object) to append each result; without reflection metadata FindClass
            // returned null, GetMethodID got a null class, and every add silently no-op'd - so the
            // result list came back empty. The invoker dispatches to the receiver's concrete add.
            JClass objType = JClass.Of("java.lang.Object");
            JClass boolType = JClass.Of("boolean");
            ClassMeta collection = ClassMeta.New("java.util.Collection", typeof(global::java.util.Collection), "java.lang.Object");
            collection.AddMethod("add", new JClass[] { objType }, boolType, 1,
                (o, a) => (JObject)global::java.lang.Boolean.valueOf(((global::java.util.Collection)o).add(a[0])), noAnns);
            Registry.Register(collection);
            ClassMeta listMeta = ClassMeta.New("java.util.List", typeof(global::java.util.List), "java.util.Collection");
            listMeta.AddMethod("add", new JClass[] { objType }, boolType, 1,
                (o, a) => (JObject)global::java.lang.Boolean.valueOf(((global::java.util.Collection)o).add(a[0])), noAnns);
            Registry.Register(listMeta);

            ClassMeta unsafe0 = ClassMeta.New("sun.misc.Unsafe", typeof(global::sun.misc.Unsafe), "java.lang.Object");
            unsafe0.AddField("theUnsafe", unsafe0.ClassObject, 24 /* Modifier.STATIC | Modifier.FINAL */,
                o => global::sun.misc.Unsafe.theUnsafe,
                (o, v) => { },
                global::System.Array.Empty<Annotation>());
            Registry.Register(unsafe0);
        }
    }
}
