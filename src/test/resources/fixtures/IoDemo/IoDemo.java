import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IoDemo {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write('H');
        baos.write(new byte[]{'i', '!'});
        System.out.println(baos.size());
        System.out.println(baos.toString());
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        System.out.println(bais.available());
        int c;
        StringBuilder rd = new StringBuilder();
        while ((c = bais.read()) != -1) { rd.append((char) c); }
        System.out.println(rd);

        StringWriter sw = new StringWriter();
        sw.write("alpha\nbeta\ngamma");
        BufferedReader br = new BufferedReader(new StringReader(sw.toString()));
        String line;
        int lc = 0;
        while ((line = br.readLine()) != null) { lc++; }
        System.out.println(lc);

        File dir = new File("io_demo");
        dir.mkdir();
        File bin = new File(dir, "data.bin");
        FileOutputStream fos = new FileOutputStream(bin);
        fos.write(new byte[]{1, 2, 3, 4, 5});
        fos.close();
        FileInputStream fis = new FileInputStream(bin);
        byte[] buf = new byte[5];
        int n = fis.read(buf);
        fis.close();
        System.out.println(n);
        System.out.println(buf[0] + buf[4]);

        File txt = new File(dir, "text.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(txt)));
        pw.println("one");
        pw.printf("num=%d%n", 42);
        pw.print("tail");
        pw.close();
        BufferedReader fr = new BufferedReader(new FileReader(txt));
        List<String> lines = fr.lines().collect(Collectors.toList());
        fr.close();
        System.out.println(lines.size());
        System.out.println(lines.get(0) + "|" + lines.get(1) + "|" + lines.get(2));

        Path np = Paths.get("io_demo", "nio.txt");
        BufferedWriter bw = Files.newBufferedWriter(np);
        bw.write("x1");
        bw.newLine();
        bw.write("x2");
        bw.close();
        BufferedReader nbr = Files.newBufferedReader(np);
        System.out.println(nbr.readLine() + "," + nbr.readLine());
        nbr.close();
        OutputStream os = Files.newOutputStream(Paths.get("io_demo", "os.bin"));
        os.write(new byte[]{9, 8, 7});
        os.close();
        InputStream is = Files.newInputStream(Paths.get("io_demo", "os.bin"));
        System.out.println(is.read() + "," + is.read());
        is.close();

        long found = Files.find(Paths.get("io_demo"), 5,
                (pp, attrs) -> attrs.isRegularFile() && pp.toString().endsWith(".txt")).count();
        System.out.println(found);

        System.out.println(FileSystems.getDefault().getSeparator());

        for (File kid : dir.listFiles()) { kid.delete(); }
        dir.delete();

        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.asIntBuffer().put(new int[]{100, 200, 300});
        System.out.println(bb.getInt(0) + "," + bb.getInt(4) + "," + bb.getInt(8));
        bb.position(4);
        ByteBuffer sl = bb.slice();
        System.out.println(sl.getInt());
        ShortBuffer shb = ShortBuffer.allocate(3);
        shb.put((short) 7).put((short) 8).flip();
        System.out.println(shb.get() + shb.get());
        FloatBuffer flb = FloatBuffer.wrap(new float[]{1.5f, 2.5f});
        System.out.println(flb.get() + flb.get());
    }
}
