import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapOrdering {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>();
        lhm.put("banana", 1);
        lhm.put("apple", 2);
        lhm.put("cherry", 3);
        lhm.put("banana", 9);
        System.out.println(lhm);
        System.out.println(lhm.keySet());
        lhm.remove("apple");
        lhm.put("date", 4);
        System.out.println(lhm.keySet());

        TreeMap<String, Integer> tm = new TreeMap<>();
        tm.put("banana", 1);
        tm.put("apple", 2);
        tm.put("cherry", 3);
        System.out.println(tm);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> e : tm.entrySet()) {
            sb.append(e.getKey()).append('=').append(e.getValue()).append(' ');
        }
        System.out.println(sb.toString().trim());

        TreeMap<Integer, String> nums = new TreeMap<>();
        nums.put(30, "c");
        nums.put(10, "a");
        nums.put(20, "b");
        System.out.println(nums.keySet());
        System.out.println(nums.values());
    }
}
