package com.wh.p1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class B3 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        for (Iterator i = list.iterator(); i.hasNext();) {
            for (Iterator i1 = list.iterator(); i1.hasNext();) {
                System.out.println((String)i.next() + (String)i1.next());
            }
        }

        for (Iterator i = list.iterator(); i.hasNext();) {
            String outer = (String)i.next();
            for (Iterator i1 = list.iterator(); i1.hasNext();) {
                System.out.println(outer + (String)i1.next());
            }
        }

        for (String s : list) {
            for (String s1 : list) {
                System.out.println(s + s1);
            }
        }

        for (String s : list) {
            if ("a".equals(s)) {
                // list.remove(s); // 运行报错，Exception in thread "main" java.util.ConcurrentModificationException
                s = "aa"; // 无法修改
            }
        }
        System.out.println(list);
    }

}
