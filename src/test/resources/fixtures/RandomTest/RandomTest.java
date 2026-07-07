import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        Random r = new Random(42);
        System.out.println(r.nextInt(100));
        System.out.println(r.nextInt());
        System.out.println(r.nextLong());
        System.out.println(r.nextBoolean());
        System.out.println(r.nextDouble());
        System.out.println(r.nextFloat());

        Random r2 = new Random(12345);
        int[] hist = new int[5];
        for (int i = 0; i < 1000; i++) {
            hist[r2.nextInt(5)]++;
        }
        for (int h : hist) {
            System.out.println(h);
        }

        Random r3 = new Random(7);
        System.out.println(r3.nextInt(16));
        System.out.println(r3.nextInt(16));
        System.out.println(r3.nextInt(1));

        Random r4 = new Random(999);
        long acc = 0;
        for (int i = 0; i < 5; i++) {
            acc += r4.nextLong();
        }
        System.out.println(acc);
    }
}
