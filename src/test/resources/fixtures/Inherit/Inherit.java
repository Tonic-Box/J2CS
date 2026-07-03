public class Inherit {
    public static void main(String[] args) {
        Animal[] zoo = new Animal[3];
        zoo[0] = new Dog();
        zoo[1] = new Puppy();
        zoo[2] = new Dog(6);
        for (int i = 0; i < zoo.length; i++) {
            System.out.println(zoo[i].describe());
            System.out.println(zoo[i]);
        }
        Puppy p = new Puppy();
        System.out.println(p.describe());
        p.legs = 3;
        System.out.println(p.describe());
        System.out.println(p instanceof Dog);
        Animal a = zoo[0];
        if (a instanceof Dog) {
            Dog d = (Dog) a;
            System.out.println(d.fetch());
        }
        Plain c = new Plain();
        System.out.println(c.id());
        System.out.println(Animal.total);
        System.out.println(Dog.total);
        System.out.println(zoo[1].sound());
        Animal ref = p;
        System.out.println(ref.equals(p));
        System.out.println(ref.hashCode() == p.hashCode());
    }
}

abstract class Animal {
    static int total;
    protected int legs;

    Animal(int legs) {
        this.legs = legs;
        total++;
    }

    abstract String sound();

    String describe() {
        return sound() + ":" + legs;
    }

    public String toString() {
        return "animal(" + legs + ")";
    }
}

class Dog extends Animal {
    Dog() {
        super(4);
    }

    Dog(int legs) {
        super(legs);
    }

    String sound() {
        return "woof";
    }

    String fetch() {
        return "fetch!";
    }

    public String toString() {
        return "dog[" + super.toString() + "]";
    }
}

class Puppy extends Dog {
    Puppy() {
        super();
    }

    String sound() {
        return "yip-" + super.sound();
    }
}

class Plain {
    int v;

    Plain() {
        v = 42;
    }

    int id() {
        return v;
    }
}
