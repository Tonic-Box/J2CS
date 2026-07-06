namespace java.util.concurrent.atomic
{
    public class AtomicLong : global::java.lang.Number
    {
        private long value;

        public AtomicLong(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V() { value = 0L; }
        public void __init_J_V(long initialValue) { value = initialValue; }

        public long get() { return global::System.Threading.Volatile.Read(ref value); }
        public void set(long newValue) { global::System.Threading.Volatile.Write(ref value, newValue); }
        public long getAndSet(long newValue) { return global::System.Threading.Interlocked.Exchange(ref value, newValue); }
        public long incrementAndGet() { return global::System.Threading.Interlocked.Increment(ref value); }
        public long decrementAndGet() { return global::System.Threading.Interlocked.Decrement(ref value); }
        public long getAndIncrement() { return global::System.Threading.Interlocked.Increment(ref value) - 1; }
        public long getAndDecrement() { return global::System.Threading.Interlocked.Decrement(ref value) + 1; }
        public long addAndGet(long delta) { return global::System.Threading.Interlocked.Add(ref value, delta); }
        public long getAndAdd(long delta) { return global::System.Threading.Interlocked.Add(ref value, delta) - delta; }
        public int compareAndSet(long expect, long update)
        {
            return global::System.Threading.Interlocked.CompareExchange(ref value, update, expect) == expect ? 1 : 0;
        }

        public override int intValue() { return (int)get(); }
        public override long longValue() { return get(); }
        public override float floatValue() { return get(); }
        public override double doubleValue() { return get(); }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(get().ToString(global::System.Globalization.CultureInfo.InvariantCulture)); }
    }
}
