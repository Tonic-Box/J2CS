import java.net.InetAddress;

public class NetAddr {
    public static void main(String[] args) throws Exception {
        InetAddress a = InetAddress.getByName("localhost");
        System.out.println(a.getHostAddress());
        System.out.println(a.isLoopbackAddress());
        InetAddress b = InetAddress.getByName("127.0.0.1");
        System.out.println(b.getHostAddress());
        System.out.println(b.isLoopbackAddress());
    }
}
