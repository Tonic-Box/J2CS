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

            ClassMeta buffer = ClassMeta.New("java.nio.Buffer", typeof(global::java.nio.Buffer), "java.lang.Object");
            buffer.AddField("address", JClass.Of("long"), 0,
                o => global::java.lang.Long.valueOf(((global::java.nio.Buffer)o).address),
                (o, v) => ((global::java.nio.Buffer)o).address = ((global::java.lang.Number)v).longValue(),
                global::System.Array.Empty<Annotation>());
            Registry.Register(buffer);

            ClassMeta unsafe0 = ClassMeta.New("sun.misc.Unsafe", typeof(global::sun.misc.Unsafe), "java.lang.Object");
            unsafe0.AddField("theUnsafe", unsafe0.ClassObject, 8 /* Modifier.STATIC */,
                o => global::sun.misc.Unsafe.theUnsafe,
                (o, v) => { },
                global::System.Array.Empty<Annotation>());
            Registry.Register(unsafe0);
        }
    }
}
