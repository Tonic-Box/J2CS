import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    public static void main(String[] args) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update("hello".getBytes(StandardCharsets.UTF_8));
        md.update(" world".getBytes(StandardCharsets.UTF_8));
        byte[] hash = md.digest();
        System.out.println(Base64.getEncoder().encodeToString(hash));
        byte[] oneshot = MessageDigest.getInstance("SHA-256").digest("hello world".getBytes(StandardCharsets.UTF_8));
        System.out.println(MessageDigest.isEqual(hash, oneshot));
        System.out.println(md.getAlgorithm());

        SecretKeySpec key = new SecretKeySpec("secretkey".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] hmac = mac.doFinal("message".getBytes(StandardCharsets.UTF_8));
        System.out.println(Base64.getEncoder().encodeToString(hmac));
        System.out.println(mac.getMacLength());
        System.out.println(key.getAlgorithm());
        System.out.println(key.getFormat());
    }
}
