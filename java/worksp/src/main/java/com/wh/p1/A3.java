package com.wh.p1;

import java.util.ArrayList;
import java.util.List;

public class A3 {
    public static void main(String[] args) {
        List l1 = new ArrayList();
        List<Object> l2 = new ArrayList<>();
        List<String> l3 = new ArrayList<>();
        f1(l1); // ok
        f1(l2); // ok
        f1(l3); // ok
        f2(l1); // ok
//        f2(l3); // 编译报错
        f3(l1); // ok
//        f3(l2); // 编译报错

        f4(l1); // ok
        f4(l2); // ok
        f4(l3); // ok

        f5(l1); // ok
        f5(l2); // ok
        f5(l3); // ok
    }
    static void f1(List l1) {
        for (Object o : l1) {
            System.out.println(o);
        }
    }
    static void f2(List<Object> l1) {
        for (Object o : l1) {
            System.out.println(o);
        }
    }
    static void f3(List<String> l1) {
        for (String s : l1) {
            System.out.println(s);
        }
    }

    static <T> void f4(List<T> l1) {
        for (T o : l1) {
            System.out.println(o);
        }
    }

    static void f5(List<?> l1) {
        for (Object o : l1) {
            System.out.println(o);
        }
    }
}
