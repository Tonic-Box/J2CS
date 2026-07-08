import java.util.ArrayList;
import java.util.List;

public class ArrayBox {
    public static void main(String[] args) {
        int[] a = {10, 20, 30};
        Object o = a;
        int[] back = (int[]) o;
        System.out.println(back.length);
        System.out.println(back[0] + back[1] + back[2]);

        Object o2 = a;
        System.out.println(o == o2);

        List<Object> list = new ArrayList<>();
        list.add(a);
        System.out.println(list.contains(a));
        int[] fromList = (int[]) list.get(0);
        System.out.println(fromList[1]);

        String[] ss = {"x", "y"};
        Object so = ss;
        String[] sb = (String[]) so;
        System.out.println(sb[0] + sb[1]);
        System.out.println(o == so);

        Object[] mixed = {a, ss};
        int[] e0 = (int[]) mixed[0];
        String[] e1 = (String[]) mixed[1];
        System.out.println(e0[2] + " " + e1[1]);
    }
}
