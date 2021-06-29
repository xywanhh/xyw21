package com.wh.p1;

import java.util.ArrayList;
import java.util.List;

public class A5 {
    public static void main(String[] args) {
        Integer[] a1 = new Integer[10];
        Number[] a2 = a1; // ok
        List<String> l1 = new ArrayList<>();
//        List<Object> l2 = l1; // 编译报错

        /*
        编译不报错，但是运行时
        Exception in thread "main" java.lang.ArrayStoreException: java.lang.Double
	at com.wh.p1.A5.main(A5.java:12)
         */
        a2[0] = 0.1;
        System.out.println(a1[0]);
    }
}
