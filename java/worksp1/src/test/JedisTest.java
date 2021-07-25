package test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {
    @Test
    public void testJedis() {
        // 1. 连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 2. 操作redis
        jedis.set("name", "whh");
        // 3. 关闭连接
        jedis.close();
    }
}

