namespace javax.swing
{
    public class JPasswordField : JTextField
    {
        public JPasswordField(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_I_V(int columns)
        {
            J2csBuildField(columns);
            field.PasswordChar = '*';
        }

        public char[] getPassword()
        {
            string text = field == null || field.Text == null ? "" : field.Text;
            return text.ToCharArray();
        }

        public void setEchoChar(char echo)
        {
            if (field != null)
            {
                field.PasswordChar = echo;
            }
        }

        public void addKeyListener(global::java.awt.@event.KeyListener l)
        {
            if (field != null && l != null)
            {
                field.KeyDown += (sender, e) =>
                {
                    var ke = new global::java.awt.@event.KeyEvent(global::java.lang.RawNew.I);
                    ke.KeyCode = e.Key == global::Avalonia.Input.Key.Enter ? 10 : (int)e.Key;
                    l.keyPressed(ke);
                };
            }
        }
    }
}
