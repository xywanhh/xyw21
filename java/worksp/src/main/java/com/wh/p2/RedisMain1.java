package com.wh.p2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.concurrent.TimeUnit;

public class RedisMain1 {
    public static void main(String[] args) {
        ServiceThread t = new ServiceThread("初级用户", 10);
        t.start();
    }
    static class Service {
        private String name;
        private int c;
        Service(String name, int c) {
            this.name = name;
            this.c = c;
        }
        void service() {
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            String key = "user:" + name;
            String value = jedis.get(key);
            try {
                if (value == null) {
                    jedis.setex(key, 20, Long.MAX_VALUE-c+"");
                } else {
                    long l1 = jedis.incr(key);
                    f1(key, c-(Long.MAX_VALUE-l1));
                }
            } catch (JedisDataException ex) {
                System.out.println("到达使用次数, 升级VIP");
                return;
            } finally {
                jedis.close();
            }
        }
        void f1(String key, long val) {
            System.out.println(key + "执行业务第" + val + "次");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class ServiceThread extends Thread {
        Service service;
        String name;
        int c;
        ServiceThread(String name, int c) {
            service = new Service(name, c);
        }
        @Override
        public void run() {
            while (true) {
                service.service();
            }
        }
    }
}

