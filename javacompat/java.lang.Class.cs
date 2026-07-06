namespace java.lang
{
    public class Class : Object
    {
        private static readonly global::System.Collections.Concurrent.ConcurrentDictionary<string, Class> NameCache =
            new global::System.Collections.Concurrent.ConcurrentDictionary<string, Class>();

        private readonly string name;
        private readonly global::j2cs.reflect.ClassMeta meta;
        private readonly global::System.Type type;

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

        public String getName()
        {
            return String.Wrap(name);
        }

        public int desiredAssertionStatus()
        {
            return 0;
        }

        public String getSimpleName()
        {
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
            return type != null && other != null && other.type != null && type.IsAssignableFrom(other.type) ? 1 : 0;
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
