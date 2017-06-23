package com.stone.login.service;

import com.stone.core.exception.MyException;
import com.stone.core.model.User;
import com.stone.core.repository.UserMapperImpl;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * Created by 石头 on 2017/6/20.
 */
@Service
public class LoginService {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private UserMapperImpl userMapperImpl;

    public String doLogin(String userName, String passWord) {
        User user;
        try{
            user = userMapperImpl.getUserByName(userName);

            if (user == null) {
                return "用户名不存在！";
            } else if (user.getStatus() == 0) {
                return "该用户已被禁用！";
            } else if (!DigestUtils.md5DigestAsHex(passWord.getBytes()).equals(user.getPassWord())) {
                return "密码错误！";
            }

            CacheManager cacheManager = CacheManager.create();

            Cache cache = cacheManager.getCache("ehcacheFir");
            cache.put(new Element(user.getId(), user));

            return "true";
        }catch (Exception e){
            logger.error("用户登录失败：" + e);
            throw new MyException("用户登录失败");
        }
    }
}
