namespace java.util
{
    public class Arrays : global::java.lang.Object
    {
        private Arrays(global::java.lang.RawNew r) : base(r)
        {
        }

        public static global::java.util.stream.Stream stream(global::java.lang.Object[] array)
        {
            var list = new global::System.Collections.Generic.List<global::java.lang.Object>(
                array == null ? 0 : array.Length);
            if (array != null)
            {
                foreach (var e in array) { list.Add(e); }
            }
            return global::java.util.stream.Stream.Wrap(list);
        }

        public static global::java.util.List asList(global::java.lang.Object[] array)
        {
            var list = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            list.__init__V();
            if (array != null)
            {
                foreach (var e in array) { list.add(e); }
            }
            return list;
        }

        public static void fill(char[] a, char val)
        {
            if (a != null)
            {
                for (int i = 0; i < a.Length; i++)
                {
                    a[i] = val;
                }
            }
        }
    }
}
