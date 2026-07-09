namespace java.math
{
    public sealed class RoundingMode : global::java.lang.Object
    {
        public static readonly RoundingMode UP = new RoundingMode("UP");
        public static readonly RoundingMode DOWN = new RoundingMode("DOWN");
        public static readonly RoundingMode CEILING = new RoundingMode("CEILING");
        public static readonly RoundingMode FLOOR = new RoundingMode("FLOOR");
        public static readonly RoundingMode HALF_UP = new RoundingMode("HALF_UP");
        public static readonly RoundingMode HALF_DOWN = new RoundingMode("HALF_DOWN");
        public static readonly RoundingMode HALF_EVEN = new RoundingMode("HALF_EVEN");
        public static readonly RoundingMode UNNECESSARY = new RoundingMode("UNNECESSARY");

        internal readonly string name;

        private RoundingMode(string n) : base(global::java.lang.RawNew.I) { name = n; }
        public RoundingMode(global::java.lang.RawNew r) : base(r) { name = ""; }

        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(name); }
    }
}
