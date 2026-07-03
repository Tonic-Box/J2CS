public class ShimGrowth {
    public static void main(String[] args) {
        System.out.println(Math.abs(-5));
        int min = Integer.MIN_VALUE;
        System.out.println(Math.abs(min));
        double negd = -2.5;
        System.out.println(Math.abs(negd));
        System.out.println(Math.max(3, 9));
        System.out.println(Math.min(-3, 2));
        double two = 2.0;
        System.out.println(Math.sqrt(two));

        System.out.println(Integer.parseInt("123") + 1);
        System.out.println(Integer.parseInt("-45"));
        System.out.println(Integer.parseInt("+7"));
        System.out.println(Integer.toString(456));

        String h = "hello world";
        System.out.println(h.substring(6));
        System.out.println(h.substring(0, 5));
        System.out.println(h.substring(5, 5));
        System.out.println(h.indexOf('o'));
        System.out.println(h.indexOf("world"));
        System.out.println(h.indexOf("zzz"));
        System.out.println(h.startsWith("he"));
        System.out.println(h.startsWith("wo"));

        int[] src = new int[] {1, 2, 3, 4, 5};
        int[] dst = new int[5];
        System.arraycopy(src, 1, dst, 0, 3);
        for (int i = 0; i < dst.length; i++) {
            System.out.println(dst[i]);
        }
        String[] names = new String[] {"x", "y"};
        String[] copy = new String[2];
        System.arraycopy(names, 0, copy, 0, 2);
        System.out.println(copy[1]);
        int[] overlap = new int[] {1, 2, 3, 4};
        System.arraycopy(overlap, 0, overlap, 1, 3);
        for (int i = 0; i < overlap.length; i++) {
            System.out.println(overlap[i]);
        }
    }
}
