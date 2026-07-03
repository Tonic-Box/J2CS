namespace java.util
{
    public class HashSet : global::java.lang.Object, Set
    {
        private static readonly global::java.lang.Object Present = new global::java.lang.Object(global::java.lang.RawNew.I);

        private readonly HashMap map = new HashMap(global::java.lang.RawNew.I);

        public HashSet(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int initialCapacity)
        {
            map.__init_I_V(initialCapacity);
        }

        public void __init_Ljava_util_Collection__V(Collection c)
        {
            Iterator it = c.iterator();
            while (it.hasNext() != 0)
            {
                map.put(it.next(), Present);
            }
        }

        public int add(global::java.lang.Object e)
        {
            return map.put(e, Present) == null ? 1 : 0;
        }

        public int contains(global::java.lang.Object o)
        {
            return map.containsKey(o);
        }

        public int remove(global::java.lang.Object o)
        {
            return map.remove(o) != null ? 1 : 0;
        }

        public int size()
        {
            return map.size();
        }

        public int isEmpty()
        {
            return map.isEmpty();
        }

        public Iterator iterator()
        {
            return map.keySet().iterator();
        }

        public override global::java.lang.String toString()
        {
            return ((global::java.lang.Object)map.keySet()).toString();
        }
    }
}
