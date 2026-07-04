namespace java.util
{
    partial class Objects
    {
        public static int checkIndex(int index, int length)
        {
            if (index < 0 || index >= length)
            {
                throw global::java.lang.JRuntime.Simple(
                        new global::java.lang.IndexOutOfBoundsException(global::java.lang.RawNew.I));
            }
            return index;
        }
    }
}
