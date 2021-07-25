package com.wh.p1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class B8 {
    static final ExecutorService service = Executors.newSingleThreadExecutor();
    public static void main(String[] args) throws InterruptedException {
        f1(5);
    }
    static void f1(int n) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(n);
        final CountDownLatch done = new CountDownLatch(n);
        final CountDownLatch start = new CountDownLatch(1);
        for (int i=0;i<n;i++) {
            final int k = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    ready.countDown();
                    try {
                        System.out.println("step start " + k);
//                        start.await();
                        TimeUnit.SECONDS.sleep(k);
                        System.out.println("step end " + k);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        done.countDown();
                    }
                }
            });
        }
        ready.await();
        long startNanos = System.nanoTime();
//        start.countDown();
        done.await();
        System.out.println(System.nanoTime() - startNanos);
        service.shutdown();
    }
}
