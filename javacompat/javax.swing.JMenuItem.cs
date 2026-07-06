namespace javax.swing
{
    public class JMenuItem : global::java.awt.Component
    {
        internal global::Avalonia.Controls.MenuItem AvMenuItem;

        public JMenuItem(global::java.lang.RawNew r) : base(r)
        {
            AvMenuItem = new global::Avalonia.Controls.MenuItem();
            AvControl = AvMenuItem;
        }

        public void __init__V() { }

        public void __init_Ljava_lang_String__V(global::java.lang.String text)
        {
            AvMenuItem.Header = global::java.lang.JRuntime.Cs(text);
        }

        public void setText(global::java.lang.String text)
        {
            AvMenuItem.Header = global::java.lang.JRuntime.Cs(text);
        }

        public void addActionListener(global::java.awt.@event.ActionListener l)
        {
            if (l != null)
            {
                AvMenuItem.Click += (sender, e) =>
                        l.actionPerformed(new global::java.awt.@event.ActionEvent(global::java.lang.RawNew.I));
            }
        }

        public void setAccelerator(global::javax.swing.KeyStroke keyStroke) { }
        public void setMnemonic(int mnemonic) { }
        public void setActionCommand(global::java.lang.String command) { }
        public void setToolTipText(global::java.lang.String text)
        {
            global::Avalonia.Controls.ToolTip.SetTip(AvMenuItem, global::java.lang.JRuntime.Cs(text));
        }
    }
}
