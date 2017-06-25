package com.system.design.objectOrientedDesign;

/**
 * Created by charles on 9/19/16.
 *
 * Singleton is a most widely used design pattern.
 * If a class has and only has one instance at every moment, we call this design as singleton.
 * For example, for class Mouse (not a animal mouse), we should design it in singleton.

 You job is to implement a getInstance method for given class, return the same instance of this class every time you call this method.

 Example
 In Java:

 A a = A.getInstance();
 A b = A.getInstance();
 a should equal to b.

 Challenge
 If we call getInstance concurrently, can you make sure your code could run correctly?
 */
public class SingletonClass {
    /**
     * @return: The same instance of this class every time
     *
     * 因为我们只需要在创建类的时候进行同步，所以只要将创建和getInstance()分开，单独为创建加synchronized关键字
     *
     * Because we only need synchronized process in initializing class, add key word synchronized for create method
     * and split method between initilization and getInstance.
     */
    // http://blog.csdn.net/longyulu/article/details/9159589
    // reference to consider concurrent situation
    private SingletonClass() {
        // set constructor private
    }
    private static SingletonClass instance = null;

    public static SingletonClass getInstance() {
        // write your code here
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    private static synchronized void initInstance() {
        if (instance == null) {
            instance = new SingletonClass();
        }
    }
}

/**
 * another better solution to take care of performance and concurrent scenario
 * is to use mechanism of JVM helps to accomplish synchronized process when it create inner class
 */

class Singleton {
    /* 私有构造方法，防止被实例化 */
    private Singleton() {
    }

    /* 此处使用一个内部类来维护单例 */
    private static class SingletonFactory {
        private static Singleton instance = new Singleton();
    }

    /* 获取实例 */
    public static Singleton getInstance() {
        return SingletonFactory.instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }

}
