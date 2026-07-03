public class SwitchTest {
    public static void main(String[] args) {
        for (int i = -1; i < 7; i++) {
            System.out.println(dense(i));
        }
        System.out.println(sparse(5));
        System.out.println(sparse(100));
        System.out.println(sparse(1000));
        System.out.println(sparse(-7));
        System.out.println(fallthrough(1));
        System.out.println(fallthrough(2));
        System.out.println(fallthrough(3));
        System.out.println(fallthrough(9));
        System.out.println(defaultOnly(3));
        System.out.println(sharedTargets(0));
        System.out.println(sharedTargets(2));
        System.out.println(sharedTargets(5));
    }

    static int dense(int v) {
        switch (v) {
            case 0: return 10;
            case 1: return 11;
            case 2: return 12;
            case 3: return 13;
            case 4: return 14;
            case 5: return 15;
            default: return -1;
        }
    }

    static int sparse(int v) {
        switch (v) {
            case 5: return 1;
            case 100: return 2;
            case 1000: return 3;
            default: return 0;
        }
    }

    static int fallthrough(int v) {
        int r = 0;
        switch (v) {
            case 1:
                r += 1;
            case 2:
                r += 2;
                break;
            case 3:
                r = 100;
                break;
            default:
                r = -1;
        }
        return r;
    }

    static int defaultOnly(int v) {
        switch (v) {
            default: return 42;
        }
    }

    static int sharedTargets(int v) {
        switch (v) {
            case 0:
            case 1:
            case 2:
                return 7;
            case 3:
            case 4:
                return 8;
            default:
                return 9;
        }
    }
}
