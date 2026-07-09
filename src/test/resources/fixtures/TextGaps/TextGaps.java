import java.util.Arrays;
import java.util.StringJoiner;

public class TextGaps {
    public static void main(String[] args) {
        strings();
        builders();
        buffers();
        characters();
        joiners();
        System.out.println("done");
    }

    static void strings() {
        char[] data = {'h', 'e', 'l', 'l', 'o'};
        System.out.println(String.valueOf(data, 1, 3));
        System.out.println(String.copyValueOf(data));
        System.out.println(String.copyValueOf(data, 0, 2));
        System.out.println(String.join("-", Arrays.asList("a", "b", "c")));
        System.out.println("abc".intern().equals("abc"));

        String s = "a" + new String(Character.toChars(0x1F600)) + "b";
        System.out.println(s.codePointAt(1));
        System.out.println(s.codePointCount(0, s.length()));
        System.out.println(s.offsetByCodePoints(0, 2));
        System.out.println("xy".codePointBefore(1));

        System.out.println("Hello".regionMatches(0, "Help", 0, 3));
        System.out.println("Hello".regionMatches(1, "well", 1, 2));
        System.out.println("Hello".regionMatches(true, 0, "HELP", 0, 3));
        System.out.println("Hello".regionMatches(false, 0, "HELP", 0, 3));

        System.out.println("abc".contentEquals(new StringBuilder("abc")));
        System.out.println("abc".contentEquals(new StringBuffer("abc")));
        System.out.println("abc".contentEquals(new StringBuffer("abd")));

        char[] buf = new char[3];
        "hello".getChars(1, 4, buf, 0);
        System.out.println(String.valueOf(buf));

        System.out.println("[" + "abc".indent(2) + "]");
        System.out.println("[" + "  x\n  y".stripIndent() + "]");
        System.out.println("a\\tb\\nc".translateEscapes());
    }

    static void builders() {
        StringBuilder b = new StringBuilder();
        b.append("ab").append(1).append(2L).append(3.5).append('x')
                .append(true).append(new char[]{'y', 'z'}).appendCodePoint(0x41);
        System.out.println(b.toString());
        b.insert(0, "[").insert(b.length(), "]").insert(1, true).insert(1, 9);
        System.out.println(b.toString());

        StringBuilder c = new StringBuilder("abcabc");
        System.out.println(c.indexOf("bc"));
        System.out.println(c.indexOf("bc", 3));
        System.out.println(c.lastIndexOf("bc"));
        System.out.println(c.substring(1));
        System.out.println(c.substring(1, 3));
        System.out.println(c.subSequence(2, 4).toString());
        System.out.println(c.codePointAt(0));
        c.replace(0, 2, "XY");
        System.out.println(c.toString());
        c.delete(0, 1).deleteCharAt(c.length() - 1);
        System.out.println(c.toString());
        c.setCharAt(0, 'Q');
        System.out.println(c.charAt(0));
        c.setLength(2);
        System.out.println(c.toString());
        System.out.println(c.length());
        System.out.println(c.reverse().toString());

        char[] out = new char[3];
        new StringBuilder("hello").getChars(1, 4, out, 0);
        System.out.println(String.valueOf(out));
    }

    static void buffers() {
        StringBuffer sb = new StringBuffer("start");
        sb.append('-').append(42).append("-end").insert(0, "<");
        System.out.println(sb.toString());
        sb.reverse();
        System.out.println(sb.toString());
        sb.reverse();
        sb.replace(0, 1, "(");
        System.out.println(sb.toString());
        System.out.println(sb.indexOf("end"));
        System.out.println(sb.substring(1, 6));
        System.out.println(sb.length());
    }

    static void characters() {
        System.out.println(Character.isSpaceChar(' '));
        System.out.println(Character.isSpaceChar('x'));
        System.out.println(Character.isAlphabetic('A'));
        System.out.println(Character.isAlphabetic('5'));
        System.out.println((int) Character.forDigit(10, 16));
        System.out.println((int) Character.forDigit(5, 10));
        System.out.println(Character.isJavaIdentifierStart('$'));
        System.out.println(Character.isJavaIdentifierStart('9'));
        System.out.println(Character.isJavaIdentifierPart('9'));
        System.out.println(Character.compare('a', 'b'));
        System.out.println(Character.hashCode('x'));
        System.out.println(Character.charCount(0x1F600));
        System.out.println(Character.charCount('a'));
        System.out.println(Character.toString(0x41));
        System.out.println(Character.isSurrogate('\uD83D'));
        System.out.println(Character.isHighSurrogate('\uD83D'));
        System.out.println(Character.isLowSurrogate('\uDE00'));
        char[] ch = Character.toChars(0x1F600);
        System.out.println(ch.length);
        System.out.println((int) ch[0]);
        System.out.println((int) ch[1]);
    }

    static void joiners() {
        StringJoiner sj = new StringJoiner(", ");
        sj.add("a").add("b").add("c");
        System.out.println(sj.toString());
        System.out.println(sj.length());

        StringJoiner sj2 = new StringJoiner(",", "[", "]");
        System.out.println(sj2.toString());
        sj2.add("x").add("y");
        System.out.println(sj2.toString());
        System.out.println(sj2.length());

        StringJoiner sj3 = new StringJoiner("/").setEmptyValue("EMPTY");
        System.out.println(sj3.toString());
        sj3.add("p").add("q");
        System.out.println(sj3.toString());

        StringJoiner m1 = new StringJoiner("-", "(", ")");
        m1.add("1").add("2");
        StringJoiner m2 = new StringJoiner("+");
        m2.add("a").add("b");
        m1.merge(m2);
        System.out.println(m1.toString());
    }
}
