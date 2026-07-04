namespace java.lang
{
    public class System : Object
    {
        public static readonly global::java.io.PrintStream @out =
                new global::java.io.PrintStream(global::System.Console.Out);

        public static readonly global::java.io.PrintStream err =
                new global::java.io.PrintStream(global::System.Console.Error);

        private System() : base(RawNew.I)
        {
        }

        public static long nanoTime()
        {
            return (long)(global::System.Diagnostics.Stopwatch.GetTimestamp()
                    * (1_000_000_000.0 / global::System.Diagnostics.Stopwatch.Frequency));
        }

        public static long currentTimeMillis()
        {
            return global::System.DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
        }

        public static void exit(int status)
        {
            global::System.Environment.Exit(status);
        }

        public static void arraycopy(global::System.Array src, int srcPos,
                global::System.Array dest, int destPos, int length)
        {
            if (src == null || dest == null)
            {
                throw JRuntime.Simple(new NullPointerException(RawNew.I));
            }
            if (srcPos < 0 || destPos < 0 || length < 0
                    || (long)srcPos + length > src.Length
                    || (long)destPos + length > dest.Length)
            {
                throw JRuntime.Simple(new ArrayIndexOutOfBoundsException(RawNew.I));
            }
            try
            {
                global::System.Array.Copy(src, srcPos, dest, destPos, length);
            }
            catch (global::System.ArrayTypeMismatchException)
            {
                throw JRuntime.Simple(new ArrayStoreException(RawNew.I));
            }
            catch (global::System.InvalidCastException)
            {
                throw JRuntime.Simple(new ArrayStoreException(RawNew.I));
            }
        }
    }
}
