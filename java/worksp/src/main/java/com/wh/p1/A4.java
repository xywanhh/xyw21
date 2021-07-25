package com.wh.p1;

/**
 * @SuppressWarnings("unchecked")只能用在声明上
 */
@SuppressWarnings("unchecked")
public class A4 {
    @SuppressWarnings("unchecked")
    private int a1;

    @SuppressWarnings("unchecked")
    void f1() {}

    void f2() {
        @SuppressWarnings("unchecked")
        int a2;
    }

    int f3() {
//        @SuppressWarnings("unchecked") // 不能用在return语句
//        return 1;
        @SuppressWarnings("unchecked")
        int a1 = 0;
        return a1;
    }
}
