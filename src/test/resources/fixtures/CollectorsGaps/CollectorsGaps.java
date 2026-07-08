import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
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

        IntSummaryStatistics stats = nums.stream().collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println(stats);
        System.out.println(stats.getMax() + " " + stats.getMin() + " " + stats.getSum() + " " + stats.getCount());
        System.out.println(nums.stream().collect(Collectors.summarizingLong(n -> (long) n)));
        System.out.println(nums.stream().collect(Collectors.summarizingDouble(n -> (double) n)));

        System.out.println(nums.stream().collect(Collectors.toUnmodifiableList()).size());
        System.out.println(nums.stream().collect(Collectors.filtering(n -> n % 2 == 0, Collectors.toList())));

        List<List<Integer>> nested = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4), Arrays.asList(5));
        System.out.println(nested.stream().collect(Collectors.flatMapping(l -> l.stream(), Collectors.toList())));

        String tee = nums.stream().collect(Collectors.teeing(
                Collectors.summingInt(Integer::intValue), Collectors.counting(),
                (s, c) -> s + "/" + c));
        System.out.println(tee);

        Map<Integer, String> cm = words.stream().collect(
                Collectors.toConcurrentMap(String::length, w -> w, (x, y) -> x + "|" + y));
        System.out.println(cm.size());
        System.out.println(cm.get(5) + " " + cm.get(6));
        Map<Integer, List<String>> gc = words.stream().collect(
                Collectors.groupingByConcurrent(String::length));
        System.out.println(gc.size());
        System.out.println(gc.get(6));
    }
}
