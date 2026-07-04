public class ArrayClone {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = (int[]) a.clone();
        b[0] = 99;
        System.out.println(a[0] + " " + b[0] + " " + b.length);

        String[] s = {"x", "y"};
        String[] t = (String[]) s.clone();
        System.out.println(t[0] + t[1] + " " + t.length);
    }
}
