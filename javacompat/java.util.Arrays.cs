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

        public static void setAll(global::java.lang.Object[] a, global::java.util.function.IntFunction generator)
        {
            for (int i = 0; i < a.Length; i++) { a[i] = generator.apply(i); }
        }

        public static global::java.lang.String deepToString(global::java.lang.Object[] a)
        {
            if (a == null) { return global::java.lang.String.Wrap("null"); }
            var sb = new global::System.Text.StringBuilder();
            DeepArr(a, sb, new global::System.Collections.Generic.HashSet<object>(
                    global::System.Collections.Generic.ReferenceEqualityComparer.Instance));
            return global::java.lang.String.Wrap(sb.ToString());
        }

        private static void DeepArr(global::System.Array node, global::System.Text.StringBuilder sb,
                global::System.Collections.Generic.HashSet<object> dejaVu)
        {
            if (node is global::java.lang.Object[] oa)
            {
                if (dejaVu.Contains(oa)) { sb.Append("[...]"); return; }
                dejaVu.Add(oa);
                sb.Append('[');
                for (int i = 0; i < oa.Length; i++)
                {
                    if (i > 0) { sb.Append(", "); }
                    var e = oa[i];
                    if (e == null) { sb.Append("null"); }
                    else if (e is global::java.lang.J2csArray b) { DeepArr(b.Value, sb, dejaVu); }
                    else { sb.Append(e.toString().Value); }
                }
                sb.Append(']');
                dejaVu.Remove(oa);
            }
            else
            {
                sb.Append(PrimArrToString(node).Value);
            }
        }

        private static global::java.lang.String PrimArrToString(global::System.Array arr)
        {
            switch (arr)
            {
                case int[] x: return toString(x);
                case long[] x: return toString(x);
                case double[] x: return toString(x);
                case char[] x: return toString(x);
                case sbyte[] x: return toString(x);
                case short[] x: return toString(x);
                case float[] x: return toString(x);
                default: return global::java.lang.String.Wrap(arr == null ? "null" : arr.ToString());
            }
        }

        public static int deepEquals(global::java.lang.Object[] a, global::java.lang.Object[] b)
        {
            if (global::System.Object.ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++) { if (!DeepEq(a[i], b[i])) { return 0; } }
            return 1;
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
            if (a is global::java.lang.Object ja && b is global::java.lang.Object jb) { return ja.equals(jb) != 0; }
            return a.Equals(b);
        }

        public static int deepHashCode(global::java.lang.Object[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a)
            {
                int el;
                if (e == null) { el = 0; }
                else if (e is global::java.lang.J2csArray b) { el = DeepElemHash(b.Value); }
                else { el = e.hashCode(); }
                r = 31 * r + el;
            }
            return r;
        }

        private static int DeepElemHash(global::System.Array arr)
        {
            switch (arr)
            {
                case global::java.lang.Object[] oa: return deepHashCode(oa);
                case int[] x: return hashCode(x);
                case long[] x: return hashCode(x);
                case double[] x: return hashCode(x);
                case char[] x: return hashCode(x);
                case sbyte[] x: return hashCode(x);
                case short[] x: return hashCode(x);
                case float[] x: return hashCode(x);
                default: return arr == null ? 0 : arr.GetHashCode();
            }
        }

        public static global::java.lang.String toString(float[] a)
        {
            return Render(a == null ? null : global::System.Array.ConvertAll(a, v => global::java.lang.JRuntime.Str(v)));
        }

        public static global::java.lang.String toString(sbyte[] a)
        {
            return Render(a == null ? null : global::System.Array.ConvertAll(a, v => global::java.lang.JRuntime.Str(v)));
        }

        public static global::java.lang.String toString(short[] a)
        {
            return Render(a == null ? null : global::System.Array.ConvertAll(a, v => global::java.lang.JRuntime.Str(v)));
        }

        public static int hashCode(int[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { r = 31 * r + e; }
            return r;
        }

        public static int hashCode(long[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { r = 31 * r + (int)(e ^ (long)((ulong)e >> 32)); }
            return r;
        }

        public static int hashCode(double[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { long b = global::java.lang.Double.doubleToLongBits(e); r = 31 * r + (int)(b ^ (long)((ulong)b >> 32)); }
            return r;
        }

        public static int hashCode(char[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { r = 31 * r + e; }
            return r;
        }

        public static int hashCode(sbyte[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { r = 31 * r + e; }
            return r;
        }

        public static int hashCode(short[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { r = 31 * r + e; }
            return r;
        }

        public static int hashCode(float[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { r = 31 * r + global::java.lang.Float.floatToIntBits(e); }
            return r;
        }

        public static int hashCode(global::java.lang.Object[] a)
        {
            if (a == null) { return 0; }
            int r = 1;
            foreach (var e in a) { r = 31 * r + (e == null ? 0 : e.hashCode()); }
            return r;
        }

        public static void fill(double[] a, double val)
        {
            global::System.Array.Fill(a, val);
        }

        public static void fill(float[] a, float val)
        {
            global::System.Array.Fill(a, val);
        }

        public static void fill(sbyte[] a, sbyte val)
        {
            global::System.Array.Fill(a, val);
        }

        public static void fill(short[] a, short val)
        {
            global::System.Array.Fill(a, val);
        }

        public static void sort(float[] a)
        {
            global::System.Array.Sort(a);
        }

        public static void sort(sbyte[] a)
        {
            global::System.Array.Sort(a);
        }

        public static void sort(short[] a)
        {
            global::System.Array.Sort(a);
        }

        public static global::java.util.stream.LongStream stream(long[] array)
        {
            return global::java.util.stream.LongStream.of(array);
        }

        public static global::java.util.stream.DoubleStream stream(double[] array)
        {
            return global::java.util.stream.DoubleStream.of(array);
        }

        public static long[] copyOf(long[] original, int newLength)
        {
            var r = new long[newLength];
            global::System.Array.Copy(original, 0, r, 0, global::System.Math.Min(newLength, original.Length));
            return r;
        }

        public static double[] copyOf(double[] original, int newLength)
        {
            var r = new double[newLength];
            global::System.Array.Copy(original, 0, r, 0, global::System.Math.Min(newLength, original.Length));
            return r;
        }

        public static char[] copyOf(char[] original, int newLength)
        {
            var r = new char[newLength];
            global::System.Array.Copy(original, 0, r, 0, global::System.Math.Min(newLength, original.Length));
            return r;
        }

        public static sbyte[] copyOf(sbyte[] original, int newLength)
        {
            var r = new sbyte[newLength];
            global::System.Array.Copy(original, 0, r, 0, global::System.Math.Min(newLength, original.Length));
            return r;
        }

        public static short[] copyOf(short[] original, int newLength)
        {
            var r = new short[newLength];
            global::System.Array.Copy(original, 0, r, 0, global::System.Math.Min(newLength, original.Length));
            return r;
        }

        public static float[] copyOf(float[] original, int newLength)
        {
            var r = new float[newLength];
            global::System.Array.Copy(original, 0, r, 0, global::System.Math.Min(newLength, original.Length));
            return r;
        }

        public static long[] copyOfRange(long[] original, int from, int to)
        {
            int len = to - from;
            var r = new long[len];
            global::System.Array.Copy(original, from, r, 0, global::System.Math.Min(len, original.Length - from));
            return r;
        }

        public static double[] copyOfRange(double[] original, int from, int to)
        {
            int len = to - from;
            var r = new double[len];
            global::System.Array.Copy(original, from, r, 0, global::System.Math.Min(len, original.Length - from));
            return r;
        }

        public static char[] copyOfRange(char[] original, int from, int to)
        {
            int len = to - from;
            var r = new char[len];
            global::System.Array.Copy(original, from, r, 0, global::System.Math.Min(len, original.Length - from));
            return r;
        }

        public static int equals(long[] a, long[] b)
        {
            if (global::System.Object.ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++) { if (a[i] != b[i]) { return 0; } }
            return 1;
        }

        public static int equals(double[] a, double[] b)
        {
            if (global::System.Object.ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++) { if (global::java.lang.Double.doubleToLongBits(a[i]) != global::java.lang.Double.doubleToLongBits(b[i])) { return 0; } }
            return 1;
        }

        public static int equals(char[] a, char[] b)
        {
            if (global::System.Object.ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++) { if (a[i] != b[i]) { return 0; } }
            return 1;
        }

        public static int equals(sbyte[] a, sbyte[] b)
        {
            if (global::System.Object.ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++) { if (a[i] != b[i]) { return 0; } }
            return 1;
        }

        public static int equals(short[] a, short[] b)
        {
            if (global::System.Object.ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++) { if (a[i] != b[i]) { return 0; } }
            return 1;
        }

        public static int equals(float[] a, float[] b)
        {
            if (global::System.Object.ReferenceEquals(a, b)) { return 1; }
            if (a == null || b == null || a.Length != b.Length) { return 0; }
            for (int i = 0; i < a.Length; i++) { if (global::java.lang.Float.floatToIntBits(a[i]) != global::java.lang.Float.floatToIntBits(b[i])) { return 0; } }
            return 1;
        }

        public static int binarySearch(long[] a, long key)
        {
            int lo = 0, hi = a.Length - 1;
            while (lo <= hi)
            {
                int mid = (int)((uint)(lo + hi) >> 1);
                long v = a[mid];
                if (v < key) { lo = mid + 1; }
                else if (v > key) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
        }

        public static int binarySearch(double[] a, double key)
        {
            int lo = 0, hi = a.Length - 1;
            while (lo <= hi)
            {
                int mid = (int)((uint)(lo + hi) >> 1);
                double v = a[mid];
                if (v < key) { lo = mid + 1; }
                else if (v > key) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
        }

        public static int binarySearch(char[] a, char key)
        {
            int lo = 0, hi = a.Length - 1;
            while (lo <= hi)
            {
                int mid = (int)((uint)(lo + hi) >> 1);
                char v = a[mid];
                if (v < key) { lo = mid + 1; }
                else if (v > key) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
        }

        public static int binarySearch(global::java.lang.Object[] a, global::java.lang.Object key)
        {
            int lo = 0, hi = a.Length - 1;
            while (lo <= hi)
            {
                int mid = (int)((uint)(lo + hi) >> 1);
                int c = Natural(a[mid], key);
                if (c < 0) { lo = mid + 1; }
                else if (c > 0) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
        }

        public static int binarySearch(global::java.lang.Object[] a, global::java.lang.Object key, global::java.util.Comparator c)
        {
            int lo = 0, hi = a.Length - 1;
            while (lo <= hi)
            {
                int mid = (int)((uint)(lo + hi) >> 1);
                int cmp = c != null ? c.compare(a[mid], key) : Natural(a[mid], key);
                if (cmp < 0) { lo = mid + 1; }
                else if (cmp > 0) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
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
