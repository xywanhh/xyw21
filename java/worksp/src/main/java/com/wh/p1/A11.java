package com.wh.p1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class A11 {
    static String f(Set<?> s) {
        return "set";
    }
    static String f(List<?> s) {
        return "list";
    }
    static String f(Collection<?> s) {
        return "collection";
    }
    static String f2(Collection<?> s) {
        return s instanceof Set ? "set" :
                s instanceof List ? "list" : "unknown collection";
    }

    public static void main(String[] args) {
        Collection<?>[] c = {
          new HashSet<String>(),
            new ArrayList<String>(),
            new HashMap<String, String>().values()
        };
        for (Collection<?> cc : c) {
            System.out.println(f(cc));
        }
        for (Collection<?> cc : c) {
            System.out.println(f2(cc));
        }
    }
}
