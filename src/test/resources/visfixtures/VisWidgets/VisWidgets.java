import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class VisWidgets {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel root = new JPanel(new GridLayout(0, 1, 4, 4));
        root.setPreferredSize(new Dimension(260, 320));
        root.setName("root");

        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Alpha");
        combo.addItem("Beta");
        combo.addItem("Gamma");
        combo.setSelectedIndex(1);
        combo.setName("combo");
        root.add(combo);

        JPanel radios = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        JRadioButton r1 = new JRadioButton("One", true);
        JRadioButton r2 = new JRadioButton("Two");
        r1.setName("r1");
        r2.setName("r2");
        ButtonGroup group = new ButtonGroup();
        group.add(r1);
        group.add(r2);
        radios.add(r1);
        radios.add(r2);
        radios.setName("radios");
        root.add(radios);

        JTextArea area = new JTextArea("line1\nline2", 3, 16);
        area.setName("area");
        root.add(area);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("First", new JPanel());
        tabs.addTab("Second", new JPanel());
        tabs.setName("tabs");
        root.add(tabs);

        return root;
    }
}
