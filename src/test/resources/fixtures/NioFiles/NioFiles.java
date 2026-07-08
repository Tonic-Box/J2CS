import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NioFiles {
    public static void main(String[] args) throws Exception {
        Path p = Paths.get("foo", "bar", "baz.txt");
        System.out.println(p);
        System.out.println(p.getFileName());
        System.out.println(p.getParent());
        System.out.println(p.getNameCount());
        System.out.println(p.getName(1));
        System.out.println(Paths.get("a/b/c.txt").getFileName());
        System.out.println(Paths.get("dir").resolve("sub/file.txt"));
        System.out.println(Paths.get("/x/y/../z").normalize());
        System.out.println(Paths.get("a").isAbsolute());

        Path f = Paths.get("nio_scratch_test.dat");
        Files.writeString(f, "hello\nworld");
        System.out.println(Files.exists(f));
        System.out.println(Files.isRegularFile(f));
        System.out.println(Files.size(f));
        System.out.println(Files.readString(f));
        List<String> lines = Files.readAllLines(f);
        System.out.println(lines.size());
        System.out.println(lines.get(0) + "|" + lines.get(1));
        byte[] bytes = Files.readAllBytes(f);
        System.out.println(bytes.length);
        Files.write(f, "12345".getBytes(StandardCharsets.UTF_8));
        System.out.println(Files.size(f));
        System.out.println(Files.readString(f, StandardCharsets.UTF_8));
        Files.delete(f);
        System.out.println(Files.exists(f));

        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putInt(42);
        bb.putLong(1234567890123L);
        bb.put((byte) 7);
        System.out.println(bb.position());
        bb.flip();
        System.out.println(bb.getInt());
        System.out.println(bb.getLong());
        System.out.println(bb.get());
        System.out.println(bb.remaining());
        ByteBuffer w = ByteBuffer.wrap("AB".getBytes(StandardCharsets.UTF_8));
        System.out.println(w.get() + "," + w.get());
        System.out.println(w.capacity());
    }
}
