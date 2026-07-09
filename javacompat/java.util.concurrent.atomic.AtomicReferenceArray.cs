namespace java.util.concurrent.atomic
{
    public class AtomicReferenceArray : global::java.lang.Object
    {
        private global::java.lang.Object[] arr;

        public AtomicReferenceArray(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_I_V(int length)
        {
            arr = new global::java.lang.Object[length];
        }

        public void __init__Ljava_lang_Object__V(global::java.lang.Object[] array)
        {
            arr = (global::java.lang.Object[])array.Clone();
        }

        public int length() { return arr.Length; }
        public global::java.lang.Object get(int i) { return arr[i]; }
        public void set(int i, global::java.lang.Object newValue) { arr[i] = newValue; }

        public global::java.lang.Object getAndSet(int i, global::java.lang.Object newValue)
        {
            var o = arr[i];
            arr[i] = newValue;
            return o;
        }

        public int compareAndSet(int i, global::java.lang.Object expect, global::java.lang.Object update)
        {
            if (global::System.Object.ReferenceEquals(arr[i], expect))
            {
                arr[i] = update;
                return 1;
            }
            return 0;
        }

        public override global::java.lang.String toString()
        {
            var sb = new global::System.Text.StringBuilder("[");
            for (int i = 0; i < arr.Length; i++)
            {
                if (i > 0) { sb.Append(", "); }
                sb.Append(global::java.lang.JRuntime.Str(arr[i]));
            }
            sb.Append(']');
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }
}
