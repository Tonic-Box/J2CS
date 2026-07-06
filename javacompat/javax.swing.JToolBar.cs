namespace javax.swing
{
    public class JToolBar : global::java.awt.Container
    {
        public JToolBar(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure(global::Avalonia.Layout.Orientation orientation)
        {
            AvPanel = new global::Avalonia.Controls.StackPanel { Orientation = orientation };
            AvControl = AvPanel;
        }

        public void __init__V()
        {
            Ensure(global::Avalonia.Layout.Orientation.Horizontal);
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String name)
        {
            Ensure(global::Avalonia.Layout.Orientation.Horizontal);
        }

        public void __init_I_V(int orientation)
        {
            Ensure(orientation == 1
                ? global::Avalonia.Layout.Orientation.Vertical
                : global::Avalonia.Layout.Orientation.Horizontal);
        }

        public void addSeparator()
        {
            if (AvPanel != null)
            {
                AvPanel.Children.Add(new global::Avalonia.Controls.Separator());
            }
        }

        public void setFloatable(int floatable) { }
        public void setRollover(int rollover) { }
        public void setBorderPainted(int painted) { }
    }
}
