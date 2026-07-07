import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("([a-z]+)@([a-z]+)[.]([a-z]+)");
        Matcher m = p.matcher("john@example.com");
        System.out.println(m.matches());
        System.out.println(m.group());
        System.out.println(m.group(1));
        System.out.println(m.group(2));
        System.out.println(m.group(3));
        System.out.println(m.groupCount());
        System.out.println(m.start(1) + "-" + m.end(1));

        Matcher m2 = Pattern.compile("[0-9]+").matcher("a12b345c6");
        int count = 0;
        StringBuilder sb = new StringBuilder();
        while (m2.find()) {
            sb.append(m2.group()).append(",");
            count++;
        }
        System.out.println(count + ":" + sb);

        System.out.println(Pattern.compile("([a-z])([0-9])").matcher("a1b2c3").replaceAll("$2$1"));

        System.out.println(Pattern.matches("[a-z]+", "hello"));
        System.out.println(Pattern.matches("[a-z]+", "hello123"));

        String[] parts = Pattern.compile(",").split("a,b,c,,");
        System.out.println(parts.length);
        for (String s : parts) {
            System.out.println(s);
        }

        Matcher ci = Pattern.compile("hello", Pattern.CASE_INSENSITIVE).matcher("HELLO world");
        System.out.println(ci.lookingAt());

        Matcher la = Pattern.compile("[a-z]+").matcher("abc123");
        System.out.println(la.lookingAt());
        System.out.println(la.matches());

        System.out.println(p.pattern());
        System.out.println(Pattern.matches(Pattern.quote("a.b"), "a.b"));
        System.out.println(Pattern.matches(Pattern.quote("a.b"), "axb"));
    }
}
