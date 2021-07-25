package com.wh.p1;

public class A8 {
    enum T1 {
        A,B,C;
        // DON'T DO THIS
        int numberOfEnum() {
            return ordinal() + 1;
        }
    }
    enum T2 {
        A(1),B(2),C(3);
        private final int val;
        T2(int val) {this.val = val;}
        int numberOfEnum() {
            return val;
        }
    }
}
