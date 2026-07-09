import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

public class VisLogin {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setName("root");

        JLabel title = new JLabel("Task Manager Authentication");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setName("title");
        root.add(title);

        JLabel status = new JLabel("Please enter password to continue");
        status.setName("status");
        root.add(status);

        JPanel inputPanel = new JPanel();
        inputPanel.setName("inputPanel");
        JLabel pwLabel = new JLabel("Password: ");
        pwLabel.setName("pwLabel");
        inputPanel.add(pwLabel);
        JPasswordField password = new JPasswordField(20);
        password.setName("password");
        inputPanel.add(password);
        root.add(inputPanel);

        JProgressBar progress = new JProgressBar(0, 100);
        progress.setPreferredSize(new Dimension(300, 20));
        progress.setName("progress");
        root.add(progress);

        return root;
    }
}
