namespace java.lang
{
    public class ThreadLocal : global::java.lang.Object
    {
        private readonly global::System.Threading.ThreadLocal<global::java.lang.Object> tl;
        private global::java.util.function.Supplier supplier;

        public ThreadLocal(global::java.lang.RawNew r) : base(r)
        {
            tl = new global::System.Threading.ThreadLocal<global::java.lang.Object>(() => Initial());
        }

        public void __init__V() { }

        private global::java.lang.Object Initial()
        {
            return supplier != null ? supplier.get() : initialValue();
        }

        public virtual global::java.lang.Object initialValue() { return null; }

        public global::java.lang.Object get() { return tl.Value; }
        public void set(global::java.lang.Object value) { tl.Value = value; }
        public void remove() { tl.Value = Initial(); }

        public static ThreadLocal withInitial(global::java.util.function.Supplier s)
        {
            var t = new ThreadLocal(global::java.lang.RawNew.I);
            t.supplier = s;
            return t;
        }
    }
}
