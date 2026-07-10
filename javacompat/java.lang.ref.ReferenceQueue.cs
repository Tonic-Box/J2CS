namespace java.lang.@ref
{
    public class ReferenceQueue : global::java.lang.Object
    {
        public ReferenceQueue(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public Reference poll()
        {
            return null;
        }

        public Reference remove()
        {
            global::System.Threading.Thread.Sleep(global::System.Threading.Timeout.Infinite);
            return null;
        }

        public Reference remove(long timeout)
        {
            if (timeout > 0)
            {
                global::System.Threading.Thread.Sleep(
                    (int)global::System.Math.Min(timeout, int.MaxValue));
            }
            return null;
        }
    }
}
