import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCipher {
    public static void main(String[] args) throws Exception {
        byte[] keyBytes = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = "abcdef9876543210".getBytes(StandardCharsets.UTF_8);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        Cipher enc = Cipher.getInstance("AES/CBC/PKCS5Padding");
        enc.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] ct = enc.doFinal("Attack at dawn!".getBytes(StandardCharsets.UTF_8));
        String ctB64 = Base64.getEncoder().encodeToString(ct);
        System.out.println(ctB64);
        System.out.println(enc.getBlockSize());

        Cipher dec = Cipher.getInstance("AES/CBC/PKCS5Padding");
        dec.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] pt = dec.doFinal(Base64.getDecoder().decode(ctB64));
        System.out.println(new String(pt, StandardCharsets.UTF_8));

        Cipher ecb = Cipher.getInstance("AES/ECB/PKCS5Padding");
        ecb.init(Cipher.ENCRYPT_MODE, key);
        System.out.println(Base64.getEncoder().encodeToString(ecb.doFinal("Hello AES ECB!!!".getBytes(StandardCharsets.UTF_8))));
    }
}
