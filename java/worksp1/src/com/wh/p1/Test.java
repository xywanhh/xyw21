package com.wh.p1;

/**
 * 提供多个构造方法的实现
 */
public class Test {
    private int a1; // required
    private int a2; // required
    private int a3; // non-required
    private int a4; // non-required

    // 第一种方式
    public Test(int a1) {
        this(a1,0,0,0);
    }
    public Test(int a1, int a2) {
        this(a1,a2,0,0);
    }
    public Test(int a1, int a2, int a3) {
        this(a1,a2,a3,0);
    }
    public Test(int a1, int a2, int a3, int a4) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
    }
}
