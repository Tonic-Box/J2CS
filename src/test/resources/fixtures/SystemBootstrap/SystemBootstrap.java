public class SystemBootstrap {
    public static void main(String[] args) {
        System.out.println("hello");
        System.out.println(42);
        System.out.println(3.5);
        System.out.println(true);
        for (int i = 0; i < 3; i++) {
            System.out.println("line " + i);
        }

        int[] src = { 1, 2, 3, 4, 5 };
        int[] dst = new int[5];
        System.arraycopy(src, 0, dst, 0, 5);
        System.out.println(dst[0] + dst[4]);
        System.arraycopy(src, 1, dst, 0, 3);
        System.out.println(dst[0] + "," + dst[1] + "," + dst[2]);

        String[] ss = { "a", "b", "c" };
        String[] sd = new String[3];
        System.arraycopy(ss, 0, sd, 0, 3);
        System.out.println(sd[0] + sd[1] + sd[2]);

        try {
            System.arraycopy(src, 0, dst, 0, 100);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("aioobe caught");
        }

        Object o = new Object();
        System.out.println(System.identityHashCode(o) == System.identityHashCode(o));
        System.out.println(System.identityHashCode(null));
        System.out.println("done");
    }
}
