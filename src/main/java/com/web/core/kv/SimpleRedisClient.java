package com.web.core.kv;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/2/29.
 */
public class SimpleRedisClient {

    static Jedis jedis = null;

    public SimpleRedisClient(String host, int port) {
        if (jedis == null) {
            jedis = new Jedis(host, port);
        }
    }

    public void setMap(String key, Map<String, String> value) {
        jedis.hmset(key, value);
    }

    public Map<String, String> getMap(String key) {
        return jedis.hgetAll(key);
    }

    public static void main(String[] args) {
        SimpleRedisClient simpleRedisClient = new SimpleRedisClient("127.0.0.1", 6379);

        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "111");
        map.put("2", "222");
        simpleRedisClient.setMap("test", map);
        System.out.println(map.toString());

        map = simpleRedisClient.getMap("test");

        System.out.println(map.toString());

    }
}
