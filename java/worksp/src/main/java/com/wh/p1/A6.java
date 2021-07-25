package com.wh.p1;

interface Operate {
    double apply(double x, double y);
}
enum Operation11 implements Operate {
    EXP("^") {
        @Override
       public double apply(double x, double y) {return Math.pow(x, y);}
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {return x - y;}
    };
    private final String symbol;
    private Operation11(String symbol) {
        this.symbol = symbol;
    }
}
public class A6 {
    enum Operation implements Operate {
        PLUS("+") {
            @Override
            public double apply(double x, double y) {return x + y;}
        },
        MINUS("-") {
            @Override
            public double apply(double x, double y) {return x - y;}
        },
        TIMES("*") {
            @Override
            public double apply(double x, double y) {return x * y;}
        },
        DIVIDE("/") {
            @Override
            public double apply(double x, double y) {return x / y;}
        };
        private final String symbol;
        private Operation(String symbol) {
            this.symbol = symbol;
        }
        @Override
        public String toString() {
            return symbol;
        }
        public abstract double apply(double x, double y);
    }
}
