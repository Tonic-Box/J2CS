namespace java.util.logging
{
    public class Level : global::java.lang.Object
    {
        public static readonly Level OFF = new Level("OFF", int.MaxValue);
        public static readonly Level SEVERE = new Level("SEVERE", 1000);
        public static readonly Level WARNING = new Level("WARNING", 900);
        public static readonly Level INFO = new Level("INFO", 800);
        public static readonly Level CONFIG = new Level("CONFIG", 700);
        public static readonly Level FINE = new Level("FINE", 500);
        public static readonly Level FINER = new Level("FINER", 400);
        public static readonly Level FINEST = new Level("FINEST", 300);
        public static readonly Level ALL = new Level("ALL", int.MinValue);

        private readonly string name;
        private readonly int value;

        public Level(global::java.lang.RawNew r) : base(r)
        {
            this.name = "";
            this.value = 0;
        }

        private Level(string name, int value) : base(global::java.lang.RawNew.I)
        {
            this.name = name;
            this.value = value;
        }

        public int intValue()
        {
            return value;
        }

        public global::java.lang.String getName()
        {
            return global::java.lang.String.Wrap(name);
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(name);
        }
    }
}
