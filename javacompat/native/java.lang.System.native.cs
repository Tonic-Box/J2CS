namespace java.lang
{
    partial class System
    {
        static System()
        {
            @out = new global::java.io.PrintStream(global::System.Console.Out);
            err = new global::java.io.PrintStream(global::System.Console.Error);
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

        public static int identityHashCode(global::java.lang.Object x)
        {
            return x == null ? 0 : global::System.Runtime.CompilerServices.RuntimeHelpers.GetHashCode(x);
        }

        public static long currentTimeMillis()
        {
            return global::System.DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
        }

        public static long nanoTime()
        {
            return global::System.Diagnostics.Stopwatch.GetTimestamp()
                    * (1000000000L / global::System.Diagnostics.Stopwatch.Frequency);
        }
    }
}
