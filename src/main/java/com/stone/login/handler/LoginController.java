package com.stone.login.handler;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public Map<String, Object> doLogin(@RequestBody User user) {
        Map<String, Object> rsMap = new HashMap<String, Object>();

        String userName = user.getUserName();
        String passWord = user.getPassWord();
        if (userName.length() == 0 || passWord.length() == 0) {
            rsMap.put("result", false);
            rsMap.put("msg", "用户名与密码不能为空！");
        } else {
            String rsStr = loginServic.doLogin(userName, passWord);
            if (rsStr.equals("true")) {
                rsMap.put("result", true);
                logger.info("[" + userName + "]：登录成功");
            } else {
                rsMap.put("result", false);
                rsMap.put("msg", rsStr);
            }
        }
        return rsMap;
    }


    @RequestMapping(value = "doLogOut/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void doLogOut(@PathVariable("id") String id) {
        Cache cache = CacheManager.create().getCache("ehcacheFir");
        User user = (User)cache.get(id).getObjectValue();
        cache.remove(id);
        logger.info("[" + user.getUserName() + "]：注销成功");
    }
}
