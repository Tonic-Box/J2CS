namespace javax.swing
{
    /// <summary>Groups radio buttons for mutual exclusion by assigning them a shared Avalonia
    /// RadioButton GroupName.</summary>
    public class ButtonGroup : global::java.lang.Object
    {
        private static int counter;
        private readonly string groupName;

        public ButtonGroup(global::java.lang.RawNew r) : base(r)
        {
            counter++;
            groupName = "j2csGroup" + counter;
        }

        public void __init__V()
        {
        }

        public void add(AbstractButton button)
        {
            if (button is JRadioButton rb && rb.radio != null)
            {
                rb.radio.GroupName = groupName;
            }
        }
    }
}
