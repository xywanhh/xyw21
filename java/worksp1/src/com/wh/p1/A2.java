package com.wh.p1;

import java.util.ArrayList;
import java.util.List;

public class A2 {
    public static void main(String[] args) {
        List l1 = new ArrayList();
        l1.add(1);
        l1.add("aa");
        for (int i=0;i<l1.size();i++) {
            String s = (String)l1.get(i);
            System.out.println(s);
        }

        List<Integer> l2 = new ArrayList();
        l2.add(1);
//        l2.add("aa"); // 编译时候就报错
    }
}
