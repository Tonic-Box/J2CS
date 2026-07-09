import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VisTaskList {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel root = new JPanel(new BorderLayout());
        root.setPreferredSize(new Dimension(300, 160));
        root.setName("root");

        JLabel header = new JLabel("Tasks");
        header.setName("header");
        root.add(header, BorderLayout.NORTH);

        JList<String> list = new JList<>(new String[]{"Alpha", "Beta", "Gamma", "Delta"});
        list.setName("list");
        root.add(new JScrollPane(list), BorderLayout.CENTER);

        return root;
    }
}
