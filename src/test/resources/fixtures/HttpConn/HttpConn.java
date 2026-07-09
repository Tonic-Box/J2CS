import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpConn {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        Thread t = new Thread(() -> {
            try (Socket s = server.accept()) {
                InputStream in = s.getInputStream();
                byte[] buf = new byte[1024];
                in.read(buf);
                String bodyText = "Hello, HTTP!";
                String resp = "HTTP/1.1 200 OK\r\nContent-Length: " + bodyText.length()
                        + "\r\nConnection: close\r\n\r\n" + bodyText;
                OutputStream out = s.getOutputStream();
                out.write(resp.getBytes(StandardCharsets.UTF_8));
                out.flush();
            } catch (Exception e) {
            }
        });
        t.start();
        URL url = new URL("http://localhost:" + port + "/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int code = conn.getResponseCode();
        BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line = r.readLine();
        conn.disconnect();
        t.join();
        server.close();
        System.out.println(code);
        System.out.println(line);
    }
}
