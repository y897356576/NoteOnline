package com.stone.common.util;

import com.stone.common.redis.RedisShardPool;
import com.stone.core.exception.MyException;
import com.stone.core.model.User;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 石头 on 2018/2/26.
 */
public class UserInfoUtil {

    /**
     * 从cookie中获取userId，根据userId从redis中获取用户信息
     * @param request
     * @return
     */
    public static User getUserFromRedis(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies() == null ? new Cookie[0] : request.getCookies();
        String userId = "";
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("userId")){
                userId = cookie.getValue();
            }
        }
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        User user = null;
        try {
            Jedis jedis = RedisShardPool.getJedisNode(userId);
            Map userMap = jedis.hgetAll(userId + "_user");
            RedisShardPool.restoreJedisNode(jedis);
            if (userMap != null && userMap.size() > 0) {
                user = (User) ObjMapTransUtil.mapToObj(userMap, User.class);
            }
        } catch (JedisConnectionException e) {
            throw new MyException("Redis服务异常");
        }
        return user;
    }

}
