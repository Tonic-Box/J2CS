namespace java.lang.reflect
{
    public sealed class Field : global::java.lang.Object
    {
        private readonly global::java.lang.Class declaringClass;
        private readonly string name;
        private readonly global::java.lang.Class type;
        private readonly int modifiers;
        private readonly global::System.Func<global::java.lang.Object, global::java.lang.Object> getter;
        private readonly global::System.Action<global::java.lang.Object, global::java.lang.Object> setter;
        private readonly global::java.lang.annotation.Annotation[] annotations;

        private Field(global::java.lang.Class declaringClass, string name, global::java.lang.Class type, int modifiers,
            global::System.Func<global::java.lang.Object, global::java.lang.Object> getter,
            global::System.Action<global::java.lang.Object, global::java.lang.Object> setter,
            global::java.lang.annotation.Annotation[] annotations)
            : base(global::java.lang.RawNew.I)
        {
            this.declaringClass = declaringClass;
            this.name = name;
            this.type = type;
            this.modifiers = modifiers;
            this.getter = getter;
            this.setter = setter;
            this.annotations = annotations;
        }

        public static Field __Make(global::java.lang.Class declaringClass, string name, global::java.lang.Class type,
            int modifiers, global::System.Func<global::java.lang.Object, global::java.lang.Object> getter,
            global::System.Action<global::java.lang.Object, global::java.lang.Object> setter,
            global::java.lang.annotation.Annotation[] annotations)
        {
            return new Field(declaringClass, name, type, modifiers, getter, setter, annotations);
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

        public int isSynthetic()
        {
            return 0;
        }

        public global::java.lang.Object get(global::java.lang.Object obj)
        {
            return getter(obj);
        }

        public void set(global::java.lang.Object obj, global::java.lang.Object value)
        {
            setter(obj, value);
        }

        public int getInt(global::java.lang.Object obj)
        {
            return ((global::java.lang.Number)getter(obj)).intValue();
        }

        public long getLong(global::java.lang.Object obj)
        {
            return ((global::java.lang.Number)getter(obj)).longValue();
        }

        public double getDouble(global::java.lang.Object obj)
        {
            return ((global::java.lang.Number)getter(obj)).doubleValue();
        }

        public float getFloat(global::java.lang.Object obj)
        {
            return ((global::java.lang.Number)getter(obj)).floatValue();
        }

        public int getShort(global::java.lang.Object obj)
        {
            return ((global::java.lang.Number)getter(obj)).shortValue();
        }

        public int getByte(global::java.lang.Object obj)
        {
            return ((global::java.lang.Number)getter(obj)).byteValue();
        }

        public int getBoolean(global::java.lang.Object obj)
        {
            return ((global::java.lang.Boolean)getter(obj)).booleanValue();
        }

        public char getChar(global::java.lang.Object obj)
        {
            return ((global::java.lang.Character)getter(obj)).charValue();
        }

        public void setInt(global::java.lang.Object obj, int value)
        {
            setter(obj, global::java.lang.Integer.valueOf(value));
        }

        public void setLong(global::java.lang.Object obj, long value)
        {
            setter(obj, global::java.lang.Long.valueOf(value));
        }

        public void setDouble(global::java.lang.Object obj, double value)
        {
            setter(obj, global::java.lang.Double.valueOf(value));
        }

        public void setFloat(global::java.lang.Object obj, float value)
        {
            setter(obj, global::java.lang.Float.valueOf(value));
        }

        public void setBoolean(global::java.lang.Object obj, int value)
        {
            setter(obj, global::java.lang.Boolean.valueOf(value));
        }

        public void setChar(global::java.lang.Object obj, char value)
        {
            setter(obj, global::java.lang.Character.valueOf(value));
        }

        public global::java.lang.String getName()
        {
            return global::java.lang.String.Wrap(name);
        }

        public global::java.lang.Class getType()
        {
            return type;
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
