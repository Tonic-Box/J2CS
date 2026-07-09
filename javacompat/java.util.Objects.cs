namespace java.util
{
    public sealed class Objects : global::java.lang.Object
    {
        private Objects() : base(global::java.lang.RawNew.I)
        {
        }

        private static global::java.lang.JThrow Npe(string message)
        {
            var npe = new global::java.lang.NullPointerException(global::java.lang.RawNew.I);
            npe.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(message));
            return global::java.lang.JThrow.of(npe);
        }

        public static global::java.lang.Object requireNonNull(global::java.lang.Object o)
        {
            if (o == null)
            {
                throw global::java.lang.JRuntime.Simple(
                        new global::java.lang.NullPointerException(global::java.lang.RawNew.I));
            }
            return o;
        }

        public static global::java.lang.Object requireNonNull(global::java.lang.Object o, global::java.lang.String message)
        {
            if (o == null)
            {
                throw Npe(message == null ? null : message.Value);
            }
            return o;
        }

        public static global::java.lang.Object requireNonNullElse(global::java.lang.Object obj, global::java.lang.Object defaultObj)
        {
            if (obj != null)
            {
                return obj;
            }
            if (defaultObj == null)
            {
                throw Npe("defaultObj");
            }
            return defaultObj;
        }

        public static global::java.lang.Object requireNonNullElseGet(global::java.lang.Object obj, global::java.util.function.Supplier supplier)
        {
            if (obj != null)
            {
                return obj;
            }
            if (supplier == null)
            {
                throw Npe("supplier");
            }
            var v = supplier.get();
            if (v == null)
            {
                throw Npe("supplier.get()");
            }
            return v;
        }

        public static int isNull(global::java.lang.Object o)
        {
            return o == null ? 1 : 0;
        }

        public static int nonNull(global::java.lang.Object o)
        {
            return o != null ? 1 : 0;
        }

        public static int equals(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (global::System.Object.ReferenceEquals(a, b))
            {
                return 1;
            }
            return a != null && a.equals(b) != 0 ? 1 : 0;
        }

        public static int deepEquals(global::java.lang.Object a, global::java.lang.Object b)
        {
            return DeepEq(a, b) ? 1 : 0;
        }

        private static bool DeepEq(object a, object b)
        {
            if (a is global::java.lang.J2csArray ba) { a = ba.Value; }
            if (b is global::java.lang.J2csArray bb) { b = bb.Value; }
            if (global::System.Object.ReferenceEquals(a, b)) { return true; }
            if (a == null || b == null) { return false; }
            if (a is global::System.Array av && b is global::System.Array bv)
            {
                if (av.Length != bv.Length) { return false; }
                for (int i = 0; i < av.Length; i++)
                {
                    if (!DeepEq(av.GetValue(i), bv.GetValue(i))) { return false; }
                }
                return true;
            }
            if (a is global::java.lang.Object ja && b is global::java.lang.Object jb)
            {
                return ja.equals(jb) != 0;
            }
            return a.Equals(b);
        }

        public static int hashCode(global::java.lang.Object o)
        {
            return o == null ? 0 : o.hashCode();
        }

        public static int hash(global::java.lang.Object[] values)
        {
            if (values == null)
            {
                return 0;
            }
            int result = 1;
            foreach (var e in values)
            {
                result = 31 * result + (e == null ? 0 : e.hashCode());
            }
            return result;
        }

        public static global::java.lang.String toString(global::java.lang.Object o)
        {
            return global::java.lang.String.Wrap(o == null ? "null" : o.toString().Value);
        }

        public static global::java.lang.String toString(global::java.lang.Object o, global::java.lang.String nullDefault)
        {
            return o != null ? o.toString() : nullDefault;
        }

        public static int compare(global::java.lang.Object a, global::java.lang.Object b, global::java.util.Comparator c)
        {
            return global::System.Object.ReferenceEquals(a, b) ? 0 : c.compare(a, b);
        }
    }
}
