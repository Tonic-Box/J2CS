import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class NioExtras {
    public static void main(String[] args) throws Exception {
        Path p = Paths.get("a", "b", "c");
        StringBuilder sb = new StringBuilder();
        for (Iterator<Path> it = p.iterator(); it.hasNext(); ) {
            sb.append(it.next()).append(' ');
        }
        System.out.println(sb.toString().trim());
        System.out.println(p.toFile().getName());

        File f = new File("nio_extras_dir");
        System.out.println(f.mkdir());
        System.out.println(f.isDirectory());
        File child = new File(f, "note.txt");
        Files.writeString(child.toPath(), "line1");
        System.out.println(child.exists());
        System.out.println(child.getName());
        System.out.println(child.length());
        System.out.println(child.getParentFile().getName());
        System.out.println(f.listFiles().length);

        Path src = child.toPath();
        Path dst = Paths.get("nio_extras_dir", "copy.txt");
        Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(Files.readString(dst));
        Files.writeString(dst, "-more", StandardOpenOption.APPEND);
        System.out.println(Files.readString(dst));
        Path moved = Paths.get("nio_extras_dir", "moved.txt");
        Files.move(dst, moved, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(Files.exists(dst));
        System.out.println(Files.exists(moved));
        System.out.println(Files.walk(Paths.get("nio_extras_dir")).count());

        child.delete();
        moved.toFile().delete();
        f.delete();
        System.out.println(f.exists());

        ByteBuffer bb = ByteBuffer.allocate(32);
        bb.putShort((short) 300);
        bb.putChar('Z');
        bb.putFloat(1.5f);
        bb.putDouble(2.5);
        bb.putInt(7).putLong(99L);
        bb.flip();
        System.out.println(bb.getShort());
        System.out.println((int) bb.getChar());
        System.out.println(bb.getFloat());
        System.out.println(bb.getDouble());
        System.out.println(bb.getInt());
        System.out.println(bb.getLong());

        ByteBuffer le = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        le.putInt(0x01020304);
        System.out.println(le.array()[0]);
        System.out.println(le.order());

        ByteBuffer d = ByteBuffer.allocate(8);
        d.putInt(11);
        d.flip();
        System.out.println(d.duplicate().getInt());

        IntBuffer ib = IntBuffer.allocate(4);
        ib.put(10).put(20).put(30);
        ib.flip();
        System.out.println(ib.get() + ib.get() + ib.get());
        CharBuffer cb = CharBuffer.wrap("hello".toCharArray());
        System.out.println(cb.toString());
        System.out.println(cb.get());
    }
}
