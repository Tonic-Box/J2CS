namespace java.util
{
    // Extendable Map base. Concrete map subclasses (e.g. jME's ListMap) override the full surface, so
    // these virtual bodies are placeholders that keep the base a valid Map implementation.
    public class AbstractMap : global::java.lang.Object, Map
    {
        public AbstractMap(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public virtual global::java.lang.Object put(global::java.lang.Object key, global::java.lang.Object value)
        {
            return null;
        }

        public virtual global::java.lang.Object get(global::java.lang.Object key)
        {
            return null;
        }

        public virtual int containsKey(global::java.lang.Object key)
        {
            return 0;
        }

        public virtual global::java.lang.Object remove(global::java.lang.Object key)
        {
            return null;
        }

        public virtual int size()
        {
            return 0;
        }

        public virtual int isEmpty()
        {
            return size() == 0 ? 1 : 0;
        }

        public virtual Set keySet()
        {
            return null;
        }

        public virtual Collection values()
        {
            return null;
        }

        public virtual Set entrySet()
        {
            return null;
        }

        public virtual void clear()
        {
        }
    }
}
