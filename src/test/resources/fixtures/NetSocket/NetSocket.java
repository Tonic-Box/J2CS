import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetSocket {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        Thread t = new Thread(() -> {
            try (Socket s = server.accept()) {
                byte[] buf = new byte[5];
                int n = s.getInputStream().read(buf);
                s.getOutputStream().write(buf, 0, n);
                s.getOutputStream().flush();
            } catch (Exception e) {
            }
        });
        t.start();
        try (Socket client = new Socket("localhost", port)) {
            client.getOutputStream().write("hello".getBytes(StandardCharsets.UTF_8));
            client.getOutputStream().flush();
            byte[] resp = new byte[5];
            int n = client.getInputStream().read(resp);
            System.out.println(new String(resp, 0, n, StandardCharsets.UTF_8));
        }
        t.join();
        server.close();
        System.out.println(port > 0);
    }
}
