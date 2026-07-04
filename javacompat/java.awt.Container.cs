namespace java.awt
{
    public class Container : Component
    {
        internal global::Avalonia.Controls.Panel AvPanel;
        private LayoutManager layout;

        public Container(global::java.lang.RawNew r) : base(r)
        {
        }

        private void EnsurePanel()
        {
            if (AvPanel == null)
            {
                AvPanel = new global::Avalonia.Controls.StackPanel();
                AvControl = AvPanel;
            }
        }

        public void setLayout(LayoutManager lm)
        {
            layout = lm;
            AvPanel = lm != null
                    ? lm.J2csCreatePanel()
                    : new global::Avalonia.Controls.StackPanel();
            AvControl = AvPanel;
        }

        public global::java.awt.Component add(global::java.awt.Component comp)
        {
            EnsurePanel();
            if (comp != null && comp.AvControl != null)
            {
                if (layout != null)
                {
                    layout.J2csAdd(AvPanel, comp.AvControl, null);
                }
                else
                {
                    AvPanel.Children.Add(comp.AvControl);
                }
            }
            return comp;
        }

        public void setBorder(global::javax.swing.border.Border border)
        {
            EnsurePanel();
            if (border != null)
            {
                AvPanel.Margin = new global::Avalonia.Thickness(
                        border.Left, border.Top, border.Right, border.Bottom);
            }
        }

        public void add(global::java.awt.Component comp, global::java.lang.Object constraint)
        {
            EnsurePanel();
            if (comp != null && comp.AvControl != null)
            {
                if (layout != null)
                {
                    layout.J2csAdd(AvPanel, comp.AvControl, constraint);
                }
                else
                {
                    AvPanel.Children.Add(comp.AvControl);
                }
            }
        }
    }
}
