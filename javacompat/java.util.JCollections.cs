namespace java.util
{
    internal sealed class ShimListIterator : global::java.lang.Object, Iterator
    {
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> items;
        private int index;

        internal ShimListIterator(global::System.Collections.Generic.List<global::java.lang.Object> items)
                : base(global::java.lang.RawNew.I)
        {
            this.items = items;
        }

        public int hasNext()
        {
            return index < items.Count ? 1 : 0;
        }

        public global::java.lang.Object next()
        {
            return items[index++];
        }
    }

    internal static class JCollections
    {
        public static bool Eq(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (a == null)
            {
                return b == null;
            }
            return a.equals(b) != 0;
        }

        public static int Hash(global::java.lang.Object o)
        {
            return o == null ? 0 : o.hashCode();
        }

        public static string Render(global::System.Collections.Generic.List<global::java.lang.Object> items)
        {
            var sb = new global::System.Text.StringBuilder();
            sb.Append('[');
            for (int i = 0; i < items.Count; i++)
            {
                if (i > 0)
                {
                    sb.Append(", ");
                }
                sb.Append(global::java.lang.JRuntime.Str(items[i]));
            }
            sb.Append(']');
            return sb.ToString();
        }
    }
}
