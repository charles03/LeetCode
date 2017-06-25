package com.system.design.objectOrientedDesign;

/**
 * Created by charles on 9/19/16.
 *
 * Factory is a design pattern in common usage.
 * Please implement a ToyFactory which can generate proper toy based on the given type.
 *
 * Example
 ToyFactory tf = ToyFactory();
 Toy toy = tf.getToy('Dog');
 toy.talk();
 >> Wow

 toy = tf.getToy('Cat');
 toy.talk();
 >> Meow
 */
/**
 * Your object will be instantiated and called as such:
 * ToyFactory tf = new ToyFactory();
 * Toy toy = tf.getToy(type);
 * toy.talk();
 */
interface Toy {
    void talk();
}

class Dog implements Toy {
    // Write your code here
    public void talk() {
        System.out.println("Wow");
    }
}

class Cat implements Toy {
    // Write your code here
    public void talk() {
        System.out.println("Meow");
    }
}

public class ToyFactory {
    /**
     * @param type a string
     * @return Get object of the type
     */
    public Toy getToy(String type) {
        // Write your code here
        if ("Dog".equals(type)){
            return new Dog();
        } else if ("Cat".equals(type)) {
            return new Cat();
        }
        return null;
    }
}
