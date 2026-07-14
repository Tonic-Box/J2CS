namespace java.util
{
    public class LinkedList : ArrayList
    {
        public LinkedList(global::java.lang.RawNew r) : base(r)
        {
        }

        public void addFirst(global::java.lang.Object e)
        {
            add(0, e);
        }

        public void addLast(global::java.lang.Object e)
        {
            add(e);
        }

        public global::java.lang.Object getFirst()
        {
            return get(0);
        }

        public global::java.lang.Object getLast()
        {
            return get(size() - 1);
        }

        public global::java.lang.Object removeFirst()
        {
            return remove(0);
        }

        public global::java.lang.Object removeLast()
        {
            return remove(size() - 1);
        }
    }
}
