import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiModal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Owner");
                frame.setSize(200, 100);
                final JDialog dialog = new JDialog(frame, "Modal", true);
                dialog.setSize(200, 100);
                JButton close = new JButton("Close");
                close.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("closing");
                        dialog.setVisible(false);
                    }
                });
                dialog.add(close);
                System.out.println("before");
                dialog.setVisible(true);
                System.out.println("after");
                System.exit(0);
            }
        });
    }
}
