public class ControlFlow {
    public static void main(String[] args) {
        int total = 0;
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                continue;
            }
            if (i == 8) {
                break;
            }
            total += i;
        }
        System.out.println(total);

        int n = 5;
        int fact = 1;
        while (n > 1) {
            fact *= n;
            n--;
        }
        System.out.println(fact);

        int s = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i + j) % 2 == 0) {
                    s += i * j;
                }
            }
        }
        System.out.println(s);

        int k = 0;
        int acc = 0;
        do {
            acc += (k % 2 == 0) ? k : -k;
            k++;
        } while (k < 7);
        System.out.println(acc);

        boolean flag = acc < 0;
        System.out.println(flag);
        boolean both = flag && total > 0;
        System.out.println(both);

        int classify = classify(total);
        System.out.println(classify);
        System.out.println(classify(-5));
        System.out.println(classify(0));
    }

    static int classify(int v) {
        if (v > 0) {
            return 1;
        } else if (v < 0) {
            return -1;
        }
        return 0;
    }
}
