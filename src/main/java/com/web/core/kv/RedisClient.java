package com.web.core.kv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/2/29.
 */
public class RedisClient {
    static ShardedJedisPool shardedJedisPool = null;
    private static Log logger = LogFactory.getLog(RedisClient.class);


    public RedisClient(ShardedJedisPool pool) {
        if (shardedJedisPool == null) {
            shardedJedisPool = pool;
        }
    }

    public void setMap(String key, Map<String, String> value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.hmset(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (jedis != null)
            shardedJedisPool.returnResource(jedis);
    }

    public void setMapWithExpire(String key, Map<String, String> value, Integer time) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.hmset(key, value);
            jedis.expire(key, time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (jedis != null)
            shardedJedisPool.returnResource(jedis);
    }

    public Map<String, String> getMap(String key) {
        ShardedJedis jedis = null;
        Map<String, String> resMap = null;
        try {
            jedis = shardedJedisPool.getResource();
            resMap = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (jedis != null)
            shardedJedisPool.returnResource(jedis);

        return resMap;
    }

    public boolean checkExists(String key) {
        Boolean isExists = false;
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            isExists = jedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (jedis != null)
            shardedJedisPool.returnResource(jedis);

        return isExists;
    }

    public void delMap(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (jedis != null)
            shardedJedisPool.returnResource(jedis);
    }

    public void run111() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "111");
        map.put("2", "222");
        setMap("test", map);

        System.out.println(map.toString());

        map = getMap("test");

        System.out.println(map.toString());

    }


}
