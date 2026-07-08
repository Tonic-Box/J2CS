import java.util.IntSummaryStatistics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class PrimitiveStreams {
    public static void main(String[] args) {
        IntSummaryStatistics is = IntStream.rangeClosed(1, 5).summaryStatistics();
        System.out.println(is);
        System.out.println(IntStream.of(3, 1, 2).sorted().boxed().count());
        System.out.println(IntStream.rangeClosed(1, 4).mapToLong(i -> (long) i * 1000000000L).sum());
        System.out.println(IntStream.rangeClosed(1, 5).asLongStream().sum());
        System.out.println(IntStream.rangeClosed(1, 5).asDoubleStream().sum());
        System.out.println(IntStream.of(4, 7, 2).reduce(Integer::sum).getAsInt());
        System.out.println(IntStream.rangeClosed(1, 3).findFirst().getAsInt());
        System.out.println(IntStream.rangeClosed(1, 3).asDoubleStream().map(x -> x + 0.5).sum());

        System.out.println(LongStream.rangeClosed(1, 5).sum());
        System.out.println(LongStream.of(5L, 3L, 9L, 1L).sorted().toArray().length);
        System.out.println(LongStream.rangeClosed(1, 4).map(x -> x * x).sum());
        System.out.println(LongStream.rangeClosed(1, 10).filter(x -> x % 2 == 0).count());
        System.out.println(LongStream.of(5L, 3L, 9L, 1L).max().getAsLong());
        System.out.println(LongStream.of(5L, 3L, 9L, 1L).min().getAsLong());
        System.out.println(LongStream.rangeClosed(1, 4).average().getAsDouble());
        System.out.println(LongStream.rangeClosed(1, 5).summaryStatistics());
        System.out.println(LongStream.rangeClosed(1, 3).boxed().count());
        System.out.println(LongStream.rangeClosed(1, 3).reduce(0L, Long::sum));

        System.out.println(DoubleStream.of(1.5, 2.5, 3.0).sum());
        System.out.println(DoubleStream.of(3.0, 1.0, 2.0).sorted().toArray().length);
        System.out.println(DoubleStream.of(1.0, 2.0, 3.0, 4.0).average().getAsDouble());
        System.out.println(DoubleStream.of(1.0, 2.0, 3.0).map(x -> x * 2).sum());
        System.out.println(DoubleStream.of(5.0, 1.0, 9.0).max().getAsDouble());
        System.out.println(DoubleStream.of(1.0, 2.0, 3.0).summaryStatistics());
        System.out.println(DoubleStream.of(1.9, 2.1).mapToInt(x -> (int) x).sum());
    }
}
