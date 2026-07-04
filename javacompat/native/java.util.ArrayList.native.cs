namespace java.util
{
    partial class ArrayList
    {
        private global::java.lang.Object[] grow()
        {
            int oldCap = elementData.Length;
            int newCap = oldCap > 0 ? oldCap + (oldCap >> 1) : 10;
            var next = new global::java.lang.Object[newCap];
            global::System.Array.Copy(elementData, next, oldCap);
            elementData = next;
            return next;
        }
    }
}
