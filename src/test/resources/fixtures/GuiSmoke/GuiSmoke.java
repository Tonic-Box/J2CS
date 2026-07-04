import javax.swing.JFrame;

public class GuiSmoke {
    public static void main(String[] args) {
        System.out.println("starting");
        JFrame frame = new JFrame("Smoke Test");
        frame.setSize(320, 240);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        System.out.println("shown");
    }
}
