namespace javax.swing.border
{
    public class TitledBorder : Border
    {
        internal global::java.lang.String Title;

        public TitledBorder(global::java.lang.RawNew r) : base(r)
        {
        }

        public void setTitle(global::java.lang.String title) { Title = title; }
        public global::java.lang.String getTitle() { return Title; }
    }
}
