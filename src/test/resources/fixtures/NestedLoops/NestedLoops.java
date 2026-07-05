public class NestedLoops {
    public static void main(String[] args) {
        System.out.println(nestedDoWhile());
        System.out.println(breakDoWhile());
        System.out.println(labeledFlow());
    }

    static int nestedDoWhile() {
        int total = 0;
        int i = 0;
        do {
            int j = 0;
            do {
                total += i * j;
                j++;
            } while (j < 3);
            i++;
        } while (i < 3);
        return total;
    }

    static int breakDoWhile() {
        int acc = 0;
        int k = 0;
        do {
            acc += k;
            if (acc > 5) {
                break;
            }
            k++;
        } while (k < 100);
        return acc;
    }

    static int labeledFlow() {
        int spins = 0;
        outer:
        for (int a = 0; a < 4; a++) {
            int b = 0;
            while (b < 4) {
                if (a * b == 6) {
                    continue outer;
                }
                if (a + b == 7) {
                    break outer;
                }
                spins++;
                b++;
            }
        }
        return spins;
    }
}
