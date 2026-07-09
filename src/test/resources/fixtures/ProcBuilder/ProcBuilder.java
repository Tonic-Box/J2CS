import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcBuilder {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "echo", "hello", "world");
        Process p = pb.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = r.readLine();
        int exit = p.waitFor();
        System.out.println(line);
        System.out.println(exit);
    }
}
