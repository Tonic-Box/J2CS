namespace java.util
{
    public class Vector : ArrayList
    {
        public Vector(global::java.lang.RawNew r) : base(r) { }

        public void addElement(global::java.lang.Object e) { add(e); }
        public void insertElementAt(global::java.lang.Object e, int index) { add(index, e); }
        public void setElementAt(global::java.lang.Object e, int index) { set(index, e); }
        public void removeElementAt(int index) { remove(index); }
        public int removeElement(global::java.lang.Object o) { return remove(o); }
        public global::java.lang.Object elementAt(int index) { return get(index); }
        public global::java.lang.Object firstElement() { return get(0); }
        public global::java.lang.Object lastElement() { return get(size() - 1); }
        public int capacity() { return size(); }
    }
}
