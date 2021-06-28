package com.wh.p1;

import java.util.Arrays;
import java.util.Objects;

public class A1 implements Cloneable {
    private int a1;
    private int a2;
    private String s1;
    private boolean b1;
    private int[] arr1;
    private float f1;

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof A1)) return false;
        A1 a11 = (A1) o;
        return a1 == a11.a1 &&
                a2 == a11.a2 &&
                b1 == a11.b1 &&
                Float.compare(a11.f1, f1) == 0 &&
                Objects.equals(s1, a11.s1) &&
                Arrays.equals(arr1, a11.arr1);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(a1, a2, s1, b1, f1);
        result = 31 * result + Arrays.hashCode(arr1);
        return result;
    }

    @Override
    public String toString() {
        return "A1{" +
                "a1=" + a1 +
                ", a2=" + a2 +
                ", s1='" + s1 + '\'' +
                ", b1=" + b1 +
                ", arr1=" + Arrays.toString(arr1) +
                ", f1=" + f1 +
                '}';
    }
}
