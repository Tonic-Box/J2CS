namespace javax.swing
{
    public class Box : global::java.lang.Object
    {
        private Box(global::java.lang.RawNew r) : base(r)
        {
        }

        public static global::java.awt.Component createVerticalStrut(int height)
        {
            var strut = new global::java.awt.Component(global::java.lang.RawNew.I);
            strut.AvControl = new global::Avalonia.Controls.Border { Height = height };
            return strut;
        }
    }
}
