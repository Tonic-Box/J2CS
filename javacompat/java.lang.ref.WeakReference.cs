namespace java.lang.@ref
{
    public class WeakReference : Reference
    {
        public WeakReference(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_lang_Object__V(global::java.lang.Object referent)
        {
            this.referent = referent;
        }

        public void __init_Ljava_lang_Object_Ljava_lang_ref_ReferenceQueue__V(
            global::java.lang.Object referent, ReferenceQueue queue)
        {
            this.referent = referent;
            this.queue = queue;
        }
    }
}
