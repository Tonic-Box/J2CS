namespace javax.swing
{
    public class JSlider : global::java.awt.Component
    {
        private global::Avalonia.Controls.Slider slider;

        public JSlider(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (slider == null)
            {
                slider = new global::Avalonia.Controls.Slider
                {
                    Minimum = 0,
                    Maximum = 100,
                    Background = global::java.awt.J2csTheme.MetalGray,
                    Foreground = global::java.awt.J2csTheme.MetalAccent
                };
                AvControl = slider;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_II_V(int min, int max)
        {
            Ensure();
            slider.Minimum = min;
            slider.Maximum = max;
        }

        public void __init_III_V(int min, int max, int value)
        {
            Ensure();
            slider.Minimum = min;
            slider.Maximum = max;
            slider.Value = value;
        }

        public int getValue()
        {
            return slider == null ? 0 : (int)slider.Value;
        }

        public void setValue(int value)
        {
            Ensure();
            slider.Value = value;
        }

        public int getMinimum() { return slider == null ? 0 : (int)slider.Minimum; }
        public int getMaximum() { return slider == null ? 0 : (int)slider.Maximum; }
        public void setMinimum(int min) { Ensure(); slider.Minimum = min; }
        public void setMaximum(int max) { Ensure(); slider.Maximum = max; }
        public void setMajorTickSpacing(int spacing) { }
        public void setMinorTickSpacing(int spacing) { }
        public void setPaintTicks(int paint) { Ensure(); slider.TickFrequency = 1; }
        public void setPaintLabels(int paint) { }

        public void addChangeListener(global::javax.swing.@event.ChangeListener l)
        {
            if (l == null)
            {
                return;
            }
            Ensure();
            slider.PropertyChanged += (s, e) =>
            {
                if (e.Property == global::Avalonia.Controls.Slider.ValueProperty)
                {
                    l.stateChanged(new global::javax.swing.@event.ChangeEvent(global::java.lang.RawNew.I));
                }
            };
        }
    }
}
