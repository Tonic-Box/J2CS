namespace javax.swing
{
    public class JTabbedPane : global::java.awt.Component
    {
        private global::Avalonia.Controls.TabControl tabs;

        public JTabbedPane(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (tabs == null)
            {
                tabs = new global::Avalonia.Controls.TabControl
                {
                    Foreground = global::java.awt.J2csTheme.MetalText
                };
                AvControl = tabs;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_I_V(int tabPlacement)
        {
            Ensure();
        }

        public void addTab(global::java.lang.String title, global::java.awt.Component component)
        {
            Ensure();
            tabs.Items.Add(new global::Avalonia.Controls.TabItem
            {
                Header = global::java.lang.JRuntime.Cs(title),
                Content = component == null ? null : component.AvControl
            });
            if (tabs.SelectedIndex < 0) { tabs.SelectedIndex = 0; }
        }

        public void add(global::java.lang.String title, global::java.awt.Component component)
        {
            addTab(title, component);
        }

        public int getSelectedIndex()
        {
            return tabs == null ? -1 : tabs.SelectedIndex;
        }

        public void setSelectedIndex(int index)
        {
            Ensure();
            tabs.SelectedIndex = index;
        }

        public int getTabCount()
        {
            return tabs == null ? 0 : tabs.Items.Count;
        }
    }
}
