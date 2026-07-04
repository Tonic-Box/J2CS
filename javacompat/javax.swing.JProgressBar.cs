namespace javax.swing
{
    public class JProgressBar : global::java.awt.Component
    {
        private global::Avalonia.Controls.ProgressBar bar;

        public JProgressBar(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            bar = new global::Avalonia.Controls.ProgressBar();
            AvControl = bar;
        }

        public void setIndeterminate(int indeterminate)
        {
            if (bar != null)
            {
                bar.IsIndeterminate = indeterminate != 0;
            }
        }
    }
}
