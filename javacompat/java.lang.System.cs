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
    }
}
