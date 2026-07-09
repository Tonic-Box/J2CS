public class EnumReflect {
    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        for (Color c : Color.class.getEnumConstants()) {
            System.out.println("const: " + c.name() + " ord=" + c.ordinal());
        }
        System.out.println("valueOf GREEN: " + Color.valueOf("GREEN"));
        try {
            Color.valueOf("PURPLE");
        } catch (IllegalArgumentException e) {
            System.out.println("iae: " + e.getMessage());
        }
        try {
            Color.valueOf(null);
        } catch (NullPointerException e) {
            System.out.println("npe: " + e.getMessage());
        }
    }
}
