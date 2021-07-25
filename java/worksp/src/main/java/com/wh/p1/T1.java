package com.wh.p1;

import java.util.concurrent.atomic.AtomicInteger;

public class T1 {
    public static final int num = 5;
    public static void main(String[] args) {
        // 2个卖，每个都有5个
        /*for (int i=1;i<=2;i++) {
            new Inner1("worker "+i).start();
        }*/
        // 2个卖，总共5个
        Inner2 inner2 = new Inner2();
        for (int i=1;i<=2;i++) {
            new Thread(inner2, "worker "+i).start();
        }

    }
    static class Inner2 implements Runnable {
        private AtomicInteger in = new AtomicInteger(num);
        @Override
        public void run() {
            for (int i=0;i<=num;i++) {
                if (in.get() > 0) {
                    System.out.println(Thread.currentThread().getName()
                            +" sold one, left " + in.decrementAndGet());
                }
            }
        }
    }
    static class Inner1 extends Thread {
        Inner1 (String name) {
            super(name);
        }
        private int sum = num;
        public void run () {
            for (int i=0;i<=num;i++) {
                if (sum > 0) {
                    System.out.println(getName()+" sold one, left " + (--sum));
                }
            }
        }
    }
}
