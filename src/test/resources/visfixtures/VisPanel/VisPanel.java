import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Visual-fidelity scenario. build() returns a name-tagged Swing tree; the JVM reference harness
 * and the Avalonia candidate harness each render it at the same size and dump geometry/colors.
 */
public class VisPanel {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel root = new JPanel(new BorderLayout(5, 5));
        root.setPreferredSize(new Dimension(320, 220));
        root.setName("root");

        JLabel title = new JLabel("Header");
        title.setOpaque(true);
        title.setBackground(Color.LIGHT_GRAY);
        title.setName("title");
        root.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createTitledBorder("Options"));
        center.setName("center");
        root.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        JButton ok = new JButton("OK");
        ok.setName("ok");
        JButton cancel = new JButton("Cancel");
        cancel.setName("cancel");
        buttons.add(ok);
        buttons.add(cancel);
        buttons.setName("buttons");
        root.add(buttons, BorderLayout.SOUTH);

        return root;
    }
}
