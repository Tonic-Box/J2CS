namespace java.util
{
    public class Arrays : global::java.lang.Object
    {
        private Arrays(global::java.lang.RawNew r) : base(r)
        {
        }

        public static void fill(char[] a, char val)
        {
            if (a != null)
            {
                for (int i = 0; i < a.Length; i++)
                {
                    a[i] = val;
                }
            }
        }
    }
}
