namespace java.lang
{
    public sealed class Thread_S_State : global::java.lang.Object
    {
        public static readonly Thread_S_State NEW = new Thread_S_State("NEW");
        public static readonly Thread_S_State RUNNABLE = new Thread_S_State("RUNNABLE");
        public static readonly Thread_S_State BLOCKED = new Thread_S_State("BLOCKED");
        public static readonly Thread_S_State WAITING = new Thread_S_State("WAITING");
        public static readonly Thread_S_State TIMED_WAITING = new Thread_S_State("TIMED_WAITING");
        public static readonly Thread_S_State TERMINATED = new Thread_S_State("TERMINATED");

        internal readonly string name;

        private Thread_S_State(string n) : base(global::java.lang.RawNew.I) { name = n; }
        public Thread_S_State(global::java.lang.RawNew r) : base(r) { name = ""; }

        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(name); }
    }
}
