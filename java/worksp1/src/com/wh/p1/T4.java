package com.wh.p1;

import java.util.concurrent.TimeUnit;

public class T4 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t111");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.interrupt();
        t1.start();
        System.out.println("end");
    }
}
