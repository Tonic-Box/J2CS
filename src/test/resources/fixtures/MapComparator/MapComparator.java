import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapComparator {
    static final class Item {
        final String name;
        final int value;
        Item(String name, int value) { this.name = name; this.value = value; }
        int getValue() { return value; }
        String getName() { return name; }
        public String toString() { return name + ":" + value; }
    }

    public static void main(String[] args) {
        Map<String, Integer> counts = new HashMap<>();
        String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};
        for (String w : words) {
            counts.merge(w, 1, (a, b) -> a + b);
        }
        System.out.println(counts.get("apple"));
        System.out.println(counts.get("banana"));
        System.out.println(counts.get("cherry"));
        System.out.println(counts.getOrDefault("apple", 0));
        System.out.println(counts.getOrDefault("durian", -1));

        int[] total = {0};
        counts.forEach((k, v) -> total[0] += v);
        System.out.println(total[0]);

        Map<String, List<Integer>> groups = new HashMap<>();
        groups.computeIfAbsent("even", k -> new ArrayList<>()).add(2);
        groups.computeIfAbsent("even", k -> new ArrayList<>()).add(4);
        groups.computeIfAbsent("odd", k -> new ArrayList<>()).add(1);
        System.out.println(groups.get("even"));
        System.out.println(groups.get("odd"));

        Map<String, Integer> m = new HashMap<>();
        m.putIfAbsent("x", 10);
        m.putIfAbsent("x", 99);
        System.out.println(m.get("x"));
        m.replace("x", 20);
        m.replace("y", 5);
        System.out.println(m.get("x"));
        System.out.println(m.containsKey("y"));

        counts.clear();
        System.out.println(counts.size());
        System.out.println(counts.isEmpty());

        List<Item> byValue = new ArrayList<>();
        byValue.add(new Item("banana", 3));
        byValue.add(new Item("apple", 1));
        byValue.add(new Item("cherry", 4));
        byValue.add(new Item("date", 2));
        byValue.sort(Comparator.comparingInt(i -> i.getValue()));
        System.out.println(byValue);

        List<Item> tied = new ArrayList<>();
        tied.add(new Item("banana", 3));
        tied.add(new Item("apple", 3));
        tied.add(new Item("cherry", 1));
        tied.add(new Item("date", 2));
        tied.sort(Comparator.comparingInt((Item i) -> i.getValue()).thenComparing(i -> i.getName()));
        System.out.println(tied);

        List<Item> byNameDesc = new ArrayList<>(byValue);
        byNameDesc.sort(Comparator.comparing((Item i) -> i.getName()).reversed());
        System.out.println(byNameDesc);
    }
}
