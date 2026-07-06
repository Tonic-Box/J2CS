namespace javax.swing
{
    /// <summary>
    /// Synchronous SwingWorker: execute() runs doInBackground() then done() inline; publish()
    /// delivers straight to process(). Sufficient for fire-and-forget background tasks; does not
    /// model real off-thread execution.
    /// </summary>
    public class SwingWorker : global::java.lang.Object
    {
        private global::java.lang.Object result;

        public SwingWorker(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public virtual global::java.lang.Object doInBackground()
        {
            return null;
        }

        public virtual void process(global::java.util.List chunks)
        {
        }

        public virtual void done()
        {
        }

        public void publish(global::java.lang.Object[] chunks)
        {
            var list = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            list.__init__V();
            if (chunks != null)
            {
                foreach (var c in chunks)
                {
                    list.add(c);
                }
            }
            process(list);
        }

        public void execute()
        {
            try
            {
                result = doInBackground();
            }
            catch (global::System.Exception)
            {
            }
            done();
        }

        public global::java.lang.Object get()
        {
            return result;
        }

        public int isDone()
        {
            return 1;
        }

        public int isCancelled()
        {
            return 0;
        }

        public int cancel(int mayInterrupt)
        {
            return 0;
        }
    }
}
