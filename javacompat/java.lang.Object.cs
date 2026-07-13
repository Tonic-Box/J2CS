namespace java.lang
{
    public class Object
    {
        public Object()
        {
        }

        public Object(RawNew r)
        {
        }

        public void __init__V()
        {
        }

        public virtual int hashCode()
        {
            return global::System.Runtime.CompilerServices.RuntimeHelpers.GetHashCode(this);
        }

        public virtual global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(GetType().FullName + "@" + hashCode().ToString("x"));
        }

        public virtual int equals(global::java.lang.Object o)
        {
            return ReferenceEquals(this, o) ? 1 : 0;
        }

        public virtual global::java.lang.Class getClass()
        {
            return global::java.lang.Class.forType(GetType());
        }

        public virtual global::java.lang.Object clone()
        {
            return (global::java.lang.Object)MemberwiseClone();
        }

        // Java's monitor wait/notify map onto Monitor.Wait/Pulse/PulseAll (all require the calling
        // thread to hold the object's monitor, which the enclosing synchronized guarantees). wait(0)
        // and wait(0, 0) block indefinitely; a positive millisecond timeout bounds the wait.
        public void notify()
        {
            global::System.Threading.Monitor.Pulse(this);
        }

        public void notifyAll()
        {
            global::System.Threading.Monitor.PulseAll(this);
        }

        public void wait()
        {
            global::System.Threading.Monitor.Wait(this);
        }

        public void wait(long timeout)
        {
            if (timeout <= 0L)
            {
                global::System.Threading.Monitor.Wait(this);
            }
            else
            {
                global::System.Threading.Monitor.Wait(this, unchecked((int)timeout));
            }
        }

        public void wait(long timeout, int nanos)
        {
            wait(timeout);
        }
    }
}
