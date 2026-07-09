import java.io.BufferedReader;
import java.io.StringReader;

public class TryResources {
    static class Res implements AutoCloseable {
        final String name;
        Res(String n) { name = n; System.out.println("open " + n); }
        public void close() { System.out.println("close " + name); }
    }

    static class BadClose implements AutoCloseable {
        public void close() { throw new RuntimeException("closefail"); }
    }

    public static void main(String[] args) throws Exception {
        try (Res a = new Res("a"); Res b = new Res("b")) {
            System.out.println("body");
        }
        try (Res c = new Res("c")) {
            System.out.println("body2");
            throw new RuntimeException("boom");
        } catch (RuntimeException e) {
            System.out.println("caught " + e.getMessage());
        }
        try (BufferedReader br = new BufferedReader(new StringReader("line1\nline2"))) {
            System.out.println(br.readLine());
            System.out.println(br.readLine());
        }
        try {
            try (BadClose x = new BadClose()) {
                throw new RuntimeException("primary");
            }
        } catch (RuntimeException e) {
            System.out.println("primary=" + e.getMessage() + " suppressed=" + e.getSuppressed().length);
            if (e.getSuppressed().length > 0) {
                System.out.println("supp0=" + e.getSuppressed()[0].getMessage());
            }
        }
    }
}
