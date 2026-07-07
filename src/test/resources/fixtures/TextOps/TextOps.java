public class TextOps {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Hello");
        sb.append(", ").append("World").append('!').append(' ').append(42).append(' ').append(true);
        System.out.println(sb.toString());
        System.out.println(sb.length());

        sb.insert(0, ">> ");
        System.out.println(sb.toString());
        sb.insert(3, 99);
        System.out.println(sb.toString());

        System.out.println(new StringBuilder("abcde").reverse().toString());

        StringBuilder d = new StringBuilder("0123456789");
        d.deleteCharAt(0);
        d.delete(2, 5);
        System.out.println(d.toString());

        StringBuilder rep = new StringBuilder("aaaa");
        rep.replace(1, 3, "XYZ");
        System.out.println(rep.toString());

        StringBuilder sc = new StringBuilder("cat");
        sc.setCharAt(0, 'b');
        System.out.println(sc.toString());
        System.out.println((int) sc.charAt(2));

        StringBuilder sl = new StringBuilder("truncate");
        sl.setLength(4);
        System.out.println(sl.toString());
        System.out.println(sl.length());

        System.out.println(new StringBuilder("find me here").indexOf("me"));

        System.out.println(Character.isDigit('7'));
        System.out.println(Character.isDigit('a'));
        System.out.println(Character.isLetter('a'));
        System.out.println(Character.isLetterOrDigit('#'));
        System.out.println(Character.isWhitespace(' '));
        System.out.println(Character.isUpperCase('A'));
        System.out.println(Character.isLowerCase('A'));
        System.out.println((int) Character.toUpperCase('a'));
        System.out.println((int) Character.toLowerCase('Z'));
        System.out.println(Character.getNumericValue('7'));
        System.out.println(Character.getNumericValue('c'));
        System.out.println(Character.digit('F', 16));
        System.out.println(Character.digit('9', 8));

        String text = "a1b2c3 D4!";
        int digits = 0;
        int letters = 0;
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            }
        }
        System.out.println("digits=" + digits + " letters=" + letters);
    }
}
