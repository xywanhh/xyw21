package com.w1;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class JedisTest {
    @Test
    public void testHash() {
        // 1. 连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 2. 操作redis
        jedis.hset("hash1", "k1", "v1");
        jedis.hset("hash1", "k2", "v2");
        Map<String, String> hash1 = jedis.hgetAll("hash1");
        for (Map.Entry<String, String> entry : hash1.entrySet()) {
            System.out.println(entry.getKey() +":" +entry.getValue());
        }
        System.out.println(jedis.hlen("hash1"));
        // 3. 关闭连接
        jedis.close();
    }

    @Test
    public void testList() {
        // 1. 连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 2. 操作redis
        jedis.lpush("list1", "a", "b", "c");
        jedis.rpush("list1", "x");
        List<String> list1 = jedis.lrange("list1", 0, -1);
        list1.stream().forEach(System.out::print);
        System.out.println(jedis.llen("list1"));
        // 3. 关闭连接
        jedis.close();
    }

    @Test
    public void testString() {
        // 1. 连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 2. 操作redis
        jedis.set("name", "whh");
        String v1 = jedis.get("name");
        System.out.println(v1);
        // 3. 关闭连接
        jedis.close();
    }
}
