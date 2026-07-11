namespace java.lang
{
    public class Class : Object
    {
        private static readonly global::System.Collections.Concurrent.ConcurrentDictionary<string, Class> NameCache =
            new global::System.Collections.Concurrent.ConcurrentDictionary<string, Class>();

        private static readonly global::System.Collections.Concurrent.ConcurrentDictionary<string, Class> ArrayCache =
            new global::System.Collections.Concurrent.ConcurrentDictionary<string, Class>();

        private readonly string name;
        private readonly global::j2cs.reflect.ClassMeta meta;
        private readonly global::System.Type type;
        private readonly string componentDesc;

        public Class(RawNew r) : base(r)
        {
            name = "";
        }

        private Class(string name) : base(RawNew.I)
        {
            this.name = name;
        }

        private Class(global::j2cs.reflect.ClassMeta meta) : base(RawNew.I)
        {
            this.meta = meta;
            this.name = meta.Name;
            this.type = meta.Type;
        }

        public static Class FromMeta(global::j2cs.reflect.ClassMeta meta)
        {
            return new Class(meta);
        }

        public static Class Of(string name)
        {
            global::j2cs.reflect.ClassMeta m = global::j2cs.reflect.Registry.ByName(name);
            return m != null ? m.ClassObject : NameCache.GetOrAdd(name, n => new Class(n));
        }

        public global::java.lang.ClassLoader getClassLoader()
        {
            return global::java.lang.ClassLoader.SystemClassLoader;
        }

        public static Class forName(String name)
        {
            string n = name == null ? null : name.Value;
            global::j2cs.reflect.ClassMeta m = n == null ? null : global::j2cs.reflect.Registry.ByName(n);
            if (m != null)
            {
                return m.ClassObject;
            }
            var ex = new global::java.lang.ClassNotFoundException(RawNew.I);
            ex.__init_Ljava_lang_String__V(name);
            throw global::java.lang.JThrow.of(ex);
        }

        public static Class forType(global::System.Type t)
        {
            global::j2cs.reflect.ClassMeta m = global::j2cs.reflect.Registry.ByType(t);
            return m != null ? m.ClassObject : NameCache.GetOrAdd(DottedName(t), n => new Class(n));
        }

        private static string DottedName(global::System.Type t)
        {
            string full = t.FullName;
            return full == null ? t.Name : full.Replace('+', '.');
        }

        // desc is a JVM array descriptor with slashes, e.g. "[I", "[Ljava/lang/Object;", "[[I".
        private Class(string arrayDesc, bool array) : base(RawNew.I)
        {
            this.componentDesc = arrayDesc.Substring(1);
            this.name = arrayDesc.Replace('/', '.');
        }

        public static Class forArray(string desc)
        {
            return ArrayCache.GetOrAdd(desc, d => new Class(d, true));
        }

        private static Class ForDescriptor(string desc)
        {
            switch (desc[0])
            {
                case '[': return forArray(desc);
                case 'L': return Of(desc.Substring(1, desc.Length - 2).Replace('/', '.'));
                case 'I': return Of("int");
                case 'J': return Of("long");
                case 'D': return Of("double");
                case 'F': return Of("float");
                case 'Z': return Of("boolean");
                case 'B': return Of("byte");
                case 'C': return Of("char");
                case 'S': return Of("short");
                default: return Of(desc.Replace('/', '.'));
            }
        }

        public int isArray()
        {
            return componentDesc != null ? 1 : 0;
        }

        internal global::System.Type ClrType()
        {
            return type;
        }

        // JVM descriptor of this class: "I"/"Z"/… for primitives, "[…" for arrays, "L…;" otherwise.
        internal string Descriptor()
        {
            if (componentDesc != null)
            {
                return name.Replace('.', '/');
            }
            switch (name)
            {
                case "int": return "I";
                case "long": return "J";
                case "double": return "D";
                case "float": return "F";
                case "boolean": return "Z";
                case "byte": return "B";
                case "char": return "C";
                case "short": return "S";
                case "void": return "V";
                default: return "L" + name.Replace('.', '/') + ";";
            }
        }

        public Class getComponentType()
        {
            return componentDesc == null ? null : ForDescriptor(componentDesc);
        }

        public String getName()
        {
            return String.Wrap(name);
        }

        public String getCanonicalName()
        {
            if (componentDesc != null)
            {
                return String.Wrap(getComponentType().getCanonicalName().Value + "[]");
            }
            return String.Wrap(name.Replace('$', '.'));
        }

        public Package getPackage()
        {
            int i = name.LastIndexOf('.');
            return new Package(i < 0 ? "" : name.Substring(0, i));
        }

        public int desiredAssertionStatus()
        {
            return 0;
        }

        public String getSimpleName()
        {
            if (componentDesc != null)
            {
                return String.Wrap(getComponentType().getSimpleName().Value + "[]");
            }
            int cut = 0;
            for (int i = 0; i < name.Length; i++)
            {
                if (name[i] == '.' || name[i] == '$')
                {
                    cut = i + 1;
                }
            }
            return String.Wrap(name.Substring(cut));
        }

        public Class getSuperclass()
        {
            return meta != null && meta.SuperName != null ? Of(meta.SuperName) : null;
        }

        public global::java.lang.Object[] getEnumConstants()
        {
            if (meta == null)
            {
                return null;
            }
            var m = meta.FindMethod("values", global::System.Array.Empty<global::java.lang.Class>());
            if (m == null)
            {
                return null;
            }
            // values() returns the concrete E[] (boxed); hand it back via array covariance so the
            // caller's checkcast to E[] (from Class<T>.getEnumConstants's T[] erasure) still succeeds.
            return global::java.lang.JRuntime.Unbox(
                    m.invoke(null, global::System.Array.Empty<global::java.lang.Object>()))
                    as global::java.lang.Object[];
        }

        public global::java.lang.reflect.Field[] getDeclaredFields()
        {
            return meta != null ? meta.Fields() : global::System.Array.Empty<global::java.lang.reflect.Field>();
        }

        public global::java.lang.reflect.Method[] getDeclaredMethods()
        {
            return meta != null ? meta.Methods() : global::System.Array.Empty<global::java.lang.reflect.Method>();
        }

        public global::java.lang.reflect.Constructor[] getDeclaredConstructors()
        {
            return meta != null ? meta.Constructors() : global::System.Array.Empty<global::java.lang.reflect.Constructor>();
        }

        public global::java.lang.reflect.Constructor getConstructor(global::java.lang.Class[] parameterTypes)
        {
            global::java.lang.Class[] want = parameterTypes ?? global::System.Array.Empty<global::java.lang.Class>();
            if (meta != null)
            {
                foreach (global::java.lang.reflect.Constructor c in meta.Constructors())
                {
                    if ((c.getModifiers() & 0x1) != 0 && ParametersMatch(c.getParameterTypes(), want))
                    {
                        return c;
                    }
                }
            }
            var ex = new global::java.lang.NoSuchMethodException(RawNew.I);
            ex.__init_Ljava_lang_String__V(global::java.lang.String.Wrap((name ?? "?") + ".<init>"));
            throw global::java.lang.JThrow.of(ex);
        }

        private static bool ParametersMatch(global::java.lang.Class[] declared, global::java.lang.Class[] want)
        {
            if (declared.Length != want.Length)
            {
                return false;
            }
            for (int i = 0; i < declared.Length; i++)
            {
                string a = declared[i] == null ? null : declared[i].getName().Value;
                string b = want[i] == null ? null : want[i].getName().Value;
                if (a != b)
                {
                    return false;
                }
            }
            return true;
        }

        public global::java.lang.reflect.Field getDeclaredField(String fieldName)
        {
            global::java.lang.reflect.Field f = meta != null ? meta.FindField(fieldName.Value) : null;
            if (f == null)
            {
                throw global::java.lang.JThrow.of(new IllegalStateException(RawNew.I));
            }
            return f;
        }

        public global::java.lang.reflect.Method getDeclaredMethod(String methodName, Class[] paramTypes)
        {
            global::java.lang.reflect.Method m = meta != null ? meta.FindMethod(methodName.Value, paramTypes) : null;
            if (m == null)
            {
                throw global::java.lang.JThrow.of(new IllegalStateException(RawNew.I));
            }
            return m;
        }

        public int isInstance(global::java.lang.Object o)
        {
            return type != null && o != null && type.IsInstanceOfType(o) ? 1 : 0;
        }

        public int isAssignableFrom(Class other)
        {
            if (other == null)
            {
                return 0;
            }
            // Same class (including primitive Class objects like int, which have no CLR type and are
            // cached one-per-name) is always assignable.
            if (global::System.Object.ReferenceEquals(this, other))
            {
                return 1;
            }
            return type != null && other.type != null && type.IsAssignableFrom(other.type) ? 1 : 0;
        }

        public int isAnnotationPresent(Class type)
        {
            return meta != null ? global::j2cs.reflect.AnnoQuery.Present(meta.Annotations, type) : 0;
        }

        public global::java.lang.annotation.Annotation getAnnotation(Class type)
        {
            return meta != null ? global::j2cs.reflect.AnnoQuery.Get(meta.Annotations, type) : null;
        }

        public global::java.lang.annotation.Annotation[] getAnnotationsByType(Class type)
        {
            return meta != null
                ? global::j2cs.reflect.AnnoQuery.ByType(meta.Annotations, type)
                : global::System.Array.Empty<global::java.lang.annotation.Annotation>();
        }

        public global::java.lang.annotation.Annotation[] getDeclaredAnnotations()
        {
            return meta != null ? meta.Annotations : global::System.Array.Empty<global::java.lang.annotation.Annotation>();
        }

        public global::java.lang.Object cast(global::java.lang.Object o)
        {
            if (o == null || isInstance(o) != 0)
            {
                return o;
            }
            throw global::java.lang.JThrow.of(new ClassCastException(RawNew.I));
        }

        public override String toString()
        {
            return String.Wrap("class " + name);
        }
    }
}
