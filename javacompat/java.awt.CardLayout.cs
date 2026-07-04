namespace java.awt
{
    public class CardLayout : global::java.lang.Object, LayoutManager
    {
        public CardLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_II_V(int hgap, int vgap)
        {
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            return new global::java.awt.J2csCardPanel();
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            child.HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Stretch;
            child.VerticalAlignment = global::Avalonia.Layout.VerticalAlignment.Stretch;
            panel.Children.Add(child);
            if (panel is global::java.awt.J2csCardPanel card)
            {
                card.AddCard(constraint == null ? "" : global::java.lang.JRuntime.Str(constraint), child);
            }
        }

        private static global::java.awt.J2csCardPanel Of(global::java.awt.Container parent)
        {
            return parent == null ? null : parent.AvPanel as global::java.awt.J2csCardPanel;
        }

        public void show(global::java.awt.Container parent, global::java.lang.String name)
        {
            Of(parent)?.ShowCard(name == null ? "" : name.Value);
        }

        public void first(global::java.awt.Container parent)
        {
            Of(parent)?.Edge(false);
        }

        public void last(global::java.awt.Container parent)
        {
            Of(parent)?.Edge(true);
        }

        public void next(global::java.awt.Container parent)
        {
            Of(parent)?.Step(1);
        }

        public void previous(global::java.awt.Container parent)
        {
            Of(parent)?.Step(-1);
        }
    }
}
