package com.wh.p1;

public class Test2 {
    public static void main(String[] args) {
        Test2 t = new Test2.Builder(1, 2).setA1(3).setA2(4).build();
    }
    private final int a1; // required
    private final int a2; // required
    private final int a3; // non-required
    private final int a4; // non-required

    static class Builder {
        private int a1; // required
        private int a2; // required
        private int a3 = 0; // non-required
        private int a4 = 1; // non-required

        public Builder(int a1, int a2) {
            this.a1 = a1;
            this.a2 = a2;
        }
        public Builder setA1(int a1) {
            this.a1 = a1;
            return this;
        }
        public Builder setA2(int a2) {
            this.a2 = a2;
            return this;
        }
        public Test2 build() {
            return new Test2(this);
        }
        public <T> T builder(T t) {
            return t;
        }
    }
    public Test2(Builder builder) {
        this.a1 = builder.a1;
        this.a2 = builder.a2;
        this.a3 = builder.a3;
        this.a4 = builder.a4;
    }
}
