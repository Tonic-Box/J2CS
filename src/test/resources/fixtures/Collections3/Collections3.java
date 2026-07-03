import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collections3 {
    public static void main(String[] args) {
        List<Integer> l = new ArrayList<Integer>();
        fill(l, 5);
        System.out.println(l);
        System.out.println(total(l));

        Collection<Integer> c = l;
        System.out.println(c.size());
        System.out.println(c.contains(3));
        System.out.println(c.contains(99));

        Map<String, Integer> counts = new HashMap<String, Integer>();
        String[] words = new String[] { "a", "b", "a", "c", "b", "a" };
        for (int i = 0; i < words.length; i++) {
            count(counts, words[i]);
        }
        System.out.println(counts.get("a"));
        System.out.println(counts.get("b"));
        System.out.println(counts.get("c"));
        System.out.println(counts);

        System.out.println(describe(l));
        System.out.println(describe(new ArrayList<Integer>()));
    }

    static void fill(List<Integer> list, int n) {
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
    }

    static int total(List<Integer> list) {
        int s = 0;
        for (int v : list) {
            s += v;
        }
        return s;
    }

    static void count(Map<String, Integer> map, String key) {
        Integer current = map.get(key);
        if (current == null) {
            map.put(key, 1);
        } else {
            map.put(key, current + 1);
        }
    }

    static String describe(Collection<Integer> c) {
        if (c.isEmpty()) {
            return "empty";
        }
        return "size " + c.size();
    }
}
