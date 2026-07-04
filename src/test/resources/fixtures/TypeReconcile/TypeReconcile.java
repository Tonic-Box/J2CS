public class TypeReconcile {

    static class Animal {
        String sound() {
            return "...";
        }

        Animal breed() {
            return new Animal();
        }
    }

    static class Cat extends Animal {
        String sound() {
            return "meow";
        }

        Cat breed() {
            return new Cat();
        }
    }

    interface Greeter {
        String greet();
    }

    static class Formal implements Greeter {
        public String greet() {
            return "hello";
        }
    }

    static String describe(Animal x) {
        return x.sound();
    }

    public static void main(String[] args) {
        Cat c = new Cat();
        Animal a = c.breed();
        System.out.println(a.sound());

        System.out.println(describe(c));

        Animal a3 = new Cat();
        Cat c2 = (Cat) a3;
        System.out.println(c2.sound());

        Greeter g = new Formal();
        System.out.println(g.greet());

        Object o = new Formal();
        Greeter g2 = (Greeter) o;
        System.out.println(g2.greet());

        Object oa = new Cat();
        System.out.println(oa instanceof Cat);
        System.out.println(oa instanceof Greeter);
    }
}
