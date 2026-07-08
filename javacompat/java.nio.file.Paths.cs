namespace java.nio.file
{
    public sealed class Paths : global::java.lang.Object
    {
        public Paths(global::java.lang.RawNew r) : base(r)
        {
        }

        public static Path get(global::java.lang.String first, global::java.lang.String[] more)
        {
            string p = Path.Normalize(first == null ? "" : first.Value);
            if (more != null)
            {
                foreach (var seg in more)
                {
                    if (seg != null && seg.Value.Length > 0)
                    {
                        p = global::System.IO.Path.Combine(p, Path.Normalize(seg.Value));
                    }
                }
            }
            return new Path(p);
        }
    }
}
