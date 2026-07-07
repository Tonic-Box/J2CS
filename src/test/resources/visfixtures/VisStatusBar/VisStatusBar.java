import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/** Mirrors DemoApp's StatusBar: BorderLayout + LoweredBevelBorder, WEST status / CENTER time /
 * EAST memory label + progress bar. Fixed strings so output is deterministic. */
public class VisStatusBar {
    public static void main(String[] args) {
    }

    public static Component build() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBorder(BorderFactory.createLoweredBevelBorder());
        bar.setPreferredSize(new Dimension(400, 26));
        bar.setName("bar");

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        JLabel status = new JLabel("Ready");
        status.setName("status");
        left.add(status);
        left.setName("left");

        JLabel time = new JLabel("12:34:56");
        time.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        time.setName("time");

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 2));
        JLabel mem = new JLabel("128 MB / 512 MB");
        mem.setName("mem");
        JProgressBar memBar = new JProgressBar(0, 100);
        memBar.setValue(45);
        memBar.setPreferredSize(new Dimension(100, 15));
        memBar.setName("memBar");
        right.add(mem);
        right.add(memBar);
        right.setName("right");

        bar.add(left, BorderLayout.WEST);
        bar.add(time, BorderLayout.CENTER);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }
}
