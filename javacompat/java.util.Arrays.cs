namespace java.util
{
    public class Arrays : global::java.lang.Object
    {
        private Arrays(global::java.lang.RawNew r) : base(r)
        {
        }

        public static global::java.lang.String toString(global::java.lang.Object[] array)
        {
            if (array == null)
            {
                return global::java.lang.String.Wrap("null");
            }
            var sb = new global::System.Text.StringBuilder("[");
            for (int i = 0; i < array.Length; i++)
            {
                if (i > 0)
                {
                    sb.Append(", ");
                }
                sb.Append(global::java.lang.JRuntime.Str(array[i]));
            }
            sb.Append(']');
            return global::java.lang.String.Wrap(sb.ToString());
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

        public static void fill(int[] a, int val)
        {
            if (a != null)
            {
                for (int i = 0; i < a.Length; i++) { a[i] = val; }
            }
        }

        public static void fill(long[] a, long val)
        {
            if (a != null)
            {
                for (int i = 0; i < a.Length; i++) { a[i] = val; }
            }
        }

        public static void fill(global::java.lang.Object[] a, global::java.lang.Object val)
        {
            if (a != null)
            {
                for (int i = 0; i < a.Length; i++) { a[i] = val; }
            }
        }

        public static void sort(int[] a)
        {
            if (a != null) { global::System.Array.Sort(a); }
        }

        public static void sort(long[] a)
        {
            if (a != null) { global::System.Array.Sort(a); }
        }

        public static void sort(double[] a)
        {
            if (a != null) { global::System.Array.Sort(a); }
        }

        public static void sort(char[] a)
        {
            if (a != null) { global::System.Array.Sort(a); }
        }

        public static void sort(global::java.lang.Object[] a)
        {
            if (a != null) { global::System.Array.Sort(a, (x, y) => Natural(x, y)); }
        }

        public static void sort(global::java.lang.Object[] a, global::java.util.Comparator c)
        {
            if (a != null)
            {
                global::System.Array.Sort(a, (x, y) => c != null ? c.compare(x, y) : Natural(x, y));
            }
        }

        public static int[] copyOf(int[] original, int newLength)
        {
            var r = new int[newLength];
            global::System.Array.Copy(original, r, global::System.Math.Min(original.Length, newLength));
            return r;
        }

        public static global::java.lang.Object[] copyOf(global::java.lang.Object[] original, int newLength)
        {
            var r = new global::java.lang.Object[newLength];
            global::System.Array.Copy(original, r, global::System.Math.Min(original.Length, newLength));
            return r;
        }

        public static int[] copyOfRange(int[] original, int from, int to)
        {
            int len = to - from;
            var r = new int[len];
            int n = global::System.Math.Min(original.Length - from, len);
            if (n > 0) { global::System.Array.Copy(original, from, r, 0, n); }
            return r;
        }

        public static global::java.lang.Object[] copyOfRange(global::java.lang.Object[] original, int from, int to)
        {
            int len = to - from;
            var r = new global::java.lang.Object[len];
            int n = global::System.Math.Min(original.Length - from, len);
            if (n > 0) { global::System.Array.Copy(original, from, r, 0, n); }
            return r;
        }

        public static int equals(int[] a, int[] b)
        {
            if (ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++)
            {
                if (a[i] != b[i]) { return 0; }
            }
            return 1;
        }

        public static int equals(global::java.lang.Object[] a, global::java.lang.Object[] b)
        {
            if (ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++)
            {
                var x = a[i];
                var y = b[i];
                bool eq = x == null ? y == null : (y != null && x.equals(y) != 0);
                if (!eq) { return 0; }
            }
            return 1;
        }

        public static int binarySearch(int[] a, int key)
        {
            int lo = 0;
            int hi = a.Length - 1;
            while (lo <= hi)
            {
                int mid = (int)(((uint)lo + (uint)hi) >> 1);
                int midVal = a[mid];
                if (midVal < key) { lo = mid + 1; }
                else if (midVal > key) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
        }

        public static global::java.util.stream.IntStream stream(int[] array)
        {
            return global::java.util.stream.IntStream.of(array);
        }

        public static global::java.lang.String toString(int[] a)
        {
            return Render(a == null ? null : global::System.Array.ConvertAll(a, v => global::java.lang.JRuntime.Str(v)));
        }

        public static global::java.lang.String toString(long[] a)
        {
            return Render(a == null ? null : global::System.Array.ConvertAll(a, v => global::java.lang.JRuntime.Str(v)));
        }

        public static global::java.lang.String toString(double[] a)
        {
            return Render(a == null ? null : global::System.Array.ConvertAll(a, v => global::java.lang.JRuntime.Str(v)));
        }

        public static global::java.lang.String toString(char[] a)
        {
            return Render(a == null ? null : global::System.Array.ConvertAll(a, v => v.ToString()));
        }

        private static global::java.lang.String Render(string[] parts)
        {
            if (parts == null)
            {
                return global::java.lang.String.Wrap("null");
            }
            var sb = new global::System.Text.StringBuilder("[");
            for (int i = 0; i < parts.Length; i++)
            {
                if (i > 0) { sb.Append(", "); }
                sb.Append(parts[i]);
            }
            sb.Append(']');
            return global::java.lang.String.Wrap(sb.ToString());
        }

        private static int Natural(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (a is global::java.lang.Comparable ca)
            {
                return ca.compareTo(b);
            }
            if (a is global::java.lang.Number na && b is global::java.lang.Number nb)
            {
                double x = na.doubleValue();
                double y = nb.doubleValue();
                return x < y ? -1 : (x > y ? 1 : 0);
            }
            return global::System.String.CompareOrdinal(
                global::java.lang.JRuntime.Str(a), global::java.lang.JRuntime.Str(b));
        }
    }
}
