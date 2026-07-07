import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class VisBorders {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel root = new JPanel(new GridLayout(0, 1, 4, 4));
        root.setPreferredSize(new Dimension(240, 300));
        root.setName("root");
        root.add(cell("titled", BorderFactory.createTitledBorder("Titled")));
        root.add(cell("etched", BorderFactory.createEtchedBorder()));
        root.add(cell("lowered", BorderFactory.createLoweredBevelBorder()));
        root.add(cell("raised", BorderFactory.createRaisedBevelBorder()));
        root.add(cell("line", BorderFactory.createLineBorder(Color.BLUE, 2)));
        return root;
    }

    private static JPanel cell(String name, Border b) {
        JPanel p = new JPanel();
        p.setBorder(b);
        p.setName(name);
        return p;
    }
}
