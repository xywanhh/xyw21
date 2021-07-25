package com.wh.p1;

import java.util.Random;

public class B4 {
    private static final Random random = new Random();
    static int random1(int n) {
        return Math.abs(random.nextInt()) % n;
    }
    static int random2(int n) {
        return random.nextInt(n);
    }
    public static void main(String[] args) {
        for (int i=0;i<10;i++) {
            // System.out.println(random1(10)); // 不安全，不推荐使用
            System.out.println(random2(10));
        }
    }
}
