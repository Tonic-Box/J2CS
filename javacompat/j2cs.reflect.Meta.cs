namespace j2cs.reflect
{
    using JObject = global::java.lang.Object;
    using JClass = global::java.lang.Class;
    using Method = global::java.lang.reflect.Method;
    using Field = global::java.lang.reflect.Field;
    using Ctor = global::java.lang.reflect.Constructor;

    /// <summary>
    /// Runtime reflection registry. Generated per-class __RegisterReflection() methods build a
    /// ClassMeta and Register it; the generated __Bootstrap.InitAll() runs them before user main.
    /// Reflection is served entirely from this statically-generated metadata (never CLR reflection),
    /// so it works under NativeAOT.
    /// </summary>
    public static class Registry
    {
        private static readonly global::System.Collections.Generic.Dictionary<string, ClassMeta> byName =
            new global::System.Collections.Generic.Dictionary<string, ClassMeta>();
        private static readonly global::System.Collections.Generic.Dictionary<global::System.Type, ClassMeta> byType =
            new global::System.Collections.Generic.Dictionary<global::System.Type, ClassMeta>();

        public static void Register(ClassMeta m)
        {
            byName[m.Name] = m;
            if (m.Type != null)
            {
                byType[m.Type] = m;
            }
        }

        public static ClassMeta ByName(string name)
        {
            byName.TryGetValue(name, out ClassMeta m);
            return m;
        }

        public static ClassMeta ByType(global::System.Type t)
        {
            byType.TryGetValue(t, out ClassMeta m);
            return m;
        }
    }

    /// <summary>Metadata for one transpiled class, with a fluent builder used by generated code.</summary>
    public sealed class ClassMeta
    {
        public readonly string Name;
        public readonly global::System.Type Type;
        public readonly string SuperName;
        private readonly JClass classObject;
        private readonly global::System.Collections.Generic.List<Field> fields =
            new global::System.Collections.Generic.List<Field>();
        private readonly global::System.Collections.Generic.List<Method> methods =
            new global::System.Collections.Generic.List<Method>();
        private readonly global::System.Collections.Generic.List<Ctor> ctors =
            new global::System.Collections.Generic.List<Ctor>();

        private ClassMeta(string name, global::System.Type type, string superName)
        {
            Name = name;
            Type = type;
            SuperName = superName;
            classObject = JClass.FromMeta(this);
        }

        public static ClassMeta New(string name, global::System.Type type, string superName)
        {
            return new ClassMeta(name, type, superName);
        }

        public JClass ClassObject => classObject;

        public ClassMeta AddField(string name, JClass type, int modifiers,
            global::System.Func<JObject, JObject> getter,
            global::System.Action<JObject, JObject> setter)
        {
            fields.Add(Field.__Make(classObject, name, type, modifiers, getter, setter));
            return this;
        }

        public ClassMeta AddMethod(string name, JClass[] paramTypes, JClass returnType, int modifiers,
            global::System.Func<JObject, JObject[], JObject> invoker)
        {
            methods.Add(Method.__Make(classObject, name, paramTypes, returnType, modifiers, invoker));
            return this;
        }

        public ClassMeta AddCtor(JClass[] paramTypes, int modifiers,
            global::System.Func<JObject[], JObject> factory)
        {
            ctors.Add(Ctor.__Make(classObject, paramTypes, modifiers, factory));
            return this;
        }

        public Field[] Fields()
        {
            return fields.ToArray();
        }

        public Method[] Methods()
        {
            return methods.ToArray();
        }

        public Ctor[] Constructors()
        {
            return ctors.ToArray();
        }

        public Field FindField(string name)
        {
            foreach (Field f in fields)
            {
                if (f.NameString == name)
                {
                    return f;
                }
            }
            return null;
        }

        public Method FindMethod(string name, JClass[] paramTypes)
        {
            foreach (Method m in methods)
            {
                if (m.NameString == name && SameParams(m.ParamTypesInternal, paramTypes))
                {
                    return m;
                }
            }
            return null;
        }

        private static bool SameParams(JClass[] a, JClass[] b)
        {
            int bn = b == null ? 0 : b.Length;
            if (a.Length != bn)
            {
                return false;
            }
            for (int i = 0; i < a.Length; i++)
            {
                if (!a[i].getName().Value.Equals(b[i].getName().Value))
                {
                    return false;
                }
            }
            return true;
        }
    }
}
