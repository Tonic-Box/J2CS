namespace java.awt.@event
{
    public class ActionEvent : global::java.lang.Object
    {
        internal global::java.lang.Object Source;
        internal global::java.lang.String Command;

        public ActionEvent(global::java.lang.RawNew r) : base(r)
        {
        }

        public global::java.lang.Object getSource()
        {
            return Source;
        }

        public global::java.lang.String getActionCommand()
        {
            return Command;
        }
    }
}
