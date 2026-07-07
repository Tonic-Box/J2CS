import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

/** Mirrors DemoApp's ConfigurationPanel: BoxLayout Y with a "Configuration" titled border,
 * titled property-editor rows (slider/checkbox), a vertical glue, then a Reset/Export row. */
public class VisConfigPanel {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createTitledBorder("Configuration"));
        root.setPreferredSize(new Dimension(240, 300));
        root.setName("root");

        root.add(sliderEditor("Animation_Speed", 50));
        root.add(Box.createVerticalStrut(5));
        root.add(sliderEditor("Shape_Count", 20));
        root.add(Box.createVerticalStrut(5));
        root.add(checkEditor("Debug_Mode", true));
        root.add(Box.createVerticalGlue());

        JPanel buttons = new JPanel(new FlowLayout());
        JButton reset = new JButton("Reset");
        reset.setName("reset");
        JButton export = new JButton("Export");
        export.setName("export");
        buttons.add(reset);
        buttons.add(export);
        buttons.setName("buttons");
        root.add(buttons);
        return root;
    }

    private static JPanel sliderEditor(String title, int value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        p.add(new JSlider(0, 100, value), BorderLayout.CENTER);
        p.setName("ed_" + title);
        return p;
    }

    private static JPanel checkEditor(String title, boolean selected) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        p.add(new JCheckBox("", selected), BorderLayout.CENTER);
        p.setName("ed_" + title);
        return p;
    }
}
