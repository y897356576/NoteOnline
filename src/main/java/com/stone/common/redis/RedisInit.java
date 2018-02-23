package com.stone.common.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/2/22.
 */
@Component
public class RedisInit {

    @PostConstruct
    public static void doRedisInit() {
        final Jedis jedis_0 = new Jedis("106.14.200.149", 6379);
        jedis_0.auth("pwd");
        final Jedis jedis_1 = new Jedis("106.14.200.149", 6380);
        jedis_1.auth("pwd");
        List<Jedis> js = new ArrayList<Jedis>(){{
            add(jedis_0);
            add(jedis_1);
        }};
        new RedisShard(js);
    }

}
