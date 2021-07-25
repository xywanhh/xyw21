package com.wh.p1;

import java.util.Comparator;

public class B6 {
    static Integer ii;
    public static void main(String[] args) {
        Integer i1 = new Integer(66);
        Integer i2 = new Integer(66);
        System.out.println(i1 == i2); // false

        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? -1 : ((o1 == o2) ? 0 : 1);
            }
        };
        System.out.println(c.compare(i1, i2)); // 1，这是有问题的
        // 改进
        Comparator<Integer> c1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int i1 = o1; // auto-unboxing
                int i2 = o2; // auto-unboxing
                return i1 < i2 ? -1 : ((i1 == i2) ? 0 : 1);
            }
        };
        System.out.println(c1.compare(i1, i2)); // 0

        if (ii == 0) { // Exception in thread "main" java.lang.NullPointerException
            System.out.println(0);
        }
    }

}
