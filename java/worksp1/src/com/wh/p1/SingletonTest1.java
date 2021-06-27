package com.wh.p1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * 对单例的破坏
 * 改进，防止被破坏
 */
public class SingletonTest1 {
    public static void main(String[] args) throws Exception{
        System.out.println("-----------序列化----------------------");
        Singleton originSingleton = Singleton.getInstance();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(Singleton.getInstance());
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Singleton serializeSingleton = (Singleton) ois.readObject();
        System.out.println(originSingleton == serializeSingleton); // true

        System.out.println("-----------反射----------------------");
        try {
            Constructor<Singleton> cons = Singleton.class.getDeclaredConstructor();
            cons.setAccessible(true);
            Singleton reflextSingleton = cons.newInstance();
            System.out.println(reflextSingleton == originSingleton); // 报错
        } catch (Exception ex) {
            System.out.println(ex); // java.lang.reflect.InvocationTargetException
        }

        System.out.println("---------------------------克隆----------------------");
        Singleton cloneSingleton = (Singleton) originSingleton.clone();
        System.out.println(cloneSingleton == originSingleton); // true
    }

    private static class Singleton implements Serializable, Cloneable {
        private static volatile boolean isCreate = false;
        private Singleton() {
            if(isCreate) {
                throw new RuntimeException("已然被实例化一次，不能在实例化");
            }
            isCreate = true;
        }
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
            return instance;
        }

        private Object readResolve() {
            return instance;
        }
    }
}