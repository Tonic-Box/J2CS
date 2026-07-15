namespace java.util.logging
{
    public class Logger : global::java.lang.Object
    {
        private static readonly global::System.Collections.Generic.Dictionary<string, Logger> registry
            = new global::System.Collections.Generic.Dictionary<string, Logger>();

        private readonly string name;
        // Default threshold mirrors the JDK (INFO); an optional J2CS_LOG_LEVEL env var overrides it
        // (a level name like FINE/ALL/OFF or a raw int) so verbosity can be raised without a rebuild.
        private static readonly int defaultLevelValue = ParseLevelEnv();
        private int levelValue = defaultLevelValue;

        private static int ParseLevelEnv()
        {
            string v = global::System.Environment.GetEnvironmentVariable("J2CS_LOG_LEVEL");
            if (string.IsNullOrEmpty(v))
            {
                return 800;
            }
            switch (v.Trim().ToUpperInvariant())
            {
                case "OFF": return int.MaxValue;
                case "SEVERE": return 1000;
                case "WARNING": return 900;
                case "INFO": return 800;
                case "CONFIG": return 700;
                case "FINE": return 500;
                case "FINER": return 400;
                case "FINEST": return 300;
                case "ALL": return int.MinValue;
                default:
                    return int.TryParse(v.Trim(), out int n) ? n : 800;
            }
        }

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
            Emit(level, msg, null, null);
        }

        public void log(global::java.util.logging.Level level, global::java.lang.String msg, global::java.lang.Object param)
        {
            Emit(level, msg, new global::java.lang.Object[] { param }, null);
        }

        public void log(global::java.util.logging.Level level, global::java.lang.String msg, global::java.lang.Object[] parameters)
        {
            Emit(level, msg, parameters, null);
        }

        public void log(global::java.util.logging.Level level, global::java.lang.String msg, global::java.lang.Throwable thrown)
        {
            Emit(level, msg, null, thrown);
        }

        public void severe(global::java.lang.String msg)
        {
            Emit(global::java.util.logging.Level.SEVERE, msg, null, null);
        }

        public void warning(global::java.lang.String msg)
        {
            Emit(global::java.util.logging.Level.WARNING, msg, null, null);
        }

        public void info(global::java.lang.String msg)
        {
            Emit(global::java.util.logging.Level.INFO, msg, null, null);
        }

        public void config(global::java.lang.String msg)
        {
            Emit(global::java.util.logging.Level.CONFIG, msg, null, null);
        }

        public void fine(global::java.lang.String msg)
        {
            Emit(global::java.util.logging.Level.FINE, msg, null, null);
        }

        public void finer(global::java.lang.String msg)
        {
            Emit(global::java.util.logging.Level.FINER, msg, null, null);
        }

        public void finest(global::java.lang.String msg)
        {
            Emit(global::java.util.logging.Level.FINEST, msg, null, null);
        }

        // The stock JDK setup logs INFO and above to stderr via a SimpleFormatter; mirror that so a
        // silent logger no longer swallows engine warnings and errors. Level filtering honours the
        // logger's own level (default INFO).
        private void Emit(global::java.util.logging.Level level, global::java.lang.String msg,
            global::java.lang.Object[] parameters, global::java.lang.Throwable thrown)
        {
            if (level == null || isLoggable(level) == 0)
            {
                return;
            }
            string text = msg == null ? "" : msg.Value;
            if (parameters != null && parameters.Length > 0)
            {
                text = Format(text, parameters);
            }
            global::java.lang.String lvlName = level.getName();
            global::System.Console.Error.WriteLine((lvlName == null ? "" : lvlName.Value) + ": " + text);
            if (thrown != null)
            {
                global::java.lang.String tStr = ((global::java.lang.Object) thrown).toString();
                global::System.Console.Error.WriteLine(tStr == null ? "" : tStr.Value);
            }
        }

        // java.text.MessageFormat-style {N} substitution - enough for the engine's log messages.
        private static string Format(string pattern, global::java.lang.Object[] parameters)
        {
            var sb = new global::System.Text.StringBuilder(pattern.Length);
            for (int i = 0; i < pattern.Length; i++)
            {
                char c = pattern[i];
                if (c == '{' && i + 1 < pattern.Length && char.IsDigit(pattern[i + 1]))
                {
                    int j = i + 1;
                    int idx = 0;
                    while (j < pattern.Length && char.IsDigit(pattern[j]))
                    {
                        idx = idx * 10 + (pattern[j] - '0');
                        j++;
                    }
                    if (j < pattern.Length && pattern[j] == '}')
                    {
                        object arg = idx < parameters.Length ? parameters[idx] : null;
                        sb.Append(ArgToString(arg));
                        i = j;
                        continue;
                    }
                }
                sb.Append(c);
            }
            return sb.ToString();
        }

        private static string ArgToString(object arg)
        {
            if (arg == null)
            {
                return "null";
            }
            if (arg is global::java.lang.Object jo)
            {
                global::java.lang.String s = jo.toString();
                return s == null ? "null" : s.Value;
            }
            return arg.ToString();
        }
    }
}
