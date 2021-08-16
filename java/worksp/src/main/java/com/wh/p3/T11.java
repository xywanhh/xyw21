package com.wh.p3;

public class T11 {
    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        new T11().deadLock();
    }

    private static void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("t1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println("t2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
