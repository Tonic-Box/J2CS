public class Printf {
    public static void main(String[] args) {
        System.out.printf("%d %d %d%n", 0, -5, 1000000);
        System.out.printf("[%5d][%-5d][%05d][%+d]%n", 42, 42, 42, 42);
        System.out.printf("%,d%n", 1234567);
        System.out.printf("%.2f %.0f %f%n", 3.14159, 2.7, 1.5);
        System.out.printf("[%8.2f][%-8.2f][%08.2f]%n", 3.14, 3.14, 3.14);
        System.out.printf("%.3e %.2E%n", 12345.678, 0.00042);
        System.out.printf("%x %X %o%n", 255, 255, 8);
        System.out.printf("%s %S %10s %-10s|%n", "hi", "hi", "hi", "hi");
        System.out.printf("%.3s%n", "truncated");
        System.out.printf("%b %b %b%n", true, false, null);
        System.out.printf("%c %c%n", 'A', 66);
        System.out.printf("%d%% done%n", 50);
        System.out.printf("%2$s %1$s%n", "world", "hello");
        System.out.println(String.format("total=%d avg=%.1f", 7, 3.5));
        System.out.printf("neg %(d money %,.2f%n", -42, 1234.5);
    }
}
