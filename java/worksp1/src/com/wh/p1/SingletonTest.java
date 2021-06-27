package com.wh.p1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
/**
 * 对单例的破坏
 */
public class SingletonTest {
    public static void main(String[] args) throws Exception{
        System.out.println("-----------序列化----------------------");
        Singleton originSingleton = Singleton.getInstance();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(Singleton.getInstance());
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Singleton serializeSingleton = (Singleton) ois.readObject();
        System.out.println(originSingleton == serializeSingleton); // false

        System.out.println("-----------反射----------------------");
        Constructor<Singleton> cons = Singleton.class.getDeclaredConstructor();
        cons.setAccessible(true);
        Singleton reflextSingleton = cons.newInstance();
        System.out.println(reflextSingleton == originSingleton); // false

        System.out.println("---------------------------克隆----------------------");
        Singleton cloneSingleton = (Singleton) originSingleton.clone();
        System.out.println(cloneSingleton == originSingleton); // false
    }

    private static class Singleton implements Serializable, Cloneable {
        private Singleton() {}
        private static volatile Singleton instance;
        public static Singleton getInstance() {
            if(instance == null) {
                synchronized (Singleton.class) {
                    if(instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}