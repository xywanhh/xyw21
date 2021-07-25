package com.wh.p1;

import java.util.concurrent.TimeUnit;

public class B7 {
    private static boolean flag;
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!flag) {
                    i++;
                }
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        flag = true;
    }
    // 这里程序永远不会终止
    // 没有同步，状态不可见
}
