package com.wh.p1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class T3 {
    public static void main(String[] args) {
        try {
            Inn inn = new Inn();
            FutureTask<Long> futureTask = new FutureTask<>(inn);
            Thread t1 = new Thread(futureTask, "thread-9527");
            t1.start();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " do something");
            long res = futureTask.get();
            System.out.println("end...");
            System.out.println(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class Inn implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            Long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+" start");
            Thread.sleep(1000);
            for (int i=0;i<10000000;i++) {
                int j = i+200;
            }
            long res = System.currentTimeMillis() - startTime;
            System.out.println(Thread.currentThread().getName()+" end");
            return res;
        }
    }
}
