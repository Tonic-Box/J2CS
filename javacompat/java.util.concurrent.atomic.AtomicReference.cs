namespace java.util.concurrent.atomic
{
    public class AtomicReference : global::java.lang.Object
    {
        private global::java.lang.Object value;

        public AtomicReference(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V() { value = null; }
        public void __init_Ljava_lang_Object__V(global::java.lang.Object initialValue) { value = initialValue; }

        public global::java.lang.Object get() { return global::System.Threading.Volatile.Read(ref value); }
        public void set(global::java.lang.Object newValue) { global::System.Threading.Volatile.Write(ref value, newValue); }
        public global::java.lang.Object getAndSet(global::java.lang.Object newValue)
        {
            return global::System.Threading.Interlocked.Exchange(ref value, newValue);
        }
        public int compareAndSet(global::java.lang.Object expect, global::java.lang.Object update)
        {
            return global::System.Threading.Interlocked.CompareExchange(ref value, update, expect) == expect ? 1 : 0;
        }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(global::java.lang.JRuntime.Str(get())); }
    }
}
