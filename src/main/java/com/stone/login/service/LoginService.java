package com.stone.login.service;

import com.stone.common.redis.RedisShard;
import com.stone.common.util.ObjMapTransUtil;
import com.stone.core.exception.MyException;
import com.stone.core.model.User;
import com.stone.core.repository.UserMapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
@Service
public class LoginService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserMapperImpl userMapperImpl;

    public User doLogin(User user, String sessionId) {
        try{
            if (user==null || StringUtils.isBlank(user.getUserName())
                    || StringUtils.isBlank(user.getPassWord())) {
                throw new MyException("用户名与密码不能为空");
            }

            String currentPwd = user.getPassWord();

            user = userMapperImpl.getUserByName(user.getUserName());

            if (user == null) {
                throw new MyException("用户名不存在");
            } else if (user.getStatus() == 0) {
                throw new MyException("该用户已被禁用");
            } else if (!DigestUtils.md5DigestAsHex(currentPwd.getBytes()).equals(user.getPassWord())) {
                throw new MyException("密码错误");
            }

            //Ehcache保存用户登陆信息
            /*Cache cache = EhcacheUtil.getCache("ehcacheFir");
            cache.put(new Element(user.getId(), user));*/

            //redis保存用户登陆信息
            Jedis jedis = RedisShard.getRedisNode(sessionId);
            Map<String, String> userMap = ObjMapTransUtil.objToStringMap(user);
            jedis.hmset(sessionId + "_user", userMap);

            return user;
        }catch (Exception e){
            logger.error("用户登录失败：" + e);
            throw new MyException("用户登录失败：" + e);
        }
    }
}
