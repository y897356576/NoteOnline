package com.stone.register.handler;

import com.stone.common.util.ResultMap;
import com.stone.core.factory.UserFactory;
import com.stone.core.model.User;
import com.stone.register.service.RegisterService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
@Controller
@RequestMapping("/registerHandler")
public class RegisterController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private RegisterService registerService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(HttpServletRequest request) {

        Object obj = request.getParameter("userName");
        String userName = obj == null ? "" : obj.toString().trim();
        obj = request.getParameter("passWord");
        String passWord = obj == null ? "" : obj.toString().trim();

        User user = UserFactory.generateUser();
        user.setUserName(userName);
        user.setPassWord(DigestUtils.md5DigestAsHex(passWord.getBytes()));
        user.setStatus(1);

        Map<String, Object> rsMap;
        try{
            Boolean result = registerService.doRegister(user);
            rsMap = ResultMap.generateMap(result);
        } catch (Exception e){
            rsMap = ResultMap.generateMap(false, e.getMessage());
        }

        return rsMap;
    }
}