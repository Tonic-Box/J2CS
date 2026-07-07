package com.tonic.j2cs.visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * JVM-side reference capture for the visual-fidelity loop. Forces Metal L&F, builds the named
 * scenario, renders its content pane offscreen to ref.png, and dumps per-named-component geometry
 * (relative to the content root), colors, opacity, and font to ref.txt. Prints SIZE=WxH so the
 * Avalonia candidate can render at the same size.
 *
 * args: &lt;scenarioClass&gt; &lt;outDir&gt;
 */
public final class VisualRef {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        String scenario = args[0];
        File outDir = new File(args[1]);
        outDir.mkdirs();

        Class<?> cls = Class.forName(scenario);
        Method build = cls.getMethod("build");
        Component comp = (Component) build.invoke(null);

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        Container content = frame.getContentPane();
        content.add(comp);
        frame.pack();
        Dimension size = content.getSize();

        BufferedImage img = new BufferedImage(
                Math.max(1, size.width), Math.max(1, size.height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        content.printAll(g);
        g.dispose();
        ImageIO.write(img, "png", new File(outDir, "ref.png"));

        try (PrintWriter out = new PrintWriter(new File(outDir, "ref.txt"))) {
            walk(comp, content, out);
        }

        System.out.println("SIZE=" + size.width + "x" + size.height);
        frame.dispose();
        System.exit(0);
    }

    private static void walk(Component c, Component root, PrintWriter out) {
        String name = c.getName();
        if (name != null && !name.startsWith("null") && !name.contains(".")) {
            Point p = SwingUtilities.convertPoint(c, 0, 0, root);
            Color bg = c.getBackground();
            Color fg = c.getForeground();
            boolean opaque = c.isOpaque();
            Font f = c.getFont();
            out.println(name + "|" + c.getClass().getSimpleName()
                    + "|" + p.x + "|" + p.y + "|" + c.getWidth() + "|" + c.getHeight()
                    + "|" + argb(bg) + "|" + argb(fg)
                    + "|" + (opaque ? 1 : 0)
                    + "|" + (f == null ? "?" : f.getFamily()) + "|" + (f == null ? 0 : f.getSize())
                    + "|" + (f == null ? 0 : f.getStyle()));
        }
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                walk(child, root, out);
            }
        }
    }

    private static String argb(Color c) {
        if (c == null) {
            return "none";
        }
        return String.format("%02X%02X%02X%02X", c.getAlpha(), c.getRed(), c.getGreen(), c.getBlue());
    }

    private VisualRef() {
    }
}
