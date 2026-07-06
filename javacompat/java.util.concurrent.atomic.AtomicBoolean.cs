namespace java.util.concurrent.atomic
{
    public class AtomicBoolean : global::java.lang.Object
    {
        private int value;

        public AtomicBoolean(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V() { value = 0; }
        public void __init_Z_V(int initialValue) { value = initialValue != 0 ? 1 : 0; }

        public int get() { return global::System.Threading.Volatile.Read(ref value); }
        public void set(int newValue) { global::System.Threading.Volatile.Write(ref value, newValue != 0 ? 1 : 0); }
        public int getAndSet(int newValue)
        {
            return global::System.Threading.Interlocked.Exchange(ref value, newValue != 0 ? 1 : 0);
        }
        public int compareAndSet(int expect, int update)
        {
            int e = expect != 0 ? 1 : 0;
            return global::System.Threading.Interlocked.CompareExchange(ref value, update != 0 ? 1 : 0, e) == e ? 1 : 0;
        }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(get() != 0 ? "true" : "false"); }
    }
}
