import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

public class SignVerify {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        byte[] data = "sign this message".getBytes(StandardCharsets.UTF_8);

        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(kp.getPrivate());
        signer.update(data);
        byte[] sig = signer.sign();
        System.out.println(sig.length);

        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        verifier.update(data);
        System.out.println(verifier.verify(sig));

        Signature v2 = Signature.getInstance("SHA256withRSA");
        v2.initVerify(kp.getPublic());
        v2.update("different message".getBytes(StandardCharsets.UTF_8));
        System.out.println(v2.verify(sig));
    }
}
