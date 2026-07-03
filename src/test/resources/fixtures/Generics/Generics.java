public class Generics {
    public static void main(String[] args) {
        StringBox sb = new StringBox();
        System.out.println(sb.get());
        Box b = sb;
        b.put("raw");
        System.out.println((String) b.get());
        Box<String> tb = new StringBox();
        tb.put("typed");
        String s = tb.get();
        System.out.println(s);
        System.out.println(sb.get());

        Maker m = new StrMaker();
        System.out.println(m.make());
        StrMaker sm = new StrMaker();
        System.out.println(sm.make());
        System.out.println(new Maker().make());

        Holder<String> h = new Holder<String>();
        h.give("held");
        System.out.println((String) h.take());

        String[] letters = new String[] { "a", "b" };
        System.out.println(first(letters));
    }

    static <T> T first(T[] a) {
        return a[0];
    }
}

interface Box<T> {
    T get();

    void put(T t);
}

class StringBox implements Box<String> {
    private String v;

    StringBox() {
        v = "empty";
    }

    public String get() {
        return v;
    }

    public void put(String t) {
        v = t;
    }
}

class Maker {
    Object make() {
        return "base-make";
    }
}

class StrMaker extends Maker {
    String make() {
        return "str-make";
    }
}

class Holder<T> {
    T item;

    T take() {
        return item;
    }

    void give(T t) {
        item = t;
    }
}
