namespace java.awt
{
    public class GridBagConstraints : global::java.lang.Object
    {
        public int gridx;
        public int gridy;
        public int gridwidth = 1;
        public int gridheight = 1;
        public double weightx;
        public double weighty;
        public int anchor = 10;
        public int fill;
        public int ipadx;
        public int ipady;
        public global::java.awt.Insets insets;

        public GridBagConstraints(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            gridwidth = 1;
            gridheight = 1;
            anchor = 10;
        }
    }
}
