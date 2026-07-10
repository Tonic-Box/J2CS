namespace java.lang.@ref
{
    public class Reference : global::java.lang.Object
    {
        internal global::java.lang.Object referent;
        internal ReferenceQueue queue;

        public Reference(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual global::java.lang.Object get()
        {
            return referent;
        }

        public virtual void clear()
        {
            referent = null;
        }

        public int refersTo(global::java.lang.Object obj)
        {
            return global::System.Object.ReferenceEquals(referent, obj) ? 1 : 0;
        }

        public int isEnqueued()
        {
            return 0;
        }

        public int enqueue()
        {
            return 0;
        }
    }
}
