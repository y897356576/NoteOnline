package com.stone.common.redis;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2018/2/22.
 */
@Component
public class RedisInit {

    @PostConstruct
    public static void doRedisInit() {
        Set<String> ips = new HashSet<>();
        ips.add("106.14.200.149:6379;pwd");
        ips.add("106.14.200.149:6380;pwd");
        new RedisShardPool(ips, 2);
    }

}
