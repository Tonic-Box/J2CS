namespace sun.misc
{
    using JObject = global::java.lang.Object;

    /// <summary>
    /// Minimal <c>sun.misc.Unsafe</c> covering what LWJGL's MemoryUtil/MemoryStack/CustomBuffer use.
    /// It follows the real Unsafe's unified access model: every accessor takes a base object and an
    /// offset. A <b>null base</b> means the offset is an absolute native address (LWJGL's native
    /// memory reads/writes); a <b>non-null base</b> means object-relative field access, where the
    /// offset is a token minted by <see cref="objectFieldOffset"/> that maps back to a reflection
    /// Field — so field reads/writes ride on the existing reflection getters/setters. The singleton
    /// is discovered by LWJGL via reflection over the static <c>theUnsafe</c> field (registered in
    /// <c>j2cs.reflect.ShimMeta</c>).
    /// </summary>
    public sealed unsafe class Unsafe : JObject
    {
        internal static readonly Unsafe theUnsafe = new Unsafe(global::java.lang.RawNew.I);

        private static readonly global::System.Collections.Generic.List<global::java.lang.reflect.Field> Fields =
            new global::System.Collections.Generic.List<global::java.lang.reflect.Field>();
        private static readonly global::System.Collections.Generic.Dictionary<global::java.lang.reflect.Field, long> FieldIds =
            new global::System.Collections.Generic.Dictionary<global::java.lang.reflect.Field, long>();
        private static readonly object FieldLock = new object();

        public Unsafe(global::java.lang.RawNew r) : base(r)
        {
        }

        public long objectFieldOffset(global::java.lang.reflect.Field field)
        {
            lock (FieldLock)
            {
                if (FieldIds.TryGetValue(field, out long existing))
                {
                    return existing;
                }
                long id = Fields.Count;
                Fields.Add(field);
                FieldIds[field] = id;
                return id;
            }
        }

        private static global::java.lang.reflect.Field FieldAt(long offset)
        {
            lock (FieldLock)
            {
                return Fields[(int)offset];
            }
        }

        private static long AsLong(JObject v) { return ((global::java.lang.Number)v).longValue(); }
        private static int AsInt(JObject v) { return ((global::java.lang.Number)v).intValue(); }
        private static short AsShort(JObject v) { return ((global::java.lang.Number)v).shortValue(); }
        private static sbyte AsByte(JObject v) { return ((global::java.lang.Number)v).byteValue(); }
        private static float AsFloat(JObject v) { return ((global::java.lang.Number)v).floatValue(); }
        private static double AsDouble(JObject v) { return ((global::java.lang.Number)v).doubleValue(); }

        public sbyte getByte(JObject o, long offset)
        {
            return o == null ? (sbyte)((byte*)offset)[0] : AsByte(FieldAt(offset).get(o));
        }

        public void putByte(JObject o, long offset, sbyte x)
        {
            if (o == null) { ((byte*)offset)[0] = (byte)x; } else { FieldAt(offset).set(o, global::java.lang.Byte.valueOf(x)); }
        }

        public short getShort(JObject o, long offset)
        {
            return o == null ? *(short*)offset : AsShort(FieldAt(offset).get(o));
        }

        public void putShort(JObject o, long offset, short x)
        {
            if (o == null) { *(short*)offset = x; } else { FieldAt(offset).set(o, global::java.lang.Short.valueOf(x)); }
        }

        public int getInt(JObject o, long offset)
        {
            return o == null ? *(int*)offset : AsInt(FieldAt(offset).get(o));
        }

        public void putInt(JObject o, long offset, int x)
        {
            if (o == null) { *(int*)offset = x; } else { FieldAt(offset).set(o, global::java.lang.Integer.valueOf(x)); }
        }

        public long getLong(JObject o, long offset)
        {
            return o == null ? *(long*)offset : AsLong(FieldAt(offset).get(o));
        }

        public void putLong(JObject o, long offset, long x)
        {
            if (o == null) { *(long*)offset = x; } else { FieldAt(offset).set(o, global::java.lang.Long.valueOf(x)); }
        }

        public float getFloat(JObject o, long offset)
        {
            return o == null ? *(float*)offset : AsFloat(FieldAt(offset).get(o));
        }

        public void putFloat(JObject o, long offset, float x)
        {
            if (o == null) { *(float*)offset = x; } else { FieldAt(offset).set(o, global::java.lang.Float.valueOf(x)); }
        }

        public double getDouble(JObject o, long offset)
        {
            return o == null ? *(double*)offset : AsDouble(FieldAt(offset).get(o));
        }

        public void putDouble(JObject o, long offset, double x)
        {
            if (o == null) { *(double*)offset = x; } else { FieldAt(offset).set(o, global::java.lang.Double.valueOf(x)); }
        }

        public JObject getObject(JObject o, long offset)
        {
            return o == null ? null : FieldAt(offset).get(o);
        }

        public void putObject(JObject o, long offset, JObject x)
        {
            if (o != null) { FieldAt(offset).set(o, x); }
        }

        public void copyMemory(JObject srcBase, long srcOffset, JObject destBase, long destOffset, long bytes)
        {
            if (srcBase == null && destBase == null)
            {
                global::System.Buffer.MemoryCopy((void*)srcOffset, (void*)destOffset, bytes, bytes);
                return;
            }
            for (long i = 0; i < bytes; i++)
            {
                putByte(destBase, destOffset + i, getByte(srcBase, srcOffset + i));
            }
        }

        public void setMemory(JObject o, long offset, long bytes, sbyte value)
        {
            if (o == null)
            {
                global::System.Runtime.InteropServices.NativeMemory.Fill((void*)offset, (nuint)bytes, (byte)value);
                return;
            }
            for (long i = 0; i < bytes; i++) { putByte(o, offset + i, value); }
        }

        public JObject allocateInstance(global::java.lang.Class cls)
        {
            global::System.Type t = cls.ClrType();
            if (t == null)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.InstantiationException(global::java.lang.RawNew.I));
            }
            return (JObject)global::System.Runtime.CompilerServices.RuntimeHelpers.GetUninitializedObject(t);
        }

        public int pageSize() { return 4096; }
    }
}
