public class ArraysTest {
    public static void main(String[] args) {
        int[] a = new int[5];
        for (int i = 0; i < a.length; i++) {
            a[i] = i * i;
        }
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        System.out.println(sum);
        System.out.println(a.length);
        System.out.println(a[3]);

        double[] d = new double[3];
        d[0] = 1.5;
        d[1] = 2.5;
        d[2] = d[0] + d[1];
        System.out.println(d[2]);

        byte[] b = new byte[4];
        int big = 300;
        b[0] = (byte) big;
        b[1] = (byte) 200;
        b[2] = 100;
        b[3] = (byte) (b[2] + b[2]);
        System.out.println(b[0]);
        System.out.println(b[1]);
        System.out.println(b[3]);
        int byteSum = 0;
        for (int i = 0; i < b.length; i++) {
            byteSum += b[i];
        }
        System.out.println(byteSum);

        char[] c = new char[3];
        c[0] = 'a';
        c[1] = (char) (c[0] + 1);
        c[2] = 'c';
        System.out.println(c[1]);
        int charAsInt = c[2];
        System.out.println(charAsInt);

        long[] l = new long[2];
        l[0] = 10000000000L;
        l[1] = l[0] / 4L;
        System.out.println(l[1]);

        boolean[] flags = new boolean[3];
        flags[1] = true;
        System.out.println(flags[0]);
        System.out.println(flags[1]);

        String[] names = new String[3];
        names[0] = "x";
        names[1] = "y";
        names[2] = "z";
        System.out.println(names[2]);
        System.out.println(names.length);

        Box[] boxes = new Box[2];
        boxes[0] = new Box(7);
        boxes[1] = new Box(8);
        System.out.println(boxes[0].v + boxes[1].v);

        int[][] m = new int[2][];
        m[0] = new int[2];
        m[1] = new int[3];
        m[0][1] = 5;
        m[1][2] = 7;
        System.out.println(m[0][1] + m[1][2]);
        System.out.println(m.length);
        System.out.println(m[1].length);

        short[] s = new short[2];
        s[0] = (short) 70000;
        System.out.println(s[0]);

        System.out.println(args.length);
    }
}

class Box {
    int v;

    Box(int v) {
        this.v = v;
    }
}
