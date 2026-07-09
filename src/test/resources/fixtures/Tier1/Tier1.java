import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tier1 {
    public static void main(String[] args) {
        BigInteger a = BigInteger.valueOf(1000);
        BigInteger b = new BigInteger("123456789012345678901234567890");
        System.out.println(a.add(BigInteger.valueOf(24)));
        System.out.println(b.multiply(BigInteger.TWO));
        System.out.println(b.mod(BigInteger.valueOf(97)));
        System.out.println(BigInteger.valueOf(2).pow(64));
        System.out.println(a.gcd(BigInteger.valueOf(24)));
        System.out.println(a.compareTo(b));
        System.out.println(BigInteger.valueOf(-5).abs());
        System.out.println(BigInteger.TEN.add(BigInteger.ONE));

        BigDecimal x = new BigDecimal("10.50");
        BigDecimal y = new BigDecimal("3");
        System.out.println(x.add(new BigDecimal("0.25")));
        System.out.println(x.multiply(y));
        System.out.println(x.divide(y, 4, RoundingMode.HALF_UP));
        System.out.println(x.setScale(1, RoundingMode.HALF_UP));
        System.out.println(x.scale());
        System.out.println(x.compareTo(new BigDecimal("10.5")));
        System.out.println(new BigDecimal("2.5").add(new BigDecimal("3.5")));

        System.out.println(Stream.iterate(1, n -> n * 2).limit(5).collect(Collectors.toList()));
        System.out.println(Stream.of(1, 2, 3, 4, 5).takeWhile(n -> n < 4).collect(Collectors.toList()));
        System.out.println(Stream.of(1, 2, 3, 4, 5).dropWhile(n -> n < 4).collect(Collectors.toList()));
        System.out.println(Stream.of("a", "b", "c").toList());
        System.out.println(Stream.of(1, 2, 3).toArray().length);
        System.out.println(Stream.concat(Stream.of(1, 2), Stream.of(3, 4)).count());
        System.out.println(Stream.generate(() -> "x").limit(3).collect(Collectors.joining()));

        Scanner sc = new Scanner("10 20 30");
        int sum = 0;
        while (sc.hasNextInt()) { sum += sc.nextInt(); }
        System.out.println(sum);

        StringTokenizer st = new StringTokenizer("a,b,c", ",");
        System.out.println(st.countTokens());
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) { sb.append(st.nextToken()).append('|'); }
        System.out.println(sb);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println(df.format(1234567.891));
        DecimalFormat df2 = new DecimalFormat("0.###");
        System.out.println(df2.format(3.14159));
    }
}
