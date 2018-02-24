package com.stone.login.service;

import com.stone.common.redis.RedisShard;
import com.stone.common.util.EhcacheUtil;
import com.stone.common.util.ObjMapTransUtil;
import com.stone.core.exception.MyException;
import com.stone.core.model.User;
import com.stone.core.repository.UserMapperImpl;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * Created by 石头 on 2017/6/20.
 */
@Service
public class LoginService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserMapperImpl userMapperImpl;

    public User doLogin(User user, HttpServletResponse response) {
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

            this.setLoginCookie(response, user);

            //redis保存用户登陆信息
            this.setLoginRedis(user);

            //Ehcache保存用户登陆信息
            //this.setLoginEhcache(user);

            return user;
        }catch (Exception e){
            logger.error("用户登录失败：" + e);
            throw new MyException("用户登录失败：" + e);
        }
    }
    private void setLoginCookie(HttpServletResponse response, User user){
        Cookie cookie = new Cookie("userId", user.getId());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    private void setLoginRedis(User user) {
        Jedis jedis = RedisShard.getRedisNode(user.getId());
        Map<String, String> userMap = ObjMapTransUtil.objToStringMap(user);
        jedis.hmset(user.getId() + "_user", userMap);
    }
    private void setLoginEhcache(User user) {
        Cache cache = EhcacheUtil.getCache("ehcacheFir");
        cache.put(new Element(user.getId(), user));
    }



    public void doLogOut( String id, HttpServletRequest request, HttpServletResponse response) {
        String userId = this.removeLoginCookie(request, response);

        //注销时清除redis中的用户信息
        this.removeLoginRedis(userId);

        //this.removeLoginEhcache(userId);
    }
    private String removeLoginCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies() == null ? new Cookie[0] : request.getCookies();
        String userId = "";
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("userId")){
                userId = cookie.getValue();
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return userId;
    }
    private void removeLoginRedis(String userId) {
        Jedis jedis = RedisShard.getRedisNode(userId);
        Set<String> columns = jedis.hkeys(userId + "_user");
        if (CollectionUtils.isNotEmpty(columns)) {
            jedis.hdel(userId + "_user", columns.toArray(new String[]{}));
        }
    }
    private void removeLoginEhcache(String userId) {
        Cache cache = EhcacheUtil.getCache("ehcacheFir");
        Element element = cache.get(userId);
        if(element != null){
            User user = (User)element.getObjectValue();
            cache.remove(userId);
            logger.info("[" + user.getUserName() + "]：注销成功");
        }
    }
}
