public class StringOps {
    public static void main(String[] args) {
        String s = "Hello, World";
        System.out.println(s.toUpperCase());
        System.out.println(s.toLowerCase());
        System.out.println(s.contains("World"));
        System.out.println(s.endsWith("World"));
        System.out.println(s.startsWith("Hello"));
        System.out.println(s.indexOf("o"));
        System.out.println(s.indexOf("o", 5));
        System.out.println(s.lastIndexOf("o"));
        System.out.println(s.lastIndexOf('l'));
        System.out.println(s.replace('l', 'L'));
        System.out.println(s.replace("World", "Java"));
        System.out.println(s.concat("!!!"));
        System.out.println("ab".repeat(3));
        System.out.println("  trim me  ".strip() + "|");

        System.out.println("a,b,c,,".split(",").length);
        for (String p : "one:two:three".split(":")) {
            System.out.println(p);
        }
        System.out.println("a1b2c3".replaceAll("[0-9]", "#"));
        System.out.println("a1b2c3".replaceFirst("[0-9]", "#"));
        System.out.println("12345".matches("[0-9]+"));
        System.out.println("12a45".matches("[0-9]+"));

        System.out.println("apple".compareTo("banana"));
        System.out.println("Apple".compareToIgnoreCase("apple"));

        System.out.println(String.valueOf(42));
        System.out.println(String.valueOf(42L));
        System.out.println(String.valueOf('X'));
        System.out.println(String.valueOf(true));
        System.out.println(String.valueOf(3.5));
        System.out.println(String.join("-", "x", "y", "z"));

        char[] chars = s.toCharArray();
        System.out.println(chars.length);
        System.out.println(String.valueOf(chars));
    }
}
