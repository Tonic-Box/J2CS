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

        public long totalMemory()
        {
            // Java: heap currently committed. Map to the committed managed heap, but never below the
            // live set (GetTotalMemory) so used = total - free stays non-negative.
            long committed = global::System.GC.GetGCMemoryInfo().TotalCommittedBytes;
            long used = global::System.GC.GetTotalMemory(false);
            return committed > used ? committed : used;
        }

        public long freeMemory()
        {
            // Java: unused portion of the committed heap = totalMemory - used.
            long free = totalMemory() - global::System.GC.GetTotalMemory(false);
            return free > 0 ? free : 0;
        }

        public long maxMemory()
        {
            long max = global::System.GC.GetGCMemoryInfo().TotalAvailableMemoryBytes;
            long total = totalMemory();
            if (max < total)
            {
                max = total;
            }
            return max > 0 ? max : 256L * 1024 * 1024;
        }

        public void gc()
        {
            global::System.GC.Collect();
        }
    }
}
