package com.wh.p1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class A12 {
    public static void main(String[] args) {
        Set<Integer> s1 = new TreeSet<>();
        List<Integer> l1 = new ArrayList<>();
        for (int i=-3;i<3;i++) {
            s1.add(i);
            l1.add(i);
        }

        // set调用的是boolean remove(Object o);
        // list调用的是E remove(int index);
        for (int i=0;i<3;i++) {
            s1.remove(i);
            l1.remove(i);
            l1.remove(Integer.valueOf(i));
        }
        System.out.println(s1 + " " + l1);
    }
    static void sum(int... arg) {
        int res = 0;
        for (int i : arg) {
            res += i;
        }
        System.out.println(res);
    }
}

//定义注解
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface IntegerVaule{
    int value() default 0;
    String name() default "";
}

//使用注解
class QuicklyWay {
    //当只想给value赋值时,可以使用以下快捷方式
    @IntegerVaule(20)
    public int age;

    //当name也需要赋值时必须采用key=value的方式赋值
    @IntegerVaule(value = 10000, name = "MONEY")
    public int money;
}