package com.wh.p2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class JedisUtil {
    private static JedisPool pool = null;
    private static String host;
    private static int port;
    private static int maxTotal;
    private static int maxIdle;
    static {
        ResourceBundle rb = ResourceBundle.getBundle("redis");
        host = rb.getString("redis.host");
        port = Integer.parseInt(rb.getString("redis.port"));
        maxTotal = Integer.parseInt(rb.getString("redis.maxTotal"));
        maxIdle = Integer.parseInt(rb.getString("redis.maxIdle"));
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        pool = new JedisPool(config, host, port);
    }

    static Jedis getPool() {
        return pool.getResource();
    }

    public static void main(String[] args) {
        Jedis jedis = getPool();
        jedis.set("k1", "v1");
        System.out.println(jedis.get("k1"));
    }
}
