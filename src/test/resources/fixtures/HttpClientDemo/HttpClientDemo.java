import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HttpClientDemo {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        Thread t = new Thread(() -> {
            try (Socket s = server.accept()) {
                InputStream in = s.getInputStream();
                byte[] buf = new byte[1024];
                in.read(buf);
                String bodyText = "Hi from HttpClient";
                String resp = "HTTP/1.1 200 OK\r\nContent-Length: " + bodyText.length()
                        + "\r\nConnection: close\r\n\r\n" + bodyText;
                OutputStream out = s.getOutputStream();
                out.write(resp.getBytes(StandardCharsets.UTF_8));
                out.flush();
            } catch (Exception e) {
            }
        });
        t.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        t.join();
        server.close();
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
