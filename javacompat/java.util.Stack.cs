namespace java.util
{
    public class Stack : Vector
    {
        public Stack(global::java.lang.RawNew r) : base(r) { }

        public global::java.lang.Object push(global::java.lang.Object item) { add(item); return item; }
        public global::java.lang.Object pop() { return remove(size() - 1); }
        public global::java.lang.Object peek() { return get(size() - 1); }
        public int empty() { return isEmpty(); }

        public int search(global::java.lang.Object o)
        {
            for (int i = size() - 1; i >= 0; i--)
            {
                if (JCollections.Eq(o, get(i))) { return size() - i; }
            }
            return -1;
        }
    }
}
