namespace java.lang.reflect
{
    public sealed class Constructor : global::java.lang.Object
    {
        private readonly global::java.lang.Class declaringClass;
        private readonly global::java.lang.Class[] paramTypes;
        private readonly int modifiers;
        private readonly global::System.Func<global::java.lang.Object[], global::java.lang.Object> factory;

        private Constructor(global::java.lang.Class declaringClass, global::java.lang.Class[] paramTypes, int modifiers,
            global::System.Func<global::java.lang.Object[], global::java.lang.Object> factory)
            : base(global::java.lang.RawNew.I)
        {
            this.declaringClass = declaringClass;
            this.paramTypes = paramTypes;
            this.modifiers = modifiers;
            this.factory = factory;
        }

        public static Constructor __Make(global::java.lang.Class declaringClass, global::java.lang.Class[] paramTypes,
            int modifiers, global::System.Func<global::java.lang.Object[], global::java.lang.Object> factory)
        {
            return new Constructor(declaringClass, paramTypes, modifiers, factory);
        }

        public global::java.lang.Object newInstance(global::java.lang.Object[] args)
        {
            return factory(args);
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
            return declaringClass.getName();
        }
    }
}
