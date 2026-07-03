public class Interfaces {
    public static void main(String[] args) {
        Greeter en = new En();
        Greeter pirate = new Pirate();
        System.out.println(en.greet());
        System.out.println(pirate.greet());
        Loud loudPirate = new Pirate();
        System.out.println(loudPirate.shout());
        System.out.println(loudPirate.greet());
        En enTyped = new En();
        System.out.println(enTyped.greet());
        System.out.println(Greeter.pick(0, en, pirate).name());
        System.out.println(Greeter.pick(1, en, pirate).greet());
        System.out.println(Greeter.TABLE[1]);
        System.out.println(Greeter.TABLE.length);
        Both b = new Both();
        System.out.println(b.greet());
        System.out.println(b.bump());
        Greeter gb = b;
        Bumper bb = b;
        System.out.println(gb.name());
        System.out.println(bb.bump());
        System.out.println(gb instanceof Bumper);
        System.out.println(en instanceof Loud);
        Object o = pirate;
        System.out.println(o instanceof Greeter);
    }
}

interface Greeter {
    int[] TABLE = {1, 2, 3};

    String name();

    default String greet() {
        return "hi " + name();
    }

    static Greeter pick(int i, Greeter a, Greeter b) {
        return i == 0 ? a : b;
    }
}

interface Loud extends Greeter {
    default String shout() {
        return greet() + "!";
    }
}

interface Bumper {
    int bump();
}

class En implements Greeter {
    public String name() {
        return "en";
    }
}

class Pirate implements Loud {
    public String name() {
        return "pirate";
    }

    public String greet() {
        return "arr " + name();
    }
}

class Both implements Greeter, Bumper {
    public String name() {
        return "both";
    }

    public int bump() {
        return 7;
    }
}
