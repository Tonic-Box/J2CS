namespace java.lang.reflect
{
    public sealed class Method : global::java.lang.Object
    {
        private readonly global::java.lang.Class declaringClass;
        private readonly string name;
        private readonly global::java.lang.Class[] paramTypes;
        private readonly global::java.lang.Class returnType;
        private readonly int modifiers;
        private readonly global::System.Func<global::java.lang.Object, global::java.lang.Object[], global::java.lang.Object> invoker;
        private readonly global::java.lang.annotation.Annotation[] annotations;

        private Method(global::java.lang.Class declaringClass, string name, global::java.lang.Class[] paramTypes,
            global::java.lang.Class returnType, int modifiers,
            global::System.Func<global::java.lang.Object, global::java.lang.Object[], global::java.lang.Object> invoker,
            global::java.lang.annotation.Annotation[] annotations)
            : base(global::java.lang.RawNew.I)
        {
            this.declaringClass = declaringClass;
            this.name = name;
            this.paramTypes = paramTypes;
            this.returnType = returnType;
            this.modifiers = modifiers;
            this.invoker = invoker;
            this.annotations = annotations;
        }

        public static Method __Make(global::java.lang.Class declaringClass, string name,
            global::java.lang.Class[] paramTypes, global::java.lang.Class returnType, int modifiers,
            global::System.Func<global::java.lang.Object, global::java.lang.Object[], global::java.lang.Object> invoker,
            global::java.lang.annotation.Annotation[] annotations)
        {
            return new Method(declaringClass, name, paramTypes, returnType, modifiers, invoker, annotations);
        }

        public int isAnnotationPresent(global::java.lang.Class type)
        {
            return global::j2cs.reflect.AnnoQuery.Present(annotations, type);
        }

        public global::java.lang.annotation.Annotation getAnnotation(global::java.lang.Class type)
        {
            return global::j2cs.reflect.AnnoQuery.Get(annotations, type);
        }

        public global::java.lang.annotation.Annotation[] getAnnotationsByType(global::java.lang.Class type)
        {
            return global::j2cs.reflect.AnnoQuery.ByType(annotations, type);
        }

        public global::java.lang.annotation.Annotation[] getDeclaredAnnotations()
        {
            return annotations;
        }

        public string NameString => name;
        public global::java.lang.Class[] ParamTypesInternal => paramTypes;

        public global::java.lang.Object invoke(global::java.lang.Object obj, global::java.lang.Object[] args)
        {
            return invoker(obj, args);
        }

        public global::java.lang.String getName()
        {
            return global::java.lang.String.Wrap(name);
        }

        public global::java.lang.Class getReturnType()
        {
            return returnType;
        }

        public global::java.lang.Class[] getParameterTypes()
        {
            return paramTypes;
        }

        public int getParameterCount()
        {
            return paramTypes.Length;
        }

        public int getModifiers()
        {
            return modifiers;
        }

        public global::java.lang.Class getDeclaringClass()
        {
            return declaringClass;
        }

        public void setAccessible(int flag)
        {
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(declaringClass.getName().Value + "." + name);
        }
    }
}
