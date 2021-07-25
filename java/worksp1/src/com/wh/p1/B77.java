package com.wh.p1;

import java.util.concurrent.TimeUnit;

public class B77 {
    private static boolean flag;
    static synchronized void set() {
        flag = true;
    }
    static synchronized boolean read() {
        return flag;
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {


            @Override
            public void run() {
                int i = 0;
                while (!read()) {
                    i++;
                }
                System.out.println(i);
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        set();
    }
    // 这里程序在1s后终止
}
