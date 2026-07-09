import java.util.Currency;
import java.util.Formatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MiscGaps {
    public static void main(String[] args) {
        threadLocalRandom();
        formatter();
        currency();
        atomics();
        System.out.println("done");
    }

    static void threadLocalRandom() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int a = r.nextInt(100);
        System.out.println(a >= 0 && a < 100);
        int b = r.nextInt(10, 20);
        System.out.println(b >= 10 && b < 20);
        double d = r.nextDouble();
        System.out.println(d >= 0.0 && d < 1.0);
        long l = r.nextLong(1000);
        System.out.println(l >= 0 && l < 1000);
        boolean bo = r.nextBoolean();
        System.out.println(bo || !bo);
        float f = r.nextFloat();
        System.out.println(f >= 0.0f && f < 1.0f);
    }

    static void formatter() {
        Formatter fmt = new Formatter();
        fmt.format("%d-%s-%c", 42, "hi", 'x');
        System.out.println(fmt.toString());

        Formatter fmt2 = new Formatter();
        fmt2.format("[%3d]", 7).format("[%s]", "end");
        System.out.println(fmt2.toString());
    }

    static void currency() {
        Currency usd = Currency.getInstance("USD");
        System.out.println(usd.getCurrencyCode());
        System.out.println(usd.getDefaultFractionDigits());
        System.out.println(usd.getNumericCode());
        Currency jpy = Currency.getInstance("JPY");
        System.out.println(jpy.getDefaultFractionDigits());
        System.out.println(jpy.getNumericCode());
        System.out.println(Currency.getInstance("EUR").getNumericCode());
        try {
            Currency.getInstance("ZZZ");
        } catch (IllegalArgumentException e) {
            System.out.println("bad-code");
        }
    }

    static void atomics() {
        AtomicIntegerArray ia = new AtomicIntegerArray(3);
        ia.set(0, 5);
        ia.set(1, 10);
        System.out.println(ia.get(0));
        System.out.println(ia.incrementAndGet(0));
        System.out.println(ia.getAndAdd(1, 5));
        System.out.println(ia.get(1));
        System.out.println(ia.getAndSet(2, 99));
        System.out.println(ia.compareAndSet(2, 99, 100));
        System.out.println(ia.compareAndSet(2, 99, 7));
        System.out.println(ia.length());
        System.out.println(ia.toString());

        AtomicLongArray la = new AtomicLongArray(new long[]{1L, 2L, 3L});
        System.out.println(la.addAndGet(0, 10L));
        System.out.println(la.getAndIncrement(1));
        System.out.println(la.toString());

        AtomicReferenceArray ra = new AtomicReferenceArray(2);
        ra.set(0, "a");
        ra.set(1, "b");
        System.out.println(ra.get(0));
        System.out.println(ra.getAndSet(1, "c"));
        System.out.println(ra.compareAndSet(0, "a", "z"));
        System.out.println(ra.toString());
        System.out.println(ra.length());
    }
}
