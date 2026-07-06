namespace javax.swing
{
    public class Timer : global::java.lang.Object
    {
        private readonly global::Avalonia.Threading.DispatcherTimer timer =
            new global::Avalonia.Threading.DispatcherTimer();
        private global::java.awt.@event.ActionListener listener;

        public Timer(global::java.lang.RawNew r) : base(r)
        {
            timer.Tick += (s, e) =>
            {
                listener?.actionPerformed(new global::java.awt.@event.ActionEvent(global::java.lang.RawNew.I));
            };
        }

        public void __init_ILjava_awt_event_ActionListener__V(int delay, global::java.awt.@event.ActionListener l)
        {
            timer.Interval = global::System.TimeSpan.FromMilliseconds(delay <= 0 ? 1 : delay);
            listener = l;
        }

        public void addActionListener(global::java.awt.@event.ActionListener l)
        {
            listener = l;
        }

        public void setDelay(int delay)
        {
            timer.Interval = global::System.TimeSpan.FromMilliseconds(delay <= 0 ? 1 : delay);
        }

        public void setRepeats(int repeats)
        {
        }

        public int isRunning()
        {
            return timer.IsEnabled ? 1 : 0;
        }

        public void start()
        {
            timer.Start();
        }

        public void stop()
        {
            timer.Stop();
        }

        public void restart()
        {
            timer.Stop();
            timer.Start();
        }
    }
}
