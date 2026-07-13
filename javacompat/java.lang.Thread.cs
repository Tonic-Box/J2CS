namespace java.lang
{
    public class Thread : Object
    {
        [global::System.ThreadStatic]
        private static Thread self;
        private static readonly Thread mainThread = Named("main");

        private global::System.Threading.Thread clr;
        private Runnable target;
        private volatile bool daemon;
        private volatile bool interruptedFlag;
        private String threadName;
        private int priority = 5;

        public Thread(RawNew r) : base(r)
        {
        }

        private static Thread Named(string name)
        {
            Thread t = new Thread(RawNew.I);
            t.threadName = global::java.lang.String.Wrap(name);
            return t;
        }

        public void __init__V()
        {
        }

        public void __init_Ljava_lang_Runnable__V(Runnable r)
        {
            this.target = r;
        }

        public void __init_Ljava_lang_Runnable_Ljava_lang_String__V(Runnable r, String name)
        {
            this.target = r;
            this.threadName = name;
        }

        public void __init_Ljava_lang_String__V(String name)
        {
            this.threadName = name;
        }

        public void start()
        {
            clr = new global::System.Threading.Thread(() =>
            {
                self = this;
                try
                {
                    run();
                }
                catch (global::System.Threading.ThreadInterruptedException)
                {
                    interruptedFlag = true;
                }
            });
            clr.IsBackground = daemon;
            if (threadName != null)
            {
                clr.Name = threadName.Value;
            }
            clr.Start();
        }

        public virtual void run()
        {
            if (target != null)
            {
                target.run();
            }
        }

        public global::java.lang.ClassLoader getContextClassLoader()
        {
            return global::java.lang.ClassLoader.SystemClassLoader;
        }

        private global::java.lang.Thread_S_UncaughtExceptionHandler uncaughtHandler;

        public void setUncaughtExceptionHandler(global::java.lang.Thread_S_UncaughtExceptionHandler h)
        {
            uncaughtHandler = h;
        }

        public global::java.lang.Thread_S_UncaughtExceptionHandler getUncaughtExceptionHandler()
        {
            return uncaughtHandler;
        }

        public void setContextClassLoader(global::java.lang.ClassLoader cl)
        {
        }

        public void join()
        {
            if (clr != null)
            {
                clr.Join();
            }
        }

        public void join(long millis)
        {
            if (clr != null)
            {
                clr.Join((int)(millis > int.MaxValue ? int.MaxValue : millis));
            }
        }

        public int isAlive()
        {
            return clr != null && clr.IsAlive ? 1 : 0;
        }

        // Priority is an advisory scheduling hint; store it so getPriority round-trips without imposing
        // a lossy mapping onto the CLR thread scheduler.
        public void setPriority(int newPriority)
        {
            priority = newPriority;
        }

        public int getPriority()
        {
            return priority;
        }

        public void setDaemon(int on)
        {
            daemon = on != 0;
        }

        public int isDaemon()
        {
            return daemon ? 1 : 0;
        }

        public void setName(String name)
        {
            threadName = name;
            if (clr != null && name != null)
            {
                clr.Name = name.Value;
            }
        }

        public String getName()
        {
            return threadName ?? global::java.lang.String.Wrap("Thread");
        }

        public void interrupt()
        {
            interruptedFlag = true;
            if (clr != null)
            {
                try
                {
                    clr.Interrupt();
                }
                catch (global::System.Exception)
                {
                }
            }
        }

        public int isInterrupted()
        {
            return interruptedFlag ? 1 : 0;
        }

        public static int interrupted()
        {
            Thread t = currentThread();
            int was = t.interruptedFlag ? 1 : 0;
            t.interruptedFlag = false;
            return was;
        }

        public static Thread currentThread()
        {
            return self ?? mainThread;
        }

        public static void sleep(long millis)
        {
            if (millis > 0)
            {
                global::System.Threading.Thread.Sleep((int)(millis > int.MaxValue ? int.MaxValue : millis));
            }
        }
    }
}
