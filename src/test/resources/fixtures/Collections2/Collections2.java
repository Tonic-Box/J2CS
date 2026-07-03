import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Collections2 {
    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("one", 1);
        m.put("two", 2);
        m.put("three", 3);
        System.out.println(m.size());
        System.out.println(m.get("two"));
        System.out.println(m.containsKey("one"));
        System.out.println(m.containsKey("zero"));
        System.out.println(m.get("absent"));
        m.put("two", 22);
        System.out.println(m.get("two"));
        System.out.println(m.size());
        System.out.println(m);

        String dynTwo = "tw";
        dynTwo = dynTwo + "o";
        System.out.println(m.get(dynTwo));
        System.out.println(m.containsKey(dynTwo));

        m.put("nv", null);
        System.out.println(m.get("nv"));
        System.out.println(m.containsKey("nv"));

        int keyTotal = 0;
        for (String k : m.keySet()) {
            keyTotal += k.length();
        }
        System.out.println(keyTotal);

        for (Map.Entry<String, Integer> e : m.entrySet()) {
            System.out.println(e.getKey() + "=" + e.getValue());
        }

        Map<Integer, String> im = new HashMap<Integer, String>();
        im.put(1000, "a");
        im.put(2000, "b");
        im.put(1000, "c");
        System.out.println(im.get(1000));
        System.out.println(im.containsKey(2000));
        System.out.println(im.size());

        HashSet<Integer> set = new HashSet<Integer>();
        set.add(5);
        set.add(5);
        set.add(10);
        set.add(15);
        System.out.println(set.size());
        System.out.println(set.contains(10));
        System.out.println(set.contains(7));
        System.out.println(set);
        int setTotal = 0;
        for (int v : set) {
            setTotal += v;
        }
        System.out.println(setTotal);
        System.out.println("done");
    }
}
