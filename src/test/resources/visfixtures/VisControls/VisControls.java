import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class VisControls {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel root = new JPanel(new GridLayout(0, 2, 6, 6));
        root.setPreferredSize(new Dimension(380, 240));
        root.setName("root");

        JLabel label = new JLabel("Name:");
        label.setName("label");
        root.add(label);

        JTextField field = new JTextField(12);
        field.setName("field");
        root.add(field);

        JButton button = new JButton("Submit");
        button.setName("button");
        root.add(button);

        JCheckBox check = new JCheckBox("Enabled", true);
        check.setName("check");
        root.add(check);

        JSlider slider = new JSlider(0, 100, 40);
        slider.setName("slider");
        root.add(slider);

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(60);
        bar.setName("bar");
        root.add(bar);

        JSpinner spinner = new JSpinner();
        spinner.setName("spinner");
        root.add(spinner);

        JList<String> list = new JList<>(new String[]{"Red", "Green", "Blue"});
        list.setName("list");
        root.add(list);

        return root;
    }
}
