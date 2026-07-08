import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorsGaps {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 3, 8, 1, 9, 2);
        List<String> words = Arrays.asList("apple", "banana", "cherry");

        System.out.println(nums.stream().collect(Collectors.minBy(Integer::compareTo)).get());
        System.out.println(nums.stream().collect(Collectors.maxBy(Integer::compareTo)).get());
        System.out.println(nums.stream().collect(Collectors.reducing(0, Integer::sum)));
        System.out.println(nums.stream().collect(Collectors.reducing(Integer::sum)).get());
        System.out.println(words.stream().collect(Collectors.reducing("", (a, b) -> a + b)));
        System.out.println(nums.stream().collect(Collectors.summingLong(n -> (long) n)));
        System.out.println(nums.stream().collect(Collectors.summingDouble(n -> (double) n)));
        System.out.println(nums.stream().collect(Collectors.averagingLong(n -> (long) n)));
        int sz = nums.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
        System.out.println(sz);
        ArrayList<Integer> ac = nums.stream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(ac);
    }
}
