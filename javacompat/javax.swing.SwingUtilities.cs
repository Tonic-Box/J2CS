namespace javax.swing
{
    public class SwingUtilities : global::java.lang.Object
    {
        private SwingUtilities(global::java.lang.RawNew r) : base(r)
        {
        }

        public static void invokeLater(global::java.lang.Runnable r)
        {
            if (r != null)
            {
                global::Avalonia.Threading.Dispatcher.UIThread.Post(() => r.run());
            }
        }
    }
}
