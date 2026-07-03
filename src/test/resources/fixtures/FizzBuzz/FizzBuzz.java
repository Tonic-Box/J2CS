public class FizzBuzz {
    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            if (i % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
        for (int i = 1; i <= 5; i++) {
            System.out.println(i + ": " + label(i));
        }
    }

    static String label(int i) {
        return i % 3 == 0 ? "Fizz" : "n" + i;
    }
}
