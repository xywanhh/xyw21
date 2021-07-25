package com.wh.p1;

import java.math.BigDecimal;

public class B5 {
    public static void main(String[] args) {
        System.out.println(1.03 - .42); // 0.6100000000000001
        System.out.println(1.00 - 9 * .10); // 0.09999999999999998
        f1();
        f2();
    }
    static void f1() {
        double f = 1.00;
        int n = 0;
        for (double p = .10;f >= p; p+= .10) {
            f -= p;
            n++;
        }
        System.out.println("bought: " + n);
        System.out.println("change: " + f);
        /**
         * bought: 3
         * change: 0.3999999999999999
         */
    }
    final static BigDecimal CNET = new BigDecimal(".10");
    static void f2() {
        BigDecimal f = new BigDecimal("1.00");
        int n = 0;
        for (BigDecimal p = CNET;f.compareTo(p) >=0; p= p.add(CNET)) {
            f = f.subtract(p);
            n++;
        }
        System.out.println("bought: " + n);
        System.out.println("change: " + f);
        /**
         * bought: 4
         * change: 0.00
         */
    }
}
