namespace java.util.logging
{
    public class Logger : global::java.lang.Object
    {
        private static readonly global::System.Collections.Generic.Dictionary<string, Logger> registry
            = new global::System.Collections.Generic.Dictionary<string, Logger>();

        private readonly string name;
        private int levelValue = 800;

        public Logger(global::java.lang.RawNew r) : base(r)
        {
            this.name = "";
        }

        private Logger(string name) : base(global::java.lang.RawNew.I)
        {
            this.name = name;
        }

        public static Logger getLogger(global::java.lang.String name)
        {
            string key = name == null ? "" : name.Value;
            lock (registry)
            {
                if (!registry.TryGetValue(key, out Logger existing))
                {
                    existing = new Logger(key);
                    registry[key] = existing;
                }
                return existing;
            }
        }

        public static Logger getLogger(global::java.lang.String name, global::java.lang.String resourceBundleName)
        {
            return getLogger(name);
        }

        public global::java.lang.String getName()
        {
            return global::java.lang.String.Wrap(name);
        }

        public void setLevel(global::java.util.logging.Level newLevel)
        {
            if (newLevel != null)
            {
                this.levelValue = newLevel.intValue();
            }
        }

        public global::java.util.logging.Level getLevel()
        {
            return global::java.util.logging.Level.INFO;
        }

        public int isLoggable(global::java.util.logging.Level level)
        {
            if (level == null)
            {
                return 0;
            }
            return level.intValue() >= this.levelValue ? 1 : 0;
        }

        public void log(global::java.util.logging.Level level, global::java.lang.String msg)
        {
        }

        public void log(global::java.util.logging.Level level, global::java.lang.String msg, global::java.lang.Object param)
        {
        }

        public void log(global::java.util.logging.Level level, global::java.lang.String msg, global::java.lang.Object[] parameters)
        {
        }

        public void log(global::java.util.logging.Level level, global::java.lang.String msg, global::java.lang.Throwable thrown)
        {
        }

        public void severe(global::java.lang.String msg)
        {
        }

        public void warning(global::java.lang.String msg)
        {
        }

        public void info(global::java.lang.String msg)
        {
        }

        public void config(global::java.lang.String msg)
        {
        }

        public void fine(global::java.lang.String msg)
        {
        }

        public void finer(global::java.lang.String msg)
        {
        }

        public void finest(global::java.lang.String msg)
        {
        }
    }
}
