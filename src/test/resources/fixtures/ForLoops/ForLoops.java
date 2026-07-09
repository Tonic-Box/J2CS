public class ForLoops {
    public static void main(String[] args) {
        int sum = 0;

        // Sequential loops reusing the same counter name (fold + split into independent loops).
        for (int i = 1; i <= 3; i++) {
            sum += i;
        }
        for (int i = 1; i <= 3; i++) {
            sum += i * i;
        }

        // Nested loops (both fold).
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum += i * j;
            }
        }

        // A for with an empty init.
        int n = 0;
        for (; n < 4; n++) {
            sum += n;
        }

        System.out.println(sum);
        System.out.println(liveOut());
    }

    // A counter used after its loop (live-out): the fold would put it out of scope, so this method
    // takes the no-fold retry path and keeps the counter's declaration in the enclosing scope.
    static int liveOut() {
        int total = 0;
        int k;
        for (k = 0; k < 5; k++) {
            total += k;
        }
        return total + k;
    }
}
