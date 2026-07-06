namespace javax.swing
{
    public class JSpinner : global::java.awt.Component
    {
        private global::Avalonia.Controls.NumericUpDown spinner;

        public JSpinner(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (spinner == null)
            {
                spinner = new global::Avalonia.Controls.NumericUpDown { Increment = 1 };
                AvControl = spinner;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_Ljavax_swing_SpinnerModel__V(SpinnerModel model)
        {
            Ensure();
            if (model != null)
            {
                spinner.Minimum = (decimal)model.Min;
                spinner.Maximum = (decimal)model.Max;
                spinner.Increment = (decimal)model.Step;
                spinner.Value = (decimal)model.Value;
            }
        }

        public global::java.lang.Object getValue()
        {
            int v = spinner == null || spinner.Value == null ? 0 : (int)spinner.Value.Value;
            return global::java.lang.Integer.valueOf(v);
        }

        public void setValue(global::java.lang.Object value)
        {
            Ensure();
            if (value is global::java.lang.Number n)
            {
                spinner.Value = n.intValue();
            }
        }

        public void addChangeListener(global::javax.swing.@event.ChangeListener l)
        {
            if (l == null)
            {
                return;
            }
            Ensure();
            spinner.ValueChanged += (s, e) =>
                    l.stateChanged(new global::javax.swing.@event.ChangeEvent(global::java.lang.RawNew.I));
        }
    }
}
