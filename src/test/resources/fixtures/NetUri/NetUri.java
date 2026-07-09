import java.net.URI;
import java.net.URL;

public class NetUri {
    public static void main(String[] args) throws Exception {
        URI uri = new URI("https://user@example.com:8080/path/to?q=1&x=2#frag");
        System.out.println(uri.getScheme());
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());
        System.out.println(uri.getPath());
        System.out.println(uri.getQuery());
        System.out.println(uri.getFragment());
        System.out.println(uri.getUserInfo());
        System.out.println(uri.getAuthority());
        System.out.println(uri.isAbsolute());
        System.out.println(uri);

        URI rel = new URI("/just/a/path");
        System.out.println(rel.getScheme());
        System.out.println(rel.getPath());
        System.out.println(rel.isAbsolute());

        URL url = new URL("http://host.com/dir/file.html?a=b#sec");
        System.out.println(url.getProtocol());
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getPath());
        System.out.println(url.getFile());
        System.out.println(url.getQuery());
        System.out.println(url.getRef());
    }
}
