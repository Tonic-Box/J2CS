namespace java.util.concurrent.atomic
{
    public class AtomicLongArray : global::java.lang.Object
    {
        private long[] arr;

        public AtomicLongArray(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_I_V(int length)
        {
            arr = new long[length];
        }

        public void __init__J_V(long[] array)
        {
            arr = (long[])array.Clone();
        }

        public int length() { return arr.Length; }
        public long get(int i) { return arr[i]; }
        public void set(int i, long newValue) { arr[i] = newValue; }
        public long getAndSet(int i, long newValue) { long o = arr[i]; arr[i] = newValue; return o; }
        public long incrementAndGet(int i) { return ++arr[i]; }
        public long decrementAndGet(int i) { return --arr[i]; }
        public long getAndIncrement(int i) { return arr[i]++; }
        public long getAndDecrement(int i) { return arr[i]--; }
        public long addAndGet(int i, long delta) { arr[i] += delta; return arr[i]; }
        public long getAndAdd(int i, long delta) { long o = arr[i]; arr[i] += delta; return o; }

        public int compareAndSet(int i, long expect, long update)
        {
            if (arr[i] == expect) { arr[i] = update; return 1; }
            return 0;
        }

        public override global::java.lang.String toString()
        {
            var sb = new global::System.Text.StringBuilder("[");
            for (int i = 0; i < arr.Length; i++)
            {
                if (i > 0) { sb.Append(", "); }
                sb.Append(arr[i].ToString(global::System.Globalization.CultureInfo.InvariantCulture));
            }
            sb.Append(']');
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }
}
