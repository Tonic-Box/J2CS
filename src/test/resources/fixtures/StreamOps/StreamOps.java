import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamOps {
    public static void main(String[] args) {
        System.out.println(IntStream.rangeClosed(1, 10).filter(n -> n % 2 == 0).sum());
        System.out.println(IntStream.rangeClosed(1, 5).map(n -> n * n).sum());
        System.out.println(IntStream.rangeClosed(1, 5).reduce(1, (a, b) -> a * b));

        System.out.println(Arrays.toString(IntStream.of(new int[]{5, 3, 1, 4, 2}).sorted().toArray()));
        System.out.println(Arrays.toString(IntStream.of(new int[]{1, 2, 2, 3, 3, 3}).distinct().toArray()));
        System.out.println(Arrays.toString(IntStream.rangeClosed(1, 10).limit(3).toArray()));
        System.out.println(Arrays.toString(IntStream.rangeClosed(1, 10).skip(7).toArray()));

        System.out.println(IntStream.of(new int[]{3, 1, 4, 1, 5}).max().getAsInt());
        System.out.println(IntStream.of(new int[]{3, 1, 4, 1, 5}).min().getAsInt());
        System.out.println(IntStream.rangeClosed(1, 4).average().getAsDouble());

        System.out.println(IntStream.rangeClosed(1, 10).anyMatch(n -> n > 8));
        System.out.println(IntStream.rangeClosed(1, 10).allMatch(n -> n > 0));
        System.out.println(IntStream.rangeClosed(1, 10).noneMatch(n -> n > 100));

        System.out.println(IntStream.rangeClosed(1, 4).max().orElse(-1));
        System.out.println(IntStream.range(0, 0).max().orElse(-1));

        System.out.println(IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList()));
        System.out.println(IntStream.rangeClosed(1, 5).map(n -> n * 10).boxed().collect(Collectors.toList()));
    }
}
