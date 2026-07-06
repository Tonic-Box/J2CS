import java.util.*;
import java.util.stream.*;

public class Streams {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 3, 8, 1, 9, 2, 8);

        System.out.println(nums.stream().filter(n -> n % 2 == 0).map(n -> n * 10)
                .collect(Collectors.toList()));

        System.out.println(nums.stream().mapToInt(n -> n).sum());

        System.out.println(nums.stream().reduce(0, Integer::sum));

        System.out.println(nums.stream().max(Comparator.naturalOrder()).get());

        System.out.println(nums.stream().sorted().distinct().collect(Collectors.toList()));

        System.out.println(nums.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]")));

        System.out.println(nums.stream().count());
        System.out.println(nums.stream().anyMatch(n -> n > 8));
        System.out.println(nums.stream().allMatch(n -> n > 0));

        List<String> words = Arrays.asList("apple", "banana", "avocado", "cherry", "blueberry");
        Map<Character, List<String>> byFirst = words.stream()
                .collect(Collectors.groupingBy(w -> w.charAt(0)));
        System.out.println(byFirst.get('a'));
        System.out.println(byFirst.get('b'));

        int rangeSum = IntStream.range(1, 11).sum();
        System.out.println(rangeSum);

        System.out.println(IntStream.rangeClosed(1, 5).boxed()
                .map(i -> i * i).collect(Collectors.toList()));

        Optional<String> found = words.stream().filter(w -> w.length() > 6).findFirst();
        System.out.println(found.orElse("none"));
        System.out.println(words.stream().filter(w -> w.length() > 100).findFirst().orElse("none"));

        Map<String, Integer> lengths = words.stream()
                .collect(Collectors.toMap(w -> w, String::length));
        System.out.println(lengths.get("banana"));

        System.out.println(nums.stream().mapToInt(n -> n).boxed()
                .reduce(1, (a, b) -> a * b));
    }
}
