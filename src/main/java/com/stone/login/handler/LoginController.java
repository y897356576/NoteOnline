package com.stone.login.handler;

import com.stone.common.util.EhcacheUtil;
import com.stone.common.util.ResultMap;
import com.stone.core.factory.UserFactory;
import com.stone.core.model.User;
import com.stone.login.service.LoginService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
@Controller
@RequestMapping("/loginHandler")
public class LoginController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    LoginService loginServic;

    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(@RequestBody User user, HttpServletResponse response) {

        Map<String, Object> rsMap = new HashMap<String, Object>();

        try{
            UserFactory.standardUser(user);
            user = loginServic.doLogin(user);
        } catch (Exception e){
            user = null;
            rsMap = ResultMap.generateMap(false, e.getMessage());
        }

        if (user != null) {
            rsMap = ResultMap.generateMap(true);
            logger.info("[" + user.getUserName() + "]：登录成功");

            this.setLoginCookie(response, user);
        }
        return rsMap;
    }

    private void setLoginCookie(HttpServletResponse response, User user){
        Cookie cookie = new Cookie("userId", user.getId());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    @RequestMapping(value = "doLogOut/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void doLogOut(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {

        Cache cache = EhcacheUtil.getCache("ehcacheFir");
        Element element = cache.get(id);
        StringBuilder userId = new StringBuilder();
        if(element != null){
            User user = (User)element.getObjectValue();
            userId = userId.append(user.getId());
            cache.remove(id);
            logger.info("[" + user.getUserName() + "]：注销成功");
        }

        this.clearLoginCookie(request, response, userId.toString());
    }

    private void clearLoginCookie(HttpServletRequest request, HttpServletResponse response, String userId){
        Cookie[] cookies = request.getCookies() == null ? new Cookie[0] : request.getCookies();
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("userId")){
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
}
