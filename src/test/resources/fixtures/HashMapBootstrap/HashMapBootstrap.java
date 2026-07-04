import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class HashMapBootstrap {
    public static void main(String[] args) {
        HashMap<String, Integer> m = new HashMap<String, Integer>();
        m.put("one", 1);
        m.put("two", 2);
        m.put("three", 3);
        System.out.println(m.size());
        System.out.println(m.get("two"));
        System.out.println(m.getOrDefault("nope", -1));
        System.out.println(m.containsKey("one"));
        System.out.println(m.containsKey("four"));
        m.put("two", 20);
        System.out.println(m.get("two"));
        System.out.println(m);

        for (String k : m.keySet()) {
            System.out.println("k=" + k);
        }
        for (Integer v : m.values()) {
            System.out.println("v=" + v);
        }
        for (Map.Entry<String, Integer> e : m.entrySet()) {
            System.out.println(e.getKey() + "=" + e.getValue());
        }
        m.remove("one");
        System.out.println(m);
        System.out.println(m.size());

        HashMap<Integer, String> byId = new HashMap<Integer, String>();
        for (int i = 0; i < 20; i++) {
            byId.put(i, "item" + i);
        }
        System.out.println(byId.size());
        System.out.println(byId.get(7));
        System.out.println(byId);
        int sum = 0;
        for (Integer k : byId.keySet()) {
            sum += k;
        }
        System.out.println(sum);

        HashSet<String> set = new HashSet<String>();
        set.add("a");
        set.add("b");
        set.add("a");
        System.out.println(set.size());
        System.out.println(set.contains("a"));
        System.out.println(set.contains("z"));
        set.remove("b");
        System.out.println(set.size());
        System.out.println(set);
        System.out.println("done");
    }
}
