namespace java.lang
{
    public class Runtime : global::java.lang.Object
    {
        private static readonly Runtime INSTANCE = new Runtime(global::java.lang.RawNew.I);

        public Runtime(global::java.lang.RawNew r) : base(r)
        {
        }

        public static Runtime getRuntime()
        {
            return INSTANCE;
        }

        public int availableProcessors()
        {
            return global::System.Environment.ProcessorCount;
        }

        public long maxMemory()
        {
            long max = global::System.GC.GetGCMemoryInfo().TotalAvailableMemoryBytes;
            return max > 0 ? max : 256L * 1024 * 1024;
        }

        public long totalMemory()
        {
            return global::System.GC.GetTotalMemory(false);
        }

        public long freeMemory()
        {
            long free = maxMemory() - totalMemory();
            return free > 0 ? free : 0;
        }

        public void gc()
        {
            global::System.GC.Collect();
        }
    }
}
