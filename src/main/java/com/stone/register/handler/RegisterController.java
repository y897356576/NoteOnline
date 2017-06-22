package com.stone.register.handler;

import com.stone.register.service.RegisterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
@Controller
@RequestMapping("/registerHandler")
public class RegisterController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private RegisterService registerService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(HttpServletRequest request) {
        Map<String, Object> rsMap = new HashMap<String, Object>();

        Object obj = request.getParameter("userName");
        String userName = obj == null ? "" : obj.toString().trim();
        obj = request.getParameter("passWord");
        String passWord = obj == null ? "" : obj.toString().trim();
        rsMap = registerService.doRegister(userName, passWord);
        return rsMap;
    }
}