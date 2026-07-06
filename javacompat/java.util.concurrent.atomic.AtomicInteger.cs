namespace java.util.concurrent.atomic
{
    public class AtomicInteger : global::java.lang.Number
    {
        private int value;

        public AtomicInteger(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V() { value = 0; }
        public void __init_I_V(int initialValue) { value = initialValue; }

        public int get() { return global::System.Threading.Volatile.Read(ref value); }
        public void set(int newValue) { global::System.Threading.Volatile.Write(ref value, newValue); }
        public int getAndSet(int newValue) { return global::System.Threading.Interlocked.Exchange(ref value, newValue); }
        public int incrementAndGet() { return global::System.Threading.Interlocked.Increment(ref value); }
        public int decrementAndGet() { return global::System.Threading.Interlocked.Decrement(ref value); }
        public int getAndIncrement() { return global::System.Threading.Interlocked.Increment(ref value) - 1; }
        public int getAndDecrement() { return global::System.Threading.Interlocked.Decrement(ref value) + 1; }
        public int addAndGet(int delta) { return global::System.Threading.Interlocked.Add(ref value, delta); }
        public int getAndAdd(int delta) { return global::System.Threading.Interlocked.Add(ref value, delta) - delta; }
        public int compareAndSet(int expect, int update)
        {
            return global::System.Threading.Interlocked.CompareExchange(ref value, update, expect) == expect ? 1 : 0;
        }

        public override int intValue() { return get(); }
        public override long longValue() { return get(); }
        public override float floatValue() { return get(); }
        public override double doubleValue() { return get(); }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(get().ToString(global::System.Globalization.CultureInfo.InvariantCulture)); }
    }
}
