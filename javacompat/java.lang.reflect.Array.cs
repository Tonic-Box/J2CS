namespace java.lang.reflect
{
    /// <summary>
    /// Reflective array access over boxed Java arrays. Every array reaches here boxed (java.lang.Object),
    /// so the JVM element descriptor is read from the box (J2csArray.Desc) rather than the CLR element
    /// type — the Z->int ABI makes boolean[] and int[] the same CLR type, so the descriptor is the only
    /// faithful discriminator. Primitive elements are wrapped/unwrapped through the java.lang wrappers.
    /// </summary>
    public sealed class Array : global::java.lang.Object
    {
        private Array(global::java.lang.RawNew r) : base(r)
        {
        }

        public static int getLength(global::java.lang.Object array)
        {
            return global::java.lang.JRuntime.Unbox(array).Length;
        }

        public static global::java.lang.Object get(global::java.lang.Object array, int index)
        {
            string elem = ElementDesc(array);
            object v = global::java.lang.JRuntime.Unbox(array).GetValue(index);
            if (v == null)
            {
                return null;
            }
            switch (elem[0])
            {
                case 'I': return global::java.lang.Integer.valueOf((int)v);
                case 'Z': return global::java.lang.Boolean.valueOf((int)v);
                case 'J': return global::java.lang.Long.valueOf((long)v);
                case 'D': return global::java.lang.Double.valueOf((double)v);
                case 'F': return global::java.lang.Float.valueOf((float)v);
                case 'B': return global::java.lang.Byte.valueOf((sbyte)v);
                case 'S': return global::java.lang.Short.valueOf((short)v);
                case 'C': return global::java.lang.Character.valueOf((char)v);
                case '[': return global::java.lang.JRuntime.Box((global::System.Array)v, elem);
                default: return (global::java.lang.Object)v;
            }
        }

        public static void set(global::java.lang.Object array, int index, global::java.lang.Object value)
        {
            string elem = ElementDesc(array);
            global::System.Array a = global::java.lang.JRuntime.Unbox(array);
            switch (elem[0])
            {
                case 'I': a.SetValue(((global::java.lang.Integer)value).intValue(), index); break;
                case 'Z': a.SetValue(((global::java.lang.Boolean)value).booleanValue(), index); break;
                case 'J': a.SetValue(((global::java.lang.Long)value).longValue(), index); break;
                case 'D': a.SetValue(((global::java.lang.Double)value).doubleValue(), index); break;
                case 'F': a.SetValue(((global::java.lang.Float)value).floatValue(), index); break;
                case 'B': a.SetValue((sbyte)((global::java.lang.Byte)value).intValue(), index); break;
                case 'S': a.SetValue((short)((global::java.lang.Short)value).intValue(), index); break;
                case 'C': a.SetValue(((global::java.lang.Character)value).charValue(), index); break;
                case '[': a.SetValue(global::java.lang.JRuntime.Unbox(value), index); break;
                default: a.SetValue(value, index); break;
            }
        }

        public static global::java.lang.Object newInstance(global::java.lang.Class componentType, int length)
        {
            string comp = componentType.Descriptor();
            global::System.Array a = global::System.Array.CreateInstance(
                    ClrElementType(comp, componentType), length);
            return global::java.lang.JRuntime.Box(a, "[" + comp);
        }

        public static int getInt(global::java.lang.Object array, int index)
        {
            return global::System.Convert.ToInt32(global::java.lang.JRuntime.Unbox(array).GetValue(index));
        }

        public static long getLong(global::java.lang.Object array, int index)
        {
            return global::System.Convert.ToInt64(global::java.lang.JRuntime.Unbox(array).GetValue(index));
        }

        public static double getDouble(global::java.lang.Object array, int index)
        {
            return global::System.Convert.ToDouble(global::java.lang.JRuntime.Unbox(array).GetValue(index));
        }

        public static float getFloat(global::java.lang.Object array, int index)
        {
            return global::System.Convert.ToSingle(global::java.lang.JRuntime.Unbox(array).GetValue(index));
        }

        private static string ElementDesc(global::java.lang.Object array)
        {
            if (array is global::java.lang.J2csArray ja)
            {
                return ja.Desc.Substring(1);
            }
            throw global::java.lang.JThrow.of(new global::java.lang.IllegalArgumentException(global::java.lang.RawNew.I));
        }

        private static global::System.Type ClrElementType(string desc, global::java.lang.Class cls)
        {
            switch (desc[0])
            {
                case 'I': case 'Z': return typeof(int);
                case 'J': return typeof(long);
                case 'D': return typeof(double);
                case 'F': return typeof(float);
                case 'B': return typeof(sbyte);
                case 'S': return typeof(short);
                case 'C': return typeof(char);
                case '[': return ClrElementType(desc.Substring(1), cls.getComponentType()).MakeArrayType();
                default:
                    global::System.Type t = cls.ClrType();
                    if (t == null)
                    {
                        t = global::System.Type.GetType(desc.Substring(1, desc.Length - 2).Replace('/', '.'));
                    }
                    return t ?? typeof(global::java.lang.Object);
            }
        }
    }
}
