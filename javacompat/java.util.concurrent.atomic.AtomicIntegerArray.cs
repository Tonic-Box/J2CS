namespace java.util.concurrent.atomic
{
    public class AtomicIntegerArray : global::java.lang.Object
    {
        private int[] arr;

        public AtomicIntegerArray(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_I_V(int length)
        {
            arr = new int[length];
        }

        public void __init__I_V(int[] array)
        {
            arr = (int[])array.Clone();
        }

        public int length() { return arr.Length; }
        public int get(int i) { return arr[i]; }
        public void set(int i, int newValue) { arr[i] = newValue; }
        public int getAndSet(int i, int newValue) { int o = arr[i]; arr[i] = newValue; return o; }
        public int incrementAndGet(int i) { return ++arr[i]; }
        public int decrementAndGet(int i) { return --arr[i]; }
        public int getAndIncrement(int i) { return arr[i]++; }
        public int getAndDecrement(int i) { return arr[i]--; }
        public int addAndGet(int i, int delta) { arr[i] += delta; return arr[i]; }
        public int getAndAdd(int i, int delta) { int o = arr[i]; arr[i] += delta; return o; }

        public int compareAndSet(int i, int expect, int update)
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
