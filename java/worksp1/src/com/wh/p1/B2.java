package com.wh.p1;

public class B2 {
    public static void print(Object obj) {
        // 没有泛型
        Class intClass = int.class;
        // 带泛型的Class对象
        Class<Integer> integerClass = int.class;
        integerClass = Integer.class;

        intClass= double.class; // 没有泛型的约束,可以随意赋值
        // integerClass = double.class // 编译期错误,无法编译通过
        // Class<Number> numberClass=Integer.class; // 编译期错误,无法编译通过

        Class<?> intClass1 = int.class;
        intClass1 = double.class;

        Class<? extends Number> clazz = Integer.class;
        clazz = double.class;
        clazz = Number.class;

        Class c = B2.class;
        int a = 1;
        Class<Boolean> cc = Boolean.TYPE;
        System.out.println(cc); // boolean
//        char.class = Character.TYPE;
//        byte.class = Byte.TYPE;
//        short.class = Short.TYPE;
//        int.class = Integer.TYPE;
//        long.class = Long.TYPE;
//        float.class = Float.TYPE;
//        double.class = Double.TYPE;
//        void.class = Void.TYPE;
        System.out.println(obj);
    }
    public static void main(String[] args) {
        print("inside main");
        new Candy();
        print("After creating Candy");
        try {
            Class.forName("com.zejian.Gum");
        } catch(ClassNotFoundException e) {
            print("Couldn't find Gum");
        }
        print("After Class.forName(\"com.zejian.Gum\")");
        new Cookie();
        print("After creating Cookie");
    }
}

class Candy {
    static {System.out.println("Loading Candy");}
}
class Gum {
    static {System.out.println("Loading Gum");}
}
class Cookie {
    static {System.out.println("Loading Cookie");}
}