package com.wh.p1;

public class Test1 {
    private int a1; // required
    private int a2; // required
    private int a3 = 0; // non-required
    private int a4 = 1; // non-required

    public Test1() {}

    public void setA1(int a1) {
        this.a1 = a1;
    }
    public void setA2(int a2) {
        this.a2 = a2;
    }

    public static void main(String[] args) {
        Test1 t = new Test1();
        t.setA1(1);
        t.setA2(2);
    }
}
