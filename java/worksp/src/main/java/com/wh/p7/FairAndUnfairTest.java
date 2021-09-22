package com.wh.p7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnfairTest {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    public static void main(String[] args) {
        fair();
//        unfair();
    }

    public static void fair() {
        testLock(fairLock);
    }
    public static void unfair() {
        testLock(unfairLock);
    }
    private static void testLock(Lock lock) {
        for (int i=0;i<5;i++) {
            new Job(lock).start();
        }
    }

    private static class Job extends Thread {
        private Lock lock;
        public Job(Lock lock) {
            this.lock = lock;
        }
        public void run() {
            System.out.println("currentThread："+Thread.currentThread().getName());
            Collection<Thread> list = ((ReentrantLock2)lock).getQueuedThreads();
            for (Thread thread : list) {
                System.out.println("queueThread: "+thread.getName());
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }
        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.
                    getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
